package enigma4j.jepdy.engine;


import enigma4j.Enigma4JAbstractService;
import enigma4j.jepdy.JEPFileLoader;
import enigma4j.jepdy.model.Category;
import enigma4j.jepdy.model.Clue;
import enigma4j.jepdy.model.Game;
import enigma4j.jepdy.model.GameState;
import enigma4j.jepdy.ui.forms.NewGameForm;
import io.quarkus.runtime.Startup;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Startup

public class PrepDB extends Enigma4JAbstractService {


    public static File r=new File(System.getProperty("user.dir")+"/data/clues.txt");

    public PrepDB() {

        if(r==null) return;

        LOG.info("starting prep db");

        JEPFileLoader.loadJEPFile(r);

        r=null;

        long cats=Category.count();
        long clues= Clue.count();

        LOG.infof("cats=%d, clues=%d",cats,clues);

        NewGameForm f=new NewGameForm();

        f.name="test game";
        f.rounds=6;
        f.teams=10;
        f.seed=1;

        Game g=GameGenerator.generate(f);
        g.start();

        g.persist();

        LOG.infof("added test game with shortcode %s",g.shortCode);

    }
}
