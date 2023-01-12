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
@RolesAllowed({"User"})

public class ClientService {

    @EJB
    private MockAPIBean mockAPIBean;

    @GET
    @Path("{nif}")
    public Response getUserDetails(@PathParam("nif") Long nif) {

        List<MockAPIBean> list = mockAPIBean.getOnMockAPI("?nif="+nif);

        try{
            String Json = mockAPIBean.MockAPItoJSON(list);
            System.out.println(Json);


        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Response.status(Response.Status.OK)
                .entity(list.get(0).toString())
                .build();
    }

}
