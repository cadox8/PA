package es.projectalpha.pa.toa.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.races.Race;

public class RaceCMD extends PACmd {

    public RaceCMD() {
        super("raza", Grupo.Usuario);
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length == 1) {
            TOA.getPlayer(u.getPlayer()).setRace(Race.parseRace(Utils.isInt(args[0]) ? Integer.parseInt(args[0]) : 0));
        }
    }
}
