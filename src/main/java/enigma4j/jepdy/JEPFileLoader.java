package enigma4j.jepdy;

import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.model.Category;
import enigma4j.jepdy.model.Clue;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JEPFileLoader extends Enigma4JAbstractService {

    private static int lc = 0;

    @Transactional
    public static void loadJEPFile(File file) {

        LOG.info("loading JEP file " + file);
        if (file == null || file.exists() == false || file.isFile() == false) return;
        try (FileReader fr = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            JEPdyFileReader lr = new JEPdyFileReader(br);
            Category cat = null;

            while (true) {
                Object entry = readEntry(lr);
                if (entry == null) break;
                if (entry instanceof Category) {
                    cat = (Category) entry;
                } else if (entry instanceof Clue) {
                    cat.entries.add((Clue) entry);
                    cat.persist();
                }

            }


        } catch (IOException ioe) {
            LOG.infof(ioe, "exception loading file");
        }


    }

    private static Object readEntry(JEPdyFileReader br) throws IOException {

        while(true) {
            Map<String, String> o = readMap(br);
            if (o == null || o.isEmpty()) {
                LOG.info("No map");
                return null;
            }
            LOG.info("Map =" + o);
            if (o.containsKey("category")) {
                return createCategory(o);
            }
            if (o.containsKey("clue")) {
                return createClue(o);
            }
            LOG.errorf("Neither map around line %d" + o,br.getLineNumber());
        }

    }

    private static Clue createClue(Map<String, String> o) {
        Clue c = new Clue();
        c.clue = o.get("clue");

        if (o.containsKey("answer")) {
            c.answer = o.get("answer");
        }
        if (o.containsKey("score")) {
            c.value = toValue(o.get("score"));
        }
        c.persist();
        return c;
    }

    private static int toValue(String valuetext) {
        if (valuetext == null) return 100;
        valuetext = valuetext.trim();
        if (valuetext.equals("")) return 100;
        try {
            return Integer.parseInt(valuetext);
        } catch (NumberFormatException nfe) {
            ;
        }
        return 100;
    }


    private static Category createCategory(Map<String, String> o) {

        Category c = new Category();
        c.title = o.get("category");

        if (o.containsKey("description")) {
            c.description = o.get("description");
        }
        c.persist();
        return c;
    }


    private static Map<String, String> readMap(JEPdyFileReader br) throws IOException {

        Map<String, String> map = new HashMap<>();

        String[] parts = br.readLine(true);

        while (true) {



            if (parts != null && parts.length>1) {
                map.put(parts[0], parts[1]);
                parts = br.readLine(false);
                if (parts == null) return map;
                if (parts.length == 0) return map;

            } else {
                return map;
            }
        }
    }
}

