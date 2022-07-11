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

    public int comparePoints(Team winner) {
        if(winner==null) return 1;  // we are higher

        if(winner.points>points) return -1; // the win
        if(winner.points>points) return 1; // we win

        // equal points
        if(winner.wrongAnswers<wrongAnswers) return -1; // they win - less bad answers
        if(winner.wrongAnswers>wrongAnswers) return 1; // we  win - less bad answers
        if(winner.correctAnswers>correctAnswers) return -1;  // they win answered more questions
        if(winner.correctAnswers<correctAnswers) return 1;  // we win answered more questions

        return 0;  //exactly the same

    }
}
