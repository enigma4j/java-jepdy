package enigma4j.jepdy.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Category extends PanacheEntity {


    public String title;
    public String description;
    @OneToMany(targetEntity=Clue.class, fetch=FetchType.EAGER)
    public List<Clue> entries = new LinkedList<>();

    public int clueCount() {
        if(entries==null) return 0;
        return entries.size();
    }

    public List<Clue> getSortedEntries() {
        List<Clue> clues=new LinkedList<>();
        clues.addAll(entries);
        Collections.sort(clues,new ClueComparator());
        return clues;
    }
}
