package enigma4j.jepdy;
import io.quarkus.oidc.IdToken;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;


import javax.ws.rs.core.UriInfo;

@Path("/callback/github")
public class AuthorisationService {

    @Inject
    @IdToken
    JsonWebToken idToken;


    @Context
    UriInfo info;

    @Inject
    io.quarkus.oidc.UserInfo userinfo;

    @GET
    @Produces("text/html")
    public String authorised() {


        StringBuilder response = new StringBuilder().append("<html>")
                .append("<body>");
        response.append("logged in as ").append(userinfo.getString("login"));
        response.append("callback::").append(idToken).append("<hr>");
        response.append("userinfo::").append(userinfo.getUserInfoString()).append("<hr>");
        response.append("path::").append(info.getPath()).append("<hr>");
        response.append("request::").append(info.getRequestUri());


        return response.append("</body>").append("</html>").toString();

    }
}
