package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class TeleportAskAllCMD extends PACmd {

    public TeleportAskAllCMD() {
        super("tpaall", Grupo.MOD, Arrays.asList("teleportaskall"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        final ArrayList<UUID> targets = new ArrayList<>();

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            PAUser target = PAServer.getUser(p);
            PAServer.addTeleportHereRequest(target.getUuid(), user.getUuid());
            if (PAServer.getTeleportRequests().containsKey(target.getUuid())) {
                PAServer.getTeleportRequests().remove(target.getUuid());
            }

            PAServer.getTeleportRequests().keySet().stream()
                .filter(u -> PAServer.getTeleportRequests().get(u).equals(target.getUuid()))
                .forEach(u -> PAServer.removeTeleportRequest(u)
            );

            target.sendMessage("");

            targets.add(target.getUuid());
        }
        user.sendMessage("");
        //Eliminar petición a los 2 minutos
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            targets.stream()
                .filter(t -> PAServer.getTeleportHereRequests().containsKey(t) && PAServer.getTeleportHereRequests().get(t).equals(user.getUuid()))
                .forEach(t -> PAServer.removeTeleportHereRequest(t)
            );
            user.sendMessage("");
        }, 120 * 20L);
    }
}
