package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.DocumentDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.DocumentBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Document;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("documents")
//@Authenticated
//@RolesAllowed({"client", "repair","expert"})
public class DocumentService {

    @EJB
    private DocumentBean documentBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/{occurrence}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(MultipartFormDataInput input, @PathParam("occurrence") Long occurrenceCode) throws IOException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

        String nif = securityContext.getUserPrincipal().getName();

        List<InputPart> inputParts = uploadForm.get("file");
        var documents = new LinkedList<Document>();

        for (InputPart inputPart : inputParts) {
            MultivaluedMap<String, String> headers = inputPart.getHeaders();
            String filename = getFilename(headers);

            // convert the uploaded file to inputstream
            InputStream inputStream = inputPart.getBody(InputStream.class, null);

            byte[] bytes = IOUtils.toByteArray(inputStream);

            String homedir = System.getProperty("user.home");
            String dirpath = homedir + File.separator + "uploads" + File.separator + occurrenceCode;
            mkdirIfNotExists(dirpath);

            String filepath =  dirpath + File.separator + filename;
            writeFile(bytes, filepath);

            Document document = documentBean.create(filepath, filename, occurrenceCode);
            documents.add(document);
        }

        return Response.ok(DocumentDTO.from(documents)).build();
    }

    private void mkdirIfNotExists(String path) {
        File file = new File(path);

        if (! file.exists()) {
            file.mkdirs();
        }
    }

    @GET
    @Path("download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("id") Long id) {
        var document = documentBean.findDocumentSafe(id);
        var response = Response.ok(new File(document.getFilepath()));
        
        response.header("Content-Disposition", "attachment;filename=" + document.getFilename());

        return response.build();
    }

    @GET
    @Path("/{occurrence}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocuments(@PathParam("occurrence") Long occurrenceCode) {
        var documents = documentBean.getOccurrenceDocuments(occurrenceCode);
        return Response.ok(DocumentDTO.from(documents)).build();
    }

    @GET
    @Path("/{occurrence}/exists")
    public Response hasDocuments(@PathParam("occurrence") Long occurrenceCode) {
        List<Document> documents = documentBean.getOccurrenceDocuments(occurrenceCode);
        return Response.status(Response.Status.OK).entity(!documents.isEmpty()).build();
    }

    private String getFilename(MultivaluedMap<String, String> headers) {
        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }

        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        var file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }
        
        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

        System.out.println("Written: " + filename);
    }
}
