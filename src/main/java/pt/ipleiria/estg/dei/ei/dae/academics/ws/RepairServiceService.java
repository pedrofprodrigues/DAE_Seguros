package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.RepairServiceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.RepairServiceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.annotation.security.RolesAllowed;
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

    @EJB
    private RepairServiceBean repairServiceBean;



    @GET
    // @Authenticated
    // @RolesAllowed({"expert"})
    @Path("{repairID}/occurrences")
    public Response repairServiceOccurrences(@PathParam("repairID") Long repairID) {
        return Response.ok(OccurrenceDTO.from(userAPIBean.repairServiceOccurrences(repairID))).build();
    }


    @POST
    @Path("")
    @Authenticated
    @RolesAllowed({"client"})
    public Response createRepairService(RepairServiceDTO repairService) {

        repairServiceBean.create(repairService.getInsuranceCompany(),repairService.getClient());
        RepairServiceDTO dto = RepairServiceDTO.from(repairServiceBean.findRepairServiceSafe(repairService.getId()));
        return Response.status(Response.Status.CREATED).entity(dto).build();

    }

}
