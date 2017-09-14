package es.projectalpha.pa.lobby.cmd.antium;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;

public class SetMailCMD extends PACmd {

    public SetMailCMD() {
        super("setmail", Grupo.Usuario, "email");
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length > 1) {
            u.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.ANTIUM));
            return;
        }
        String email = args[0];

        if (PACore.getInstance().getMysql().setEmail(u.getName(), email)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Email cambiado correctamente");
        } else {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&cError al cambiar el email");
        }
    }
}
