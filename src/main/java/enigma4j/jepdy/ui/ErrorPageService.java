package enigma4j.jepdy.ui;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/jepdy/public/error/noaccess")
public class ErrorPageService {


    @Inject
    @Location("NoAccess.html")
    Template welcomeUser;



    @GET
    @Produces("text/html")
    public TemplateInstance noAccess() {
        return welcomeUser.instance();
    }
}
