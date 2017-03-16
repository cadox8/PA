package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;
import java.util.List;

public class TeleportHereCMD extends PACmd {

    public TeleportHereCMD() {
        super("tphere", Grupo.Admin, Arrays.asList("teleporthere", "s"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("");
            return;
        }

        PAUser target = PAServer.getUser(plugin.getServer().getPlayer(args[0]));
        if (!target.isOnline()) {
            userNotOnline(user);
            return;
        }

        target.getPlayer().teleport(user.getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        user.sendMessage("");
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }

}
