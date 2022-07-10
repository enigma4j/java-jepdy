package enigma4j.jepdy;


import io.quarkus.logging.Log;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
    public class foo implements ContainerResponseFilter {
        @Override
        public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
                throws IOException {
            Log.infof("rq: %s %s",requestContext.getRequest().getMethod(),
            requestContext.getUriInfo().getPath());
           // responseContext.getHeaders().add("X-Content-Type-Options", "nosniff");
        }
    }

