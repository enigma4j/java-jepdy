package enigma4j.jepdy.engine;

import com.mongodb.client.model.Indexes;
import enigma4j.jepdy.api.UpdateService;
import enigma4j.jepdy.model.Cell;
import enigma4j.jepdy.model.Game;
import enigma4j.jepdy.model.GameState;
import enigma4j.jepdy.model.Team;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import io.quarkus.runtime.Startup;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Collection;

@Singleton
public class GameStore  {

    /*
    @Inject
    UpdateService publisher;

    private FriendlyIDGenerator idGen=null;

    public static final Logger log = Logger.getLogger(GameStore.class);

    public GameStore() throws IOException {

        log.warn("--> game store created");
      FriendlyIDGenerator idGen=new FriendlyIDGenerator();

     // mongoCollection().createIndex(Indexes.text("shortCode"));

    }

    public synchronized String addGame(Game e) {

        String code=null;
        do {
            code = idGen.generate();
        }
        while(find("shortCode", code).firstResult()!=null);

        e.shortCode=code;

        persist(e);

        return e.id.toString();
    }

    public Game getGame(String key) {
        try {
            return find("shortCode", key).firstResult();
        } catch( IllegalArgumentException e) {
            ;
        }
        return null;

    }


    public Collection<Game> getActiveGames() {
        return list("status", GameState.started);
    }

    public Game startGame(String key) {
        Game g=getGame(key);
        g.status=GameState.started;
        persistOrUpdate(g);
        publisher.broadcast(g.id.toString());

        return g;
    }

    public Collection<Game> getGamesByDateReversed() {
        return listAll(Sort.by("created").descending());
    }

    public Game endGame(String key) {
        Game g=getGame(key);
        g.status=GameState.finished;
        persistOrUpdate(g);
        publisher.broadcast(g.id.toString());
        return g;

    }

    public Game broadcast(String key) {
        Game g=getGame(key);
        if(g!=null) {
            publisher.broadcast(g.id.toString());
        }
        return g;
    }

    public void revealClue(Game g, Cell c) {

        g.currentCell=c;
        g.status=GameState.showClue;
        persistOrUpdate(g);
        publisher.broadcast(g.id.toString());

    }


    public void revealAnswer(Game g, Cell c) {

        g.currentCell=c;
        g.status=GameState.showAnswer;
        persistOrUpdate(g);
        publisher.broadcast(g.id.toString());

    }

    public void score(Game g, Cell c, int winner, int loser) {
        c.used=true;

        if(winner>=0) {
            Team w= g.teams.get(winner);
            w.points+=c.value;
            w.correctAnswers++;
        }

        if(loser>=0) {
            Team l= g.teams.get(loser);
            l.points-=c.value;
            l.wrongAnswers++;
        }

        g.currentCell=null;
        g.status=GameState.started;
        persistOrUpdate(g);
        publisher.broadcast(g.id.toString());


    }

    public Game nextRound(String key) {

        Game g=getGame(key);
        int max=g.rounds.size();
        int next=g.currentRound+1;
        if(next<max) {
            g.currentRound=next;
            persistOrUpdate(g);
            publisher.broadcast(g.id.toString());
        }

        return g;
    }


    public Game previousRound(String key) {

        Game g=getGame(key);
        int next=g.currentRound-1;

        if(next>=0) {
            g.currentRound=next;
            persistOrUpdate(g);
            publisher.broadcast(g.id.toString());
        }

        return g;
    }

     */
}