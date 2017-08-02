package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Messages;
import sun.security.krb5.internal.PAForUserEnc;

public class FlyCMD extends PACmd {

    public FlyCMD() {
        super("fly", Grupo.Mod, "volar");
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length > 1) {
            u.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.CORE));
            return;
        }
        u.toggleFly();
    }
}
