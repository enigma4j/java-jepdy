package enigma4j.jepdy.ui;

import enigma4j.Enigma4JAbstractService;

import enigma4j.jepdy.model.Game;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import java.util.Map;

@Path("/jepdy/public")
public class PublicHtmlGateway extends Enigma4JAbstractService {

    private static final Logger LOG = Logger.getLogger(PublicHtmlGateway.class);

    @Inject
    @Location("jepdy/welcome.html")
    Template welcome;

    @Inject
    @Location("jepdy/active_games.html")
    Template active;

    @Inject
    @Location("jepdy/game.html")
    Template game;


    @Inject
    @Location("jepdy/viewstate.html")
    Template viewstate;


    @Inject
    @Location("jepdy/nogame.html")
    Template nogame;




    @GET
    @Path("welcome")
    @Produces("text/html")
    public TemplateInstance welcome() {

        LOG.info("G/jepdy/welcome");
        Map<String,Object> user=getUser();
        return welcome.data(user);

    }


    @GET
    @Path("games")
    @Produces("text/html")
    public TemplateInstance games() {

        LOG.info("G/jepdy/games");
        Map<String,Object> user=getUser();
        return active.data(user);
    }


    @GET
    @Path("game/viewstate")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance viewstate(@QueryParam("code") String id) {
        LOG.infof("G/jepdy/viewstate code=[%s]",id);
        Game g=Game.findByCode(id);
        LOG.infof("game found = %s",g);
        if(g==null) return nogame.data(null);
        return viewstate.data("game",g);
    }

    @GET
    @Path("/game/watch")
    @Produces("text/html")
    public TemplateInstance game(@QueryParam("code") String code) {

        LOG.infof("G/jepdy/game/watch code=[%s]",code);

        Map<String,Object> user=getUser();
        user.put("code",code);

        Game g= Game.findByCode(code);

        LOG.infof("game=%s",g);
        if(g!=null) {
            LOG.infof("team=%d", g.teams.size());
            LOG.infof("rounds=%d", g.rounds.size());
        }
        user.put("game",g);



        return game.data(user);
    }






}
