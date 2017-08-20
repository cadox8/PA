package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TeleportAskCMD extends PACmd {

    public TeleportAskCMD() {
        super("tpa", Grupo.Builder, Arrays.asList("teleportask"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage(Messages.getMessage(Messages.NEED_ARGS, PAData.CORE));
            return;
        }

        PAUser target = PAServer.getUser(plugin.getServer().getPlayerExact(args[0]));
        if (!target.isOnline()) {
            userNotOnline(user);
            return;
        }

        PAServer.addTeleportRequest(target.getName(), user.getName());
        if (PAServer.getTeleportHereRequests().containsKey(target.getName())) {
            PAServer.getTeleportHereRequests().remove(target.getName());
        }

        PAServer.getTeleportHereRequests().keySet().stream()
                .filter(u -> PAServer.getTeleportHereRequests().get(u).equals(target.getName()))
                .forEach(u -> PAServer.removeTeleportHereRequest(u));

        user.sendMessage("Has enviado una petición de teletransporte");
        target.sendMessage("Te han enviado una petición de teletransporte");

        //Eliminar petición a los 2 minutos
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (PAServer.getTeleportRequests().containsKey(target.getName()) && PAServer.getTeleportRequests().get(target.getName()).equals(user.getName())) {
                PAServer.removeTeleportRequest(target.getName());
                user.sendMessage("La petición ha caducado");
            }
        }, 120 * 20L);
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
