package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/repair")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class RepairService {

    @EJB
    private UserAPIBean userAPIBean;

    @EJB
    private EmailBean emailBean;


    @GET
    //  @Authenticated
    //  @RolesAllowed({"client"})
    @Path("{nif}")
    public Response getUserDetails(@PathParam("nif") Long nif) {
        UserAPIBean userMockAPI = userAPIBean.getUserMockAPI("?nif=" + nif);
        return Response.status(Response.Status.OK)
                .entity(userMockAPI.toString())
                .build();
    }
    @GET
    // @Authenticated
    // @RolesAllowed({"client"})
    @Path("")
    public Response getAllRepair() {
        UserAPIBean[] userMockAPI = userAPIBean.getUserMockAPIList("?role=repair");
        return Response.ok(SimpleUserAPIDTO.from(List.of(userMockAPI))).build();
    }
    @GET
    // @Authenticated
    // @RolesAllowed({"client"})
    @Path("{nif}/{policy}/occurrences")
    public Response clientPolicyOccurrences(@PathParam("nif") Long nif,@PathParam("policy") Long policy) {
        return Response.ok(OccurrenceDTO.from(userAPIBean.clientPolicyOccurrences(policy))).build();
    }

    @POST
    @Path("/{nif}/email/send")
    public Response sendEmail(@PathParam("nif") Long nif, EmailDTO email) throws MessagingException {
        UserAPIBean user = userAPIBean.getUserMockAPI("?nif="+nif);
        emailBean.send(user.getEmail(), email.getSubject(), email.getMessage());
        return Response.noContent().build();
    }


}
