package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.Auth;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.PolicyDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.PolicyBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.academics.security.TokenIssuer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @EJB
    private UserBean userBean;
    @EJB
    private PolicyBean policyBean;

    @EJB
    private ClientBean clientBean;



    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    public Response authenticate(@Valid Auth auth) {

        try {
            URL url = new URL("https://63a3873e471b38b20611069a.mockapi.io/seguroAPI?username=" + auth.getUsername());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                if (dataObject.size() == 0) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("ERROR_FINDING_CLIENT")
                            .build();
                }

                JSONObject data = (JSONObject) dataObject.get(0);
                if (userBean.canLogin((String) data.get("password"), auth.getPassword())) {
                    String token = issuer.issue(auth.getUsername());

                    Policy policy = new Policy(
                            (Long) data.get("pol_num"),
                            (String) data.get("company_name")
                    );

                    PolicyDTO policyDTO = PolicyDTO.from(policy);
                    policyBean.create(policyDTO.getCode(), policyDTO.getName());


                    Client client = new Client(
                            (String) data.get("username"),
                            (String) data.get("name"),
                            (String) data.get("email"),
                            policy
                    );



                    ClientDTO clientDTO = ClientDTO.from(client);
                    clientBean.create(clientDTO.getUsername(),clientDTO.getName(),clientDTO.getEmail(),clientDTO.getPolicyCode());

                    return Response.ok(token).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        var username = securityContext.getUserPrincipal().getName();
        var user = userBean.findUserSafe(username);

        return Response.ok(UserDTO.from(user)).build();
    }
}
