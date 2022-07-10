package enigma4j.jepdy.engine;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Singleton
public class FriendlyIDGenerator {

    private String[] words=null;
    private Random r=new Random();

    public FriendlyIDGenerator() throws IOException {

        List<String> data=new LinkedList<>();
        try (InputStream in=getClass().getResourceAsStream("/words.txt")) {
            BufferedReader br=new BufferedReader(new InputStreamReader(in));

            while(true) {
                String l=br.readLine();
                if(l==null) break;
                l=l.trim().toLowerCase();
                String checked=l.replaceAll("[^a-zA-Z0-9]", "");
                if(checked.equals(l)) {
                    data.add(l);
                }


            }
        }

        words=data.toArray(new String[0]);

    }

    String generate() {

        int x=r.nextInt(words.length);
        int y=r.nextInt(words.length);
        return words[x]+"-"+words[y];
    }
}
