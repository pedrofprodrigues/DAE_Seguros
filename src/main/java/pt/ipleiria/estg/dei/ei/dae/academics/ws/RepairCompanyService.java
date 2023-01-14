package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.RepairCompanyDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.RepairCompanyBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/repairCompany")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RepairCompanyService {

    @EJB
    private UserAPIBean userAPIBean;
    @EJB
    private RepairCompanyBean repairCompanyBean;

    @EJB
    private OccurrenceBean occurrenceBean;

    @GET
    // @Authenticated
    // @RolesAllowed({"expert"})
    @Path("{repairID}/occurrences")
    public Response repairCompanyOccurrences(@PathParam("repairID") Long repairID) {
        return Response.ok(OccurrenceDTO.from(userAPIBean.repairCompanyOccurrences(repairID))).build();
    }

    @POST
    @Path("")
    @Authenticated
    @RolesAllowed({"client"})
    public Response createRepairCompany(RepairCompanyDTO repairCompany) {
        repairCompanyBean.create(repairCompany.getRepairCompany(), repairCompany.getEmail());
        RepairCompanyDTO dto = RepairCompanyDTO.from(repairCompanyBean.findRepairCompanySafe(repairCompany.getId()));
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    // @Authenticated
    // @RolesAllowed({"expert"})
    @Path("")
    public Response getAllRepairCompanies() {
        return Response.ok(RepairCompanyDTO.from(repairCompanyBean.all())).build();
    }

    @PUT
    @Path("{occurrenceID}")
    public Response updateRepairCompany(@PathParam("occurrenceID") Long occurrenceID, String companyName) {
        occurrenceBean.updateRepairCompany(occurrenceID, companyName);
        return Response.ok(OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(occurrenceID))).build();
    }


}
