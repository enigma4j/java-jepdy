package enigma4j;

import io.quarkus.oidc.OidcSession;
import io.quarkus.oidc.UserInfo;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.Quarkus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public abstract class Enigma4JAbstractService {

  public static final Logger LOG = Logger.getLogger(Enigma4JAbstractService.class);

    @Inject
   public UserInfo userinfo;

    @Inject
    public OidcSession oidcSession;

    @ConfigProperty(name = "quarkus.security.auth.enabled-in-dev-mode")
    String devMode;

   protected Map<String, Object> getUser() {
        Map<String,Object> userdata=new HashMap<>();

        LOG.warnf("devmode=",devMode);

        if(devMode.equals("false")) {
            userdata.put("registered",true);
            userdata.put("login","enigma4j");
            return userdata;
        }

        if(oidcSession==null || userinfo.getJsonObject()==null) {
            userdata.put("registered",false);
            return userdata;
        }

        userdata.put("registered",true);
        if(userinfo.contains("login")) {
            String login=userinfo.getString("login");
            userdata.put("login",login);
        }

        return userdata;
    }

}
