package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.PaginatedDTOs;
import pt.ipleiria.estg.dei.ei.dae.academics.requests.PageRequest;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("policies")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PolicyService {

    @EJB
    private PolicyBean policyBean;

    @GET
    @Path("/")
    public Response all(@BeanParam @Valid PageRequest pageRequest) {
        var count = policyBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTOs<>(count)).build();
        }

        var courses = policyBean.all(pageRequest.getOffset(), pageRequest.getLimit());

        var paginatedDTO = new PaginatedDTOs<>(PolicyDTO.from(courses), count, pageRequest.getOffset());
        return Response.ok(paginatedDTO).build();
    }

    @GET
    @Path("{code}")
    public Response get(@PathParam("code") Long code) {
        return Response.ok(PolicyDTO.from(policyBean.find(code))).build();
    }

    /*
    @POST
    @Path("")
    @Authenticated
    @RolesAllowed({"Administrator"})
    public Response create(PolicyDTO course) {
        policyBean.create(course.getCode(), course.getName());

        var dto = PolicyDTO.from(policyBean.find(course.getCode()));
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @PUT
    @Path("{code}")
    public Response update(@PathParam("code") Long code, PolicyDTO policyDTO) {
        policyBean.updatePolicy(code, policyDTO.getName());
        policyDTO = PolicyDTO.from(policyBean.find(policyDTO.getCode()));

        return Response.ok(policyDTO).build();
    }
*/
    @DELETE
    @Path("{code}")
    public Response delete(@PathParam("code") Long code) {
        policyBean.remove(code);
        return Response.noContent().build();
    }
}
