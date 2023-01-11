package pt.ipleiria.estg.dei.ei.dae.academics.ws;


import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.*;


import javax.ejb.EJB;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URL;
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
      // URL url = new URL("https://63a3873e471b38b20611069a.mockapi.io/seguroAPI/?nif=" + nif);

        if(list==null || list.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.OK)
                .entity(list.toString())
                .build();
    }


}
