package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.PaginatedDTOs;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ClientCreateDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
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
import java.util.List;

@Path("/clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Expert", "Administrator"})
public class ClientService {

    @EJB
    private ClientBean clientBean;

    @EJB
    private EmailBean emailBean;

    @Context
    private SecurityContext securityContext;

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

        return Response.ok(ClientDTO.from(clientBean.findOrFail(username))).build();
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
        Client client = clientBean.findOrFail(username);

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
}
