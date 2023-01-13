package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.RepairServiceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("occurrence")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OccurrenceService {
    @EJB
    private OccurrenceBean occurrenceBean;

    @POST
    @Path("")
    //@Authenticated
    //@RolesAllowed({"client"})
    public Response createOccurrence(OccurrenceDTO occurrence, RepairServiceDTO repairService) {

        occurrenceBean.create(occurrence.getPolicyNumber(), occurrence.getDescription(), occurrence.getExpertNif(), repairService.getId());
        OccurrenceDTO dto = OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(occurrence.getId()));
        return Response.status(Response.Status.CREATED).entity(dto).build();

    }

    @GET
    @Path("{occurrenceID}")
    //@Authenticated
    //@RolesAllowed({"client,repair,expert"})
    public Response getOccurrenceDetails(@PathParam("occurrenceID") Long occurrenceID) {
        return Response.ok(OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(occurrenceID))).build();

    }
    @PUT
    @Path("{occurrenceID}")
    public Response updateOccurrenceStatus(@PathParam("occurrenceID") Long occurrenceID, String occurrenceState) {
        occurrenceBean.updateOccurrence(occurrenceID, occurrenceState);
        return Response.ok(OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(occurrenceID))).build();
    }




}
