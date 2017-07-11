package es.projectalpha.pa.antium.cmd;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;

public class RegisterCMD extends PACmd {

    public RegisterCMD(){
        super("register", Grupo.Usuario, "r", "registrar");
    }

    private String syntax = "&6Por favor, usa &c/register <contraseña> <contraseña>";

    @Override
    public void run(PAUser u, String label, String[] args){
        if (args.length == 0 || args.length >= 3){
            u.sendMessage(PAData.PAPlugins.ANTIUM.getPrefix() + syntax);
            return;
        }
        if (args.length == 1){
            PAAntium.getInstance().getPassManager().register(u, args[0], args[1]);
        }
    }
}
