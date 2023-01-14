package pt.ipleiria.estg.dei.ei.dae.academics.ws;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.PolicyAPIDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
//@Authenticated
//@RolesAllowed({"expert","repair"})

public class ClientService {

    @EJB
    private UserAPIBean userAPIBean;

    @EJB
    private EmailBean emailBean;


    @GET
    //  @Authenticated
    //  @RolesAllowed({"client"})
    @Path("{nif}")
    public Response getUserDetails(@PathParam("nif") Long nif) {
        UserAPIBean userMockAPI = null;
        try {
            userMockAPI = userAPIBean.getUserMockAPI("?nif=" + nif);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No User found").build();
        }

        return Response.status(Response.Status.OK)
                .entity(userMockAPI.toString())
                .build();
    }
    @GET
   // @Authenticated
   // @RolesAllowed({"client"})
    @Path("{nif}/policies")
    public Response clientPolicies(@PathParam("nif") Long nif) {
        return Response.ok(PolicyAPIDTO.from(List.of(userAPIBean.clientPolicies(nif)))).build();
    }
    @GET
    // @Authenticated
    // @RolesAllowed({"client"})
    @Path("{nif}/{policy}/occurrences")
    public Response clientPolicyOccurrences(@PathParam("nif") Long nif,@PathParam("policy") Long policy) {
        if(List.of(userAPIBean.clientPolicies(nif)).isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("No User found").build();
        }
        List <Occurrence> exists = userAPIBean.clientPolicyOccurrences(policy);
        if(exists.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("No policy found").build();
        }
        return Response.ok(OccurrenceDTO.from(exists)).build();
    }

    @POST
    @Path("/{nif}/email/send")
    public Response sendEmail(@PathParam("nif") Long nif, EmailDTO email) throws MessagingException {
        UserAPIBean user = userAPIBean.getUserMockAPI("?nif="+nif);
        emailBean.send(user.getEmail(), email.getSubject(), email.getMessage());
        return Response.noContent().build();
    }
}
