package enigma4j.jepdy;

import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class JEPdyFileReader {

    public static final Logger LOG = Logger.getLogger(JEPdyFileReader.class);
    private BufferedReader br;
    private int lc=0;
    public JEPdyFileReader(BufferedReader br) {
        this.br=br;
    }

    public String[] readLine(boolean skipblank) throws IOException {
        while(true) {
        String line=br.readLine();
        LOG.debugf("%d line read [%s]",lc,line);
        if(line==null) return null;
        lc++;
        line=line.replaceAll("[^a-zA-Z0-9,\"':&#\\-+\\\\/\\.]", " ");
        line=line.trim();
        if(line.startsWith("#")) continue;
        if(skipblank && line.equals("")) continue;
        return split(line);

    }
}

    private String[] split(String line) {

        if(line==null) return null;
        LOG.debugf("%d line l=%d split in [%s] ",lc,line.length(),line);

        String[] parts=new String[0];
        int colon=line.indexOf(':');
        if(colon>0) {
            parts=new String[]{"",""};
            parts[0]=line.substring(0,colon);
            parts[1]=line.substring(colon+1);
            LOG.debugf("%d line split [%s][%s]",lc,parts[0],parts[1]);
        } else {
            LOG.debugf("%d line no colon found",lc);
        }

        return parts;
    }

    public int getLineNumber() {
        return lc;
    }
}
