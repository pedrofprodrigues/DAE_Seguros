package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/repairService")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RepairServiceService {

    @EJB
    private UserAPIBean userAPIBean;



    @GET
    // @Authenticated
    // @RolesAllowed({"expert"})
    @Path("{repairID}/occurrences")
    public Response repairServiceOccurrences(@PathParam("repairID") Long repairID) {
        return Response.ok(OccurrenceDTO.from(userAPIBean.repairServiceOccurrences(repairID))).build();
    }


}
