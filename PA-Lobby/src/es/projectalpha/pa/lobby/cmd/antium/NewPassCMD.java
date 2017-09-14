package es.projectalpha.pa.lobby.cmd.antium;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;

public class NewPassCMD extends PACmd {

    public NewPassCMD() {
        super("changepass", Grupo.Usuario, "cp", "newpass", "cambiarcontraseña", "nuevacontraseña");
    }

    public void run(PAUser u, String label, String... args) {
        if (args.length > 1) {
            u.sendMessage(Messages.getMessage(Messages.BUFF_ARGS, PAData.ANTIUM));
            return;
        }
        String pass = args[0];

        if (PACore.getInstance().getMysql().changePassword(u.getName(), pass)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Contraseña cambiada correctamente");
        } else {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&cError al cambiar la contraseña");
        }
    }
}
