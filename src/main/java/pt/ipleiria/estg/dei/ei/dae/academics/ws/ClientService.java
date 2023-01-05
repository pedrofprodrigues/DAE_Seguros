package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.StudentNotInTheSameSubjectCourseException;
import pt.ipleiria.estg.dei.ei.dae.academics.requests.PageRequest;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Path("/clients")

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
//@Authenticated
//@RolesAllowed({"Expert", "Administrator"})

public class ClientService {

    @EJB
    private ClientBean clientBean;

    @EJB
    private EmailBean emailBean;

    @EJB
    private PolicyBean policyBean;


    @Context
    private SecurityContext securityContext;

/*
    @GET
    @Path("/")
    public Response all(@BeanParam @Valid PageRequest pageRequest) {
        var count = clientBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTOs<>(count)).build();
        }

        List<Client> clients = clientBean.getAll(pageRequest.getOffset(), pageRequest.getLimit());
        var paginatedDTO = new PaginatedDTOs<>(ClientDTO.from(clients), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"Client"})
    @Path("{username}")
    public Response get(@PathParam("username") String username) {
        if(!securityContext.getUserPrincipal().getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok(ClientDTO.from(clientBean.findClientSafe(username))).build();
    }


    @GET
    @Authenticated
    @RolesAllowed({"Client"})
    @Path("{username}/subjects")
    public Response enrolled(@PathParam("username") String username) {
        return Response.ok(OccurrenceDTO.from(clientBean.enrolled(username))).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"Client"})
    @Path("{username}/subjects/unrolled")
    public Response unrolled(@PathParam("username") String username) {
        return Response.ok(OccurrenceDTO.from(clientBean.unrolled(username))).build();
    }

    @PATCH
    @Path("{username}/occurrences/{code}/enroll")
    public Response enroll(@PathParam("username") String studentUsername, @PathParam("code") Long subjectCode) throws StudentNotInTheSameSubjectCourseException {
        clientBean.enroll(studentUsername, subjectCode);
        return Response.noContent().build();
    }

    @PATCH
    @Path("{username}/occurrences/{code}/unroll")
    public Response unroll(@PathParam("username") String studentUsername, @PathParam("code") Long subjectCode) throws StudentNotInTheSameSubjectCourseException {
        clientBean.unroll(studentUsername, subjectCode);
        return Response.noContent().build();
    }

    @POST
    @Path("/{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email) throws MessagingException {
        Client client = clientBean.findClientSafe(username);

        emailBean.send(client.getEmail(), email.getSubject(), email.getMessage());
        return Response.noContent().build();
    }






    @POST
    @Path("/")
    public Response create(ClientCreateDTO studentDTO) {
        clientBean.create(
                studentDTO.getUsername(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getPolicyCode()
        );

        var student = clientBean.find(studentDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(ClientDTO.from(student)).build();
    }

 */




    @GET
    @Path("{nif}")
    public Response getClientDetails(@PathParam("nif") Long nif) {

        try{
            URL url = new URL("https://63a3873e471b38b20611069a.mockapi.io/seguroAPI?nif="+nif);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);

            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                if (dataObject.size() == 0){
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ERROR_FINDING_CLIENT")
                            .build();
                }

                System.out.println("numero de apolices"+dataObject.size());

                List<PolicyDTO> totalPolicies = new ArrayList<>();


                for (Object o : dataObject) {

                    JSONObject data = (JSONObject) o;


                    Policy policy = new Policy(
                            (Long) data.get("pol_num"),
                            (String) data.get("company_name")
                    );

                    PolicyDTO policyDTO = PolicyDTO.from(policy);
                    policyBean.create(policyDTO.getCode(), policyDTO.getName());
                    totalPolicies.add(policyDTO);



                    Client client = new Client(
                            (String) data.get("username"),
                            (String) data.get("name"),
                            (String) data.get("email"),
                            policy
                    );



                    ClientDTO clientDTO = ClientDTO.from(client);
                    clientBean.create(clientDTO.getUsername(),clientDTO.getName(),clientDTO.getEmail(),clientDTO.getPolicyCode());



                }
                return Response.ok(totalPolicies).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.CONFLICT)
                .entity("ERROR_DUPLICATING_CLIENT")
                .build();
    }


    @GET
    @Path("/")
    public Response getAllClients() {

        try{
            URL url = new URL("https://63a3873e471b38b20611069a.mockapi.io/seguroAPI/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);

            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                if (dataObject.size() == 0){
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ERROR_FINDING_CLIENT")
                            .build();
                }

                System.out.println("numero de apolices"+dataObject.size());

                List<PolicyDTO> totalPolicies = new ArrayList<>();


                for (Object o : dataObject) {

                    JSONObject data = (JSONObject) o;


                    Policy policy = new Policy(
                            (Long) data.get("pol_num"),
                            (String) data.get("company_name")
                    );

                    PolicyDTO policyDTO = PolicyDTO.from(policy);
                    policyBean.create(policyDTO.getCode(), policyDTO.getName());
                    totalPolicies.add(policyDTO);



                    Client client = new Client(
                            (String) data.get("username"),
                            (String) data.get("name"),
                            (String) data.get("email"),
                            policy
                    );



                    ClientDTO clientDTO = ClientDTO.from(client);
                    clientBean.create(clientDTO.getUsername(),clientDTO.getName(),clientDTO.getEmail(),clientDTO.getPolicyCode());



                }
                return Response.ok(totalPolicies).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.CONFLICT)
                .entity("ERROR_DUPLICATING_CLIENT")
                .build();
    }

}
