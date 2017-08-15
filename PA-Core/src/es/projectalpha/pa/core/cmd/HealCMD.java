package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HealCMD extends PACmd {

    public HealCMD() {
        super("heal", Grupo.Mod, "curar");
    }

    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.heal();
            user.sendMessage(PAData.CORE.getPrefix() + "&6Te has curado");
            return;
        }

        if (args.length == 1) {
            PAUser target = PAServer.getUser(args[0]);
            if (target == null || !target.isOnline()) {
                user.sendMessage(PAData.CORE.getPrefix() + "&cEL jugador debe estar conectado");
                return;
            }
            target.heal();
            user.sendMessage(PAData.CORE.getPrefix() + "&6Has curado a &c" + target.getName());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
