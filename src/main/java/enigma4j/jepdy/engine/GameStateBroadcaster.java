package enigma4j.jepdy.engine;

import enigma4j.Enigma4JAbstractService;
import io.quarkus.logging.Log;
import io.vertx.core.impl.ConcurrentHashSet;

import javax.inject.Singleton;
import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class GameStateBroadcaster extends Enigma4JAbstractService {

    Set<Session> sessions = new ConcurrentHashSet<>();
    Map<String,Set<Session>> bycode=new ConcurrentHashMap<>();

    public void broadcast(String message) {
        LOG.infof("broadcast msg %s",message);

        sessions.forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

    public synchronized  void addSession(Session s,String code) {
        LOG.infof("new session for %s [%s]",code,s);
        if(code==null) return;

        sessions.add(s);
        Set<Session> subgroup=bycode.get(code);
        if(subgroup==null) {
            LOG.infof("added new group for code %s",code);
            subgroup=new ConcurrentHashSet<>();
           bycode.put(code,subgroup);
        }
        subgroup.add(s);
    }

    public void removeSession(Session s,String code ) {
        LOG.infof("remove session for %s [%s]",code,s);
        sessions.remove(s);
        Set<Session> subgroup=bycode.get(code);
        if(subgroup!=null) {
            if(subgroup.contains(s)) {
                subgroup.remove(s);
                LOG.infof("removed session from subgroup");
            }
        } else {
            LOG.infof("no subgroup for code [%s]",code);
        }
    }
}
