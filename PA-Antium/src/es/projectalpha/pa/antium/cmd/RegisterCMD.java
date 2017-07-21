package es.projectalpha.pa.antium.cmd;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;

public class RegisterCMD extends PACmd {

    private String syntax = "&6Por favor, usa &c/register <contraseña> <contraseña>";

    public RegisterCMD() {
        super("register", Grupo.Usuario, "r", "registrar");
    }

    @Override
    public void run(PAUser u, String label, String[] args) {
        if (args.length == 0 || args.length >= 3) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + syntax);
            return;
        }
        if (args.length == 2) {
            PAAntium.getInstance().getPassManager().register(u, args[0], args[1]);
        }
    }
}
