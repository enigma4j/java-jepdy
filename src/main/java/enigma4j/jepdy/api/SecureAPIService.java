package enigma4j.jepdy.api;

import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.api.restformats.CategorySummary;
import enigma4j.jepdy.model.Category;
import enigma4j.jepdy.model.Clue;
import enigma4j.jepdy.model.Game;
import enigma4j.jepdy.model.User;
import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;

import javax.ws.rs.*;
import java.util.*;

@Authenticated
@Path("/jepdy/secure/api")
public class SecureAPIService extends Enigma4JAbstractService {



    @POST
    @Path("logout")
    public String logout() {
        LOG.info("P/secure/api/logout");
        oidcSession.logout().await().indefinitely();
        return "login";
    }



    @GET
    @Path("users")
    public List<User> displayUsers() {
        LOG.info("G/secure/api/users");
        List<User> users= User.listAll();
        LOG.info("display "+users.size()+" users");
        return users;

    }



    @GET
    @Path("categories")
    public List<CategorySummary> displayCategories() {

        LOG.info("G/secure/api/categories");
        List<Category> categories= Category.listAll();
        List<CategorySummary> results=new LinkedList<>();
        for(Category c:categories) {
            CategorySummary cs=new CategorySummary();
            cs.id=c.id;
            cs.title=c.title;
            cs.clueCount=c.entries.size();
            results.add(cs);
        }
        LOG.info("display "+categories.size()+" categories");
        return results;

    }



    @GET
    @Path("clues/{catid}")
    public List<Clue> displayClues(@PathParam("catid") Long id) {

        LOG.infof("G/secure/api/clues catid=[%d]",id);
       Category parent= Category.findById(id);
        LOG.infof("found category %s",parent);
       return parent.entries;

    }

    @GET
    @Path("game/{id}")
    public Game activeGame(@PathParam("id") String id) {

        LOG.infof("G/api/secure/game code=[%s]",id);
        Game g=Game.findByCode(id);
        LOG.infof("found game %s",g);
        return g;

    }
}
