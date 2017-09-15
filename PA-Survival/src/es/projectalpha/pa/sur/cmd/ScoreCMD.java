package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;

public class ScoreCMD extends PACmd {

    public ScoreCMD() {
        super("score", Grupo.Usuario, "board", "sb");
    }

    public void run(PAUser u, String label, String... args) {
        SurvivalUser su = PASurvival.getPlayer(u.getPlayer());

        su.setScore(!su.isScore());
        String state = su.isScore() ? "&3Activado" : "&cDesactivado";
        su.sendMessage(PAData.SURVIVAL.getPrefix() + "&6Tu Scoreboard se ha " + state);
    }
}
