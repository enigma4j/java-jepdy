package enigma4j.jepdy.api;

import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.engine.GameStateBroadcaster;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/jepdy/public/api/websocket/{code}")
@ApplicationScoped
public class WebSocketAPI extends Enigma4JAbstractService {

    @Inject
    GameStateBroadcaster caster;

    @OnOpen
    public void onOpen(Session session,@PathParam("code") String code) {
        caster.addSession(session,code);
    }

    @OnClose
    public void onClose(Session session,@PathParam("code") String code) {
      caster.removeSession(session,code);
    }

    @OnError
    public void onError(Session session,@PathParam("code") String code, Throwable throwable) {
        caster.removeSession(session,code);
        LOG.error(throwable);

    }

    @OnMessage
    public void onMessage(String message) {
        LOG.infof("incoming message [%s]",message);
    }
}
