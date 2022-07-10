package enigma4j.jepdy.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
public class Clue extends PanacheEntity {

    public String clue;
    public String answer;
    public int value;

}
