package es.projectalpha.pa.toa.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.events.BagEvents;

public class MochilaCMD extends PACmd {

    public MochilaCMD() {
        super("mochila", Grupo.Usuario);
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length == 0) {
            BagEvents.loadInv(TOA.getPlayer(u.getPlayer()));
        }
    }
}
