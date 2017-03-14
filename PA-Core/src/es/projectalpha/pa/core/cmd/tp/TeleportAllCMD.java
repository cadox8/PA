package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;
import java.util.List;

public class TeleportAllCMD extends PACmd {
    
    public TeleportAllCMD() {
        super("tpall", Grupo.ADMIN, Arrays.asList("teleportall"));
    }
    
    @Override
    public void run(PAUser user, String label, String[] args) {
        PAUser target = user;
        if (args.length != 0) {
            target = PAServer.getUser(plugin.getServer().getPlayer(args[0]));
            if (!target.isOnline()) {
                userNotOnline(user);
                return;
            }
        }
        
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.teleport(target.getPlayer().getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        }

        user.sendMessagePrefix("&6Todos los jugadores han sido teletransportados");
    }
    
    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
