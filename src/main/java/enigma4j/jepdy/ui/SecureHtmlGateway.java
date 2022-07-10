package enigma4j.jepdy.ui;

import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.engine.GameStateBroadcaster;
import enigma4j.jepdy.model.*;
import enigma4j.jepdy.ui.forms.ScoreForm;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Authenticated
@Path("/jepdy/secure")
public class SecureHtmlGateway extends Enigma4JAbstractService {

    @Inject
    GameStateBroadcaster publisher;

    @Inject
    @Location("jepdy/welcome.html")
    Template welcome;

    @Inject
    @Location("jepdy/game.html")
    Template game;

    @Inject
    @Location("jepdy/host.html")
    Template host;

    @Inject
    @Location("jepdy/includes/host_grid.html")
    Template host_grid;

    @Inject
    @Location("jepdy/includes/clue_modal.html")
    Template clue_modal;

    @Location("jepdy/includes/gamestate.html")
    Template gamestate;

    @GET
    @Path("game/host")
    @Produces("text/html")
    public TemplateInstance host(@QueryParam("code") String code) {

        LOG.info("G/jepdy/host");
        Map<String,Object> user=getUser();
        user.put("code",code);
        Game g=Game.findByCode(code);
        LOG.infof("host found game %s",g);

        user.put("game", g);

        return host.data(user);
    }
    @POST
    @Path("game/host")
    @Produces("text/html")
    public TemplateInstance host2(@QueryParam("code") String code) {

        LOG.info("P/jepdy/host");
        Map<String,Object> user=getUser();
        user.put("code",code);
        Game g=Game.findByCode(code);
        LOG.infof("host found game %s",g);
        user.put("game", g);

        return host.data(user);
    }


    @GET
    @Path("game/play")
    @Produces("text/html")
    public TemplateInstance play() {

        LOG.info("G/jepdy/play");
        Map<String,Object> user=getUser();
        return welcome.data(user);
    }

    @POST
    @Path("game/clue_modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance clueModal(@QueryParam("code") String code,
                                      @QueryParam("row") int row,
                                      @QueryParam("round") int round,
                                      @QueryParam("cell") int cell) {

        LOG.infof("P/jepdy/clue_modal code=%s,round=%d,row=%d,cell=%d",code,round,row,cell);
        Map<String,Object> m=new HashMap<>();
        m.put("code",code);

        Game g=Game.findByCode(code);
        LOG.infof("found game %s",g);
        m.put("game",g);
        if(g!=null) {
            Round r = g.rounds.get(round);
            Row w = r.rows.get(row - 1);
            Cell c = w.cells.get(cell - 1);
            m.put("round",round);
            m.put("row",row);
            m.put("cell",cell);
            m.put("c",c);
        }

        LOG.info("P/jepdy/clue_modal returned");
        return clue_modal.data(m);

    }


    @POST
    @Path("game/score")
    public Response score(@BeanParam ScoreForm f){

        LOG.infof("P/jepdy/score %s (w=%d,l=%d",f.code,f.winner,f.loser);
        Game g=Game.findByCode(f.code);
        if(g==null) return Response.status(Response.Status.NOT_FOUND).build();

        Round r=g.rounds.get(f.round);
        Row w=r.rows.get(f.row-1);
        Cell c=w.cells.get(f.cell-1);
        g.score(c,f.winner-1,f.loser-1);

        publisher.broadcast(f.code);
        URI rd=URI.create("/jepdy/secure/game/host?code="+f.code);

        LOG.infof("score redirecting to %s",rd);
        return Response.temporaryRedirect(rd).build();
    }

    @POST
    @Path("game/revealClue")
    public Response revealClue(@QueryParam("code") String code,
                               @QueryParam("round") int round,
                               @QueryParam("row") int row,
                               @QueryParam("cell") int cell) {

        LOG.infof("P/jepdy/revealClue clue %s",code);

        Game g=Game.findByCode(code);
        if(g==null) return Response.status(Response.Status.NOT_FOUND).build();

        Round r=g.rounds.get(round);
        Row w=r.rows.get(row-1);
        Cell c=w.cells.get(cell-1);

        g.currentCell=c;
        g.status= GameState.showClue;
        g.persistOrUpdate();
        publisher.broadcast(g.shortCode);

        return Response.ok().build();
    }


    @POST
    @Path("game/revealAnswer")
    public Response revealAnswer(@QueryParam("code") String code,
                                 @QueryParam("round") int round,
                                 @QueryParam("row") int row,
                                 @QueryParam("cell") int cell) {

       LOG.infof("/jepdy/revealAnswer code=[%s]",code);

        Game g=Game.findByCode(code);
        if(g==null) {
            LOG.infof("couldn't find game %s",code);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Round r=g.rounds.get(round);
        Row w=r.rows.get(row-1);
        Cell c=w.cells.get(cell-1);

        g.currentCell=c;
        g.status=GameState.showAnswer;
        g.persistOrUpdate();
        publisher.broadcast(g.shortCode);

        return Response.ok().build();
    }



    @POST
    @Path("game/start")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance start(@QueryParam("code") String code) {

        LOG.infof("/jepdy/start  code %s",code);

        Game g=Game.findByCode(code);
        LOG.infof("found game %s",g);
        if(g !=null) {
            g.status = GameState.started;
            g.persistOrUpdate();
            publisher.broadcast(g.shortCode);
        }
        return gamestate.data("game",g,"code",code);

    }


    @POST
    @Path("game/nextRound")
    public TemplateInstance nextRound(@QueryParam("code") String code) {

        Game g=Game.findByCode(code);
        LOG.infof("nextRound: found game %s",g);
        if(g!=null) {
            int max = g.rounds.size();
            int next = g.currentRound + 1;
            if (next < max) {
                g.currentRound = next;
                g.persistOrUpdate();
                publisher.broadcast(g.shortCode);
            }
        }

        Map<String,Object> user=getUser();
        user.put("code",code);
        user.put("game", g);

        return host_grid.data(user);
    }


    @POST
    @Path("game/previousRound")
    public TemplateInstance previousRound(@QueryParam("code") String code) {

        Game g=Game.findByCode(code);
        LOG.infof("previousRound: found game $s",g);
        if(g!=null) {
            int next = g.currentRound - 1;

            if (next >= 0) {
                g.currentRound = next;
                g.persistOrUpdate();
                publisher.broadcast(g.shortCode);
            }
        }
        Map<String,Object> user=getUser();
        user.put("code",code);
        user.put("game", g);

        return host_grid.data(user);
    }


    @POST
    @Path("game/endgame")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance endGame(@QueryParam("code") String code) {

        LOG.infof("/jepdy/endgame %s",code);
        Game g=Game.findByCode(code);
        LOG.infof("endGame: found game $s",g);
        if(g!=null) {
            g.status = GameState.finished;
            g.persistOrUpdate();
            publisher.broadcast(g.shortCode);
        }

        return gamestate.data("game",g,"code",code);

    }

}
