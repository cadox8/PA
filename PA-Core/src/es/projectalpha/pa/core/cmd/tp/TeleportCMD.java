package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;
import java.util.List;

public class TeleportCMD extends PACmd {

    public TeleportCMD() {
        super("tp", Grupo.Builder, Arrays.asList("teleport"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        switch (args.length) {
            case 1: //del sender a otra persona
                PAUser target = PAServer.getUser(plugin.getServer().getPlayer(args[0]));
                if (!target.isOnline() || target == null) {
                    userNotOnline(user);
                    return;
                }
                user.getPlayer().teleport(target.getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
                user.sendMessage(PAData.CORE.getPrefix() + "&6Teletransportado a &c" + target.getName());
                break;
            case 2: //tp de un user a otro
                PAUser from = PAServer.getUser(plugin.getServer().getPlayer(args[0]));
                PAUser to = PAServer.getUser(plugin.getServer().getPlayer(args[1]));

                if (!from.isOnline() || from == null || !to.isOnline() || to == null) {
                    userNotOnline(user);
                    return;
                }

                from.getPlayer().teleport(to.getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
                user.sendMessage(PAData.CORE.getPrefix() + "&c" + user.getName() + " &6se ha teletransportado hacia tí");
                from.sendMessage(PAData.CORE.getPrefix() + "&6Teletransportado a &c" + user.getName());
                break;
            default:
                user.sendMessage(ChatColor.RED + "Error :D");
                break;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
