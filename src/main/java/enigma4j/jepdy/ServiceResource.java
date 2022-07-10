package enigma4j.jepdy;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.quarkus.oidc.OidcSession;
import io.quarkus.security.Authenticated;

@Path("/app/service")
@Authenticated
public class ServiceResource {

    @Inject
    OidcSession oidcSession;

    @GET
    @Path("logout")
    public void logout() {
        oidcSession.logout().await().indefinitely();

    }
}