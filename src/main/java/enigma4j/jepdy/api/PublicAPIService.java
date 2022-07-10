package enigma4j.jepdy.api;

import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.api.restformats.ActiveGame;
import enigma4j.jepdy.model.Game;
import io.quarkus.logging.Log;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.*;

@Path("/jepdy/public/api")
public class PublicAPIService extends Enigma4JAbstractService {


    @GET
    @Path("active")
    public List<ActiveGame> activeGames() {

        LOG.info("G/public/api/active");

        Collection<Game> games= Game.getActiveGames();
        List<ActiveGame> results=new LinkedList<>();
        for(Game g:games) {
            ActiveGame ag=new ActiveGame();
            ag.name=g.name;
            ag.code=g.shortCode;
            ag.started=g.started;
            results.add(ag);
        }
        LOG.infof("return %d games ",results.size());

        return results;

    }

    @GET
    @Path("userinfo")
    public Map<String,String> userInfo() {

        Log.info("G/public/api/userinfo");
        Map m=new HashMap<>();
        try {
            populateMap(m);
        } catch(Throwable e) {
            populateNoAuth(m);
        }
        return m;
    }

    private void populateNoAuth(Map m) {
        m.put("ua","0");

    }

    private void populateMap(Map m) {
        if(userinfo!=null) {
            m.put("ua","1");
            if(userinfo.contains("login")) {
                m.put("user", userinfo.getString("login"));
            }
        }
        else {
            m.put("ua","0");
        }

    }

}
