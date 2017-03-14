package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TeleportAskCMD extends PACmd {
    
    public TeleportAskCMD() {
        super("tpa", Grupo.MOD, Arrays.asList("teleportask"));
    }
    
    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("");
            return;
        }
        
        PAUser target = PAServer.getUser(plugin.getServer().getPlayerExact(args[0]));
        if (!target.isOnline()) {
            userNotOnline(user);
            return;
        }
        
        PAServer.addTeleportRequest(target.getUuid(), user.getUuid());
        if (PAServer.getTeleportHereRequests().containsKey(target.getUuid())) {
            PAServer.getTeleportHereRequests().remove(target.getUuid());
        }

        PAServer.getTeleportHereRequests().keySet().stream()
            .filter(u -> PAServer.getTeleportHereRequests().get(u).equals(target.getUuid()))
            .forEach(u -> PAServer.removeTeleportHereRequest(u));
        
        user.sendMessage("");
        target.sendMessage("");

        //Eliminar petición a los 2 minutos
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (PAServer.getTeleportRequests().containsKey(target.getUuid()) && PAServer.getTeleportRequests().get(target.getUuid()).equals(user.getUuid())) {
                PAServer.removeTeleportRequest(target.getUuid());
                user.sendMessage("");
            }
        }, 120 * 20L);
    }
    
    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
