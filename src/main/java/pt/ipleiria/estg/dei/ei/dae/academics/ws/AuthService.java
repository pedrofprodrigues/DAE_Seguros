package pt.ipleiria.estg.dei.ei.dae.academics.ws;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.Auth;


import pt.ipleiria.estg.dei.ei.dae.academics.dtos.UserAPIDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;
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

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @Context
    private SecurityContext securityContext;
    @EJB
    private UserAPIBean userAPIBean;

    @POST
    @Path("/login")
    public Response authenticate(@Valid Auth auth) {

        UserAPIBean loggedUser = userAPIBean.getUserMockAPI("?nif=" + auth.getNif());

        if (userAPIBean.canLogin(loggedUser.getPassword(), auth.getPassword())) {
            String token = issuer.issue(loggedUser);
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        String nif = securityContext.getUserPrincipal().getName();
        UserAPIBean authedUser = userAPIBean.getUserMockAPI("?nif="+nif);

        return Response.ok(UserAPIDTO.from(authedUser)).build();
    }
}


