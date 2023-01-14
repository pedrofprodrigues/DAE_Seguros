package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SimpleUserAPIDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/expert")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ExpertService {

    @EJB
    private UserAPIBean userAPIBean;


    @GET
    //  @Authenticated
    //  @RolesAllowed({"expert"})
    @Path("{nif}")
    public Response getUserDetails(@PathParam("nif") Long nif) {
        UserAPIBean userMockAPI = userAPIBean.getUserMockAPI("?nif=" + nif);
        return Response.status(Response.Status.OK)
                .entity(userMockAPI.toString())
                .build();
    }


    @GET
    // @Authenticated
    // @RolesAllowed({"expert"})
    @Path("{nif}/occurrences")
    public Response expertOccurrences(@PathParam("nif") Long nif) {
        return Response.ok(OccurrenceDTO.from(userAPIBean.expertOccurrences(nif))).build();
    }

    @GET
    // @Authenticated
    // @RolesAllowed({"client"})
    @Path("")
    public Response getAllRepair() {
        UserAPIBean[] userMockAPI = userAPIBean.getUserMockAPIList("?role=expert");
        return Response.ok(SimpleUserAPIDTO.from(List.of(userMockAPI))).build();
    }

}
