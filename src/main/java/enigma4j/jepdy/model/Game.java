package enigma4j.jepdy.model;

import io.quarkus.logging.Log;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@MongoEntity(collection="games")
public class Game extends PanacheMongoEntity {

    public String shortCode;
    public ArrayList<Team> teams=new ArrayList<>();
    public ArrayList<Round> rounds=new ArrayList<>();
    public GameState status=GameState.ready;
    public Date started;
    public String name;
    public Cell currentCell;
    public int currentRound = 0;

    public Game() {

    }
    public Game(String sc,String name,int team_count,int round_count) {

        shortCode=sc;
        this.name=name;
        teams=new ArrayList<>(team_count);
        rounds=new ArrayList<>(round_count);
        status= GameState.ready;
        currentRound=0;

    }

    public static Game findByCode(String code){
        Game g=find("shortCode", code).firstResult();
        Log.infof("request for game by code [%s] = %s",code,g);
        return g;

    }

    public static Collection<Game> getActiveGames() {

        return list("status", GameState.started);
    }

    public void start() {
        status= GameState.started;
        started=Date.from(Instant.now());
    }

    public void score(Cell c, int winner, int loser) {
        c.used=true;

        if(winner>=0) {
            Team w= teams.get(winner);
            w.points+=c.value;
            w.correctAnswers++;
        }

        if(loser>=0) {
            Team l= teams.get(loser);
            l.points-=c.value;
            l.wrongAnswers++;
        }

        currentCell=null;
        status=GameState.started;
        persistOrUpdate();



    }
}
