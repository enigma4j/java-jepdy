package enigma4j.jepdy.ui.forms;


import javax.ws.rs.FormParam;

public class NewGameForm {

    @FormParam("name")
    public String name;

    @FormParam("rounds")
    public int rounds;

    @FormParam("seed")
    public int seed;

    @FormParam("teams")
    public int teams;

}