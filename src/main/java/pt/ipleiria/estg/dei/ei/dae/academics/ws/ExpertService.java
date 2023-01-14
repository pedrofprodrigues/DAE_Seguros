package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SimpleUserAPIDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.annotation.security.RolesAllowed;

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
    @Authenticated
    //@RolesAllowed({"expert"})
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
    @Authenticated
    //@RolesAllowed({"expert"})
    @Path("{nif}/occurrences")
    public Response expertOccurrences(@PathParam("nif") Long nif) {
        return Response.ok(OccurrenceDTO.from(userAPIBean.expertOccurrences(nif))).build();
    }

    @GET
    @Authenticated
    //@RolesAllowed({"client"})
    @Path("")
    public Response getAllRepair() {
        UserAPIBean[] userMockAPI = userAPIBean.getUserMockAPIList("?role=expert");
        return Response.ok(SimpleUserAPIDTO.from(List.of(userMockAPI))).build();
    }

}
