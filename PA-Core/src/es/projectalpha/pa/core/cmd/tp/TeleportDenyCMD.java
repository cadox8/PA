package es.projectalpha.pa.core.cmd.tp;


import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;

import java.util.Arrays;

public class TeleportDenyCMD extends PACmd {

    public TeleportDenyCMD() {
        super("tpadeny", Grupo.Usuario, Arrays.asList("teleportdeny"));
    }

   @Override
    public void run(PAUser user, String label, String[] args) {
        if (PAServer.getTeleportRequests().containsKey(user.getName()) || PAServer.getTeleportHereRequests().containsKey(user.getName())) {
            PAUser t1 = PAServer.getUser(PAServer.getTeleportRequests().get(user.getName()));
            PAUser t2 = PAServer.getUser(PAServer.getTeleportHereRequests().get(user.getName()));
            user.sendMessage(PAData.CORE.getPrefix() + "&6Has denegado la petición");
            if (t1 != null) t1.sendMessage(PAData.CORE.getPrefix() + "&c" + user.getName() + " &6ha denagado la petición de teletransporte");
            if (t2 != null) t2.sendMessage(PAData.CORE.getPrefix() + "&c" + user.getName() + " &6ha denagado la petición de teletransporte");

            PAServer.removeTeleportRequest(user.getName());
            PAServer.removeTeleportHereRequest(user.getName());
        } else {
            user.sendMessage("");
        }
    }
}
