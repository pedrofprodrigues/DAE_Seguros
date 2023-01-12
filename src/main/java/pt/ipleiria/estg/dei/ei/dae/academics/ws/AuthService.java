package pt.ipleiria.estg.dei.ei.dae.academics.ws;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.Auth;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.MockAPIBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.academics.security.TokenIssuer;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;


@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;
    @EJB
    private UserBean userBean;
    @Context
    private SecurityContext securityContext;
    @EJB
    private MockAPIBean mockAPIBean;


    @POST
    @Path("/login")
    public Response authenticate(@Valid Auth auth) {

        List<MockAPIBean> list = mockAPIBean.getOnMockAPI("?nif=" + auth.getNif());

        System.out.println(list.get(0).getNif());


        if (userBean.canLogin(list.get(0).getPassword(), auth.getPassword())) {
            String token = issuer.issue(auth.getNif());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        String username = securityContext.getUserPrincipal().getName();
        var user = userBean.findUserSafe(username);

        return Response.ok(UserDTO.from(user)).build();
    }
}

