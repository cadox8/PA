package es.projectalpha.pa.antium.cmd;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;

public class LoginCMD extends PACmd {

    public LoginCMD(){
        super("login", Grupo.Usuario, "l", "entrar");
    }

    private String syntax = "&6Por favor, usa &c/login <contraseña>";

    @Override
    public void run(PAUser u, String label, String[] args){
        if (args.length == 0 || args.length >= 2){
            u.sendMessage(PAData.PAPlugins.ANTIUM.getPrefix() + syntax);
            return;
        }
        if (args.length == 1){
            PAAntium.getInstance().getPassManager().login(u, args[0]);
        }
    }
}
