package enigma4j.jepdy.api;

import enigma4j.Enigma4JAbstractService;
import io.quarkus.logging.Log;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;


@Singleton
@Path("/jepy/public/api")

public class UpdateService extends Enigma4JAbstractService {


    private SseBroadcaster sseBroadcaster;


    @Context
    private Sse sse;


    @GET
    @Path("update")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink es, @QueryParam("game") String game) {


        LOG.infof("Registering update listener for game %s",game);
        if(sseBroadcaster==null) {
            LOG.info("sseBroadcaster is null");
            sseBroadcaster = sse.newBroadcaster();
            sseBroadcaster.onClose(eventSink -> LOG.infof("On close EventSink: %s", eventSink));
            sseBroadcaster.onError(
                    (eventSink, throwable) -> LOG.errorf("On Error EventSink: %s, Throwable: %s", eventSink, throwable));

        }

        sseBroadcaster.register(es);

    }


    @GET
    @Path("send")
    public void broadcast(@QueryParam("key") String message) {
        Log.infof("broadcast msg %s",message);

        if(sse!=null) {
            OutboundSseEvent event = sse.newEventBuilder().name(message).data(message).reconnectDelay(10000).build();
            LOG.infof("broadcasting %s",event.getName());
                sseBroadcaster.broadcast(event);
                    } else {
            LOG.info("unable to broadcast. No sse");
        }
    }

}