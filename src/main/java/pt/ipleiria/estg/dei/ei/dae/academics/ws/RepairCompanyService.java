package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.RepairCompanyDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.RepairCompanyBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.RepairManBean;
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
    RepairManBean repairManBean;
    @EJB
    private RepairCompanyBean repairCompanyBean;
    @EJB
    private OccurrenceBean occurrenceBean;

    @GET
    @Authenticated
    @RolesAllowed({"expert,repair"})
    @Path("{repairID}/occurrences")
    public Response repairCompanyOccurrences(@PathParam("repairID") Long repairID) {
        return Response.ok(OccurrenceDTO.from(repairCompanyBean.repairCompanyOccurrences(repairID))).build();
    }

    @POST
    @Path("")
    @Authenticated
    @RolesAllowed({"client"})
    public Response createRepairCompany(RepairCompanyDTO repairCompany) {
        Long newCompanyId = repairCompanyBean.create(repairCompany.getRepairCompany(), repairCompany.getEmail());
        RepairCompanyDTO dto = RepairCompanyDTO.from(repairCompanyBean.findRepairCompanySafe(newCompanyId));
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Authenticated
    //@RolesAllowed({"client,repair"})
    @Path("")
    public Response getAllRepairCompanies() {
        return Response.ok(RepairCompanyDTO.from(repairCompanyBean.all())).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"expert,repair"})
    @Path("{companyId}")
    public Response getAllRepairFromCompanies(@PathParam("companyId") Long company) {
        return Response.ok(OccurrenceDTO.from(repairCompanyBean.repairCompanyOccurrences(company))).build();
    }

    @GET
    @Authenticated
    @RolesAllowed({"expert,repair"})
    @Path("/repair/{nif}")
    public Response getAllOccurrenceFromRepairCompany(@PathParam("nif") Long nif) {
        return Response.ok(OccurrenceDTO.from(repairCompanyBean.repairCompanyOccurrences(repairManBean.findRepairSafe(nif).getNif()))).build();
    }


    @PUT
    @Path("{occurrenceID}")
    public Response updateRepairCompany(@PathParam("occurrenceID") Long occurrenceID, String companyName) {
        occurrenceBean.updateRepairCompany(occurrenceID, companyName);
        return Response.ok(OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(occurrenceID))).build();
    }


}
