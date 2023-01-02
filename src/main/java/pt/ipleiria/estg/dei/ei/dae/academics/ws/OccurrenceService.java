package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.PaginatedDTOs;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.OccurrenceDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.requests.PageRequest;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("occurrences")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OccurrenceService {

    @EJB
    private OccurrenceBean occurrenceBean;


    @Path("")
    @GET
    public Response all(@BeanParam @Valid PageRequest pageRequest) {
        var count = occurrenceBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTOs<>(count)).build();
        }

        var subjects = occurrenceBean.all(pageRequest.getOffset(), pageRequest.getLimit());

        var paginatedDTO = new PaginatedDTOs<>(OccurrenceDTO.from(subjects), count, pageRequest.getOffset());
        return Response.ok(paginatedDTO).build();
    }
}
