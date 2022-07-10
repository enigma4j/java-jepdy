package enigma4j.jepdy.ui.forms;

import org.jboss.resteasy.reactive.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;


public class UploadForm {

    @FormParam("csvfile")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File file;

}
