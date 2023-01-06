package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.*;


import javax.ejb.EJB;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;


@Path("/clients")

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
//@Authenticated
//@RolesAllowed({"Expert", "Administrator"})

public class ClientService {

    @EJB
    private MockAPIBean mockAPIBean;

    @GET
    @Path("{nif}")
    public Response getClientDetails(@PathParam("nif") Long nif) {

        List<MockAPIBean> list = mockAPIBean.getOnMockAPI("?nif="+nif);
        System.out.println("\n\n\n\n" + list.get(0).getCovers().get(1) + "\n\n\n\n" );

        return Response.status(Response.Status.OK)
                .entity(list.get(0).toString())
                .build();
    }


}
