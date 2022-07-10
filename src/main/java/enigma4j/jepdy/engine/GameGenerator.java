package enigma4j.jepdy.engine;

import enigma4j.jepdy.model.*;
import enigma4j.jepdy.ui.forms.NewGameForm;
import io.quarkus.logging.Log;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.*;


@ApplicationScoped

public class GameGenerator {

    private static final Logger log = Logger.getLogger(GameGenerator.class);

    private static FriendlyIDGenerator codeGenerator;
    static {

        try {
            codeGenerator=new FriendlyIDGenerator();
        } catch (IOException e) {
            log.error(e);
        }
    }



    private static int categoriesPerRound=4;

    private Random r;

    /**
     * Generate event from provided form.
     * @param f
     */
    public static Game generate(NewGameForm f) {

        GameGenerator generator = new GameGenerator();
        return generator.gen(f);
    }

    private Game gen(NewGameForm f) {
        // a game is built up of random categories and random questions guided by value
        // each category should provide 5 clues. Categories with less that 5 are ignored
        // unless there is no option (ie many rounds wanted)
        // if there are not enough categories for the required number of rounds the
        // number of rounds is reduced.

        List<Category> categories=Category.listAll();


        log.infof("seed   : %d",f.seed);
        log.infof("rounds : %d",f.rounds);
        log.infof("categories per round : %d",categoriesPerRound);
        log.infof("categories required / available : %d/%d",f.rounds*categoriesPerRound,categories.size());

        r=new Random(f.seed);

        if(f.rounds*4>categories.size()) {
            f.rounds = categories.size() % categoriesPerRound;
            log.infof("rounds reduced to %d", f.rounds);
        }

        // convert incoming team ids to real teams..
        Game e=new Game(codeGenerator.generate(), f.name,f.teams,f.rounds);

        List<Category> backupCategories=new LinkedList<>();

        for(int i=0;i<f.rounds;i++) {

            Round round=createRound(categories,backupCategories);
            e.rounds.add(round);

        }

        for(int t=0;t<f.teams;t++) {
            Team team=new Team(""+(t+1));
            e.teams.add(team);
        }
        return e;
    }


    private  Round createRound( List<Category> categories, List<Category> backupCategories) {

        Round round=new Round();
        round.rows=new ArrayList<>(categoriesPerRound);

        for(int col=0;col<categoriesPerRound;col++) {
            if(categories.isEmpty()) {
                categories=backupCategories;
                backupCategories=null;
                if(categories.isEmpty()) break; // run out of stuff
            }
            Category cat = categories.remove(r.nextInt(categories.size()));
            if (cat.clueCount() < 4 && backupCategories!=null) {
                backupCategories.add(cat);
                col--; // go back one
            } else {
                Row row=new Row();
                row.cells=new ArrayList<>(4);
                round.rows.add(row);
                row.category=cat.title;
                // select 4 clues
                // sort the entries by size
                List<Clue> clues=cat.getSortedEntries();
                // divide the group into 4 sections and take one randomly from each
                int offset=0;
                int div=clues.size()/4;
                for(int c=0;c<4;c++) {
                    int q=offset+r.nextInt(div);
                    Clue ce=clues.get(q);
                    Cell cell=new Cell();
                    row.cells.add(cell);
                    cell.clue=ce.clue;
                    cell.answer=ce.answer;
                    cell.used=false;
                    cell.value=(c+1)*100;
                    offset+=div;
                    log.infof("added row=%d/ col=%d",col,c);
                }

            }
        }

        return round;
    }
}