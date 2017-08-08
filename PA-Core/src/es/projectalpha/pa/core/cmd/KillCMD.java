package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class KillCMD extends PACmd {

    public KillCMD() {
        super("kill", Grupo.Admin, "matar");
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.getPlayer().damage(user.getPlayer().getHealth());
        }
        if (args.length == 1) {
            PAUser target = PAServer.getUser(args[0]);

            if (target == null || !target.isOnline()) {
                userNotOnline(user);
                return;
            }
            target.getPlayer().damage(target.getPlayer().getHealth());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
