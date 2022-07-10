package enigma4j.jepdy.model;

public class Team {

    public String name;
    public int points;

    public int correctAnswers;
    public int wrongAnswers;

    public Team() {

    }
    public Team(String name) {
        this.name=name;
    }
}
