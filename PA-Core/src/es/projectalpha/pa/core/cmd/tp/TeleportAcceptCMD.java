package es.projectalpha.pa.core.cmd.tp;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;

public class TeleportAcceptCMD extends PACmd {
    
    public TeleportAcceptCMD() {
        super("tpaccept", Grupo.Craftero, Arrays.asList("teleportaccept", "tpaacept"));
    }
    
    @Override
    public void run(PAUser user, String label, String[] args) {
        if (PAServer.getTeleportHereRequests().containsKey(user.getUuid())) {
            PAUser target = PAServer.getUser(PAServer.getTeleportHereRequests().get(user.getUuid()));
            if (target == null) {
                userNotOnline(user);
                return;
            }

            user.getPlayer().teleport(target.getPlayer(), PlayerTeleportEvent.TeleportCause.COMMAND);
            target.sendMessagePrefix("&6Teletransportado a &c" + user.getName());
            PAServer.removeTeleportHereRequest(target.getUuid());

        } else if (!PAServer.getTeleportRequests().containsKey(user.getUuid())) {
            user.sendMessagePrefix("&cNo tienes peticiones de TP pendientes");
        } else {
            PAUser target = PAServer.getUser(PAServer.getTeleportRequests().get(user.getUuid()));
            if (target == null) {
                user.sendMessage("");
            } else {
                target.getPlayer().teleport(user.getPlayer(), PlayerTeleportEvent.TeleportCause.COMMAND);
                target.sendMessagePrefix("&6Teletransportado a &c" + user.getName());
                PAServer.removeTeleportRequest(user.getUuid());
            }
        }
    }  
}
