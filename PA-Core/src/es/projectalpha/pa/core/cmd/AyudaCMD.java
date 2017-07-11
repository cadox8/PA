package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class AyudaCMD extends PACmd {

    public AyudaCMD() {
        super("ayuda", Grupo.Usuario, Arrays.asList("help"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if(args.length == 0) {
            user.sendDiv();
            user.sendMessage("&6Comandos de ProjectAlpha:");
            user.sendMessage("");
            user.sendDiv();
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
