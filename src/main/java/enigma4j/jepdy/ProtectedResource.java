package enigma4j.jepdy;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;

@Path("/app/web-app")
@Authenticated
public class ProtectedResource {

    @Inject
    @IdToken
    JsonWebToken idToken;


    @Inject
    @Location("WelcomeUser.html")
    Template welcomeUser;


    @Inject
    io.quarkus.oidc.UserInfo userinfo;


    @GET
    @Produces("text/html")
    public TemplateInstance getUserName() {
       String login= userinfo.getString("login");

        return  welcomeUser.data("login",login);
        /*
        StringBuilder response = new StringBuilder().append("<html>")
                .append("<body>");


        response.append("userName::").append(userinfo.get("login")).append("<hr>");


        return response.append("</body>").append("</html>").toString();
    */

    }
}