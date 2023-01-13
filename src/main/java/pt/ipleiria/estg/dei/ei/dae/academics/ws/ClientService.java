package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;


import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;


@Path("/clients")

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"All"})

public class ClientService {

    @EJB
    private UserAPIBean userAPIBean;

    @GET
    @Path("{nif}")
    public Response getUserDetails(@PathParam("nif") Long nif) {

        UserAPIBean userMockAPI = userAPIBean.getUserMockAPI("?nif="+nif);


        return Response.status(Response.Status.OK)
                .entity(userMockAPI.toString())
                .build();
    }

}
