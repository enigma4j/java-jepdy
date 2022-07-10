package enigma4j;

import io.quarkus.oidc.OidcSession;
import io.quarkus.oidc.UserInfo;
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



   protected Map<String, Object> getUser() {
        Map<String,Object> userdata=new HashMap<>();
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
