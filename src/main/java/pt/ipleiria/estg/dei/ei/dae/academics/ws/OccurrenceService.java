package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrencePolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.PolicyAPIBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
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
    @EJB
    private PolicyAPIBean policyAPIBean;

    @POST
    @Path("")
    @Authenticated
    @RolesAllowed({"client"})
    public Response createOccurrence(OccurrenceDTO occurrence) {
        Long newID = occurrenceBean.create(occurrence.getPolicyNumber(), occurrence.getDescription(), occurrence.getExpertNif());
        OccurrenceDTO dto = OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(newID));
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("{occurrenceID}")
    @Authenticated
    @RolesAllowed({"client,repair,expert"})
    public Response getOccurrenceDetails(@PathParam("occurrenceID") Long occurrenceID) {
        Occurrence occurrenceSafe = occurrenceBean.findOccurrenceSafe(occurrenceID);

        return Response.ok(OccurrencePolicyDTO.from(occurrenceBean.findOccurrenceSafe(occurrenceID),
                policyAPIBean.getPolicyMockAPI("?policy_number=" + occurrenceSafe.getPolicyNumber()))).build();

    }

    @PUT
    @Path("{occurrenceID}")
    public Response updateOccurrenceStatus(@PathParam("occurrenceID") Long occurrenceID, String occurrenceState) {
        occurrenceBean.updateOccurrence(occurrenceID, occurrenceState);
        return Response.ok(OccurrenceDTO.from(occurrenceBean.findOccurrenceSafe(occurrenceID))).build();
    }


}
