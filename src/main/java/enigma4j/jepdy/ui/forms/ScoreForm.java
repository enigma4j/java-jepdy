package enigma4j.jepdy.ui.forms;

import javax.ws.rs.FormParam;

public class ScoreForm {


    @FormParam("code")
    public String code;

    @FormParam("round")
    public int round;

    @FormParam("row")
    public int row;

    @FormParam("cell")
    public int cell;

    @FormParam("winner")
    public int winner;

    @FormParam("loser")
    public int loser;

}