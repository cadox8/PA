package es.projectalpha.pa.core.cmd.tp;


import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;

import java.util.Arrays;

public class TeleportDenyCMD extends PACmd {
    
    public TeleportDenyCMD() {
        super("tpadeny", Grupo.Craftero, Arrays.asList("teleportdeny"));
    }
    
    @Override
    public void run(PAUser user, String label, String[] args) {
        if (PAServer.getTeleportRequests().containsKey(user.getUuid()) || PAServer.getTeleportHereRequests().containsKey(user.getUuid())) {
            PAUser t1 = PAServer.getUser(PAServer.getTeleportRequests().get(user.getUuid()));
            PAUser t2 = PAServer.getUser(PAServer.getTeleportHereRequests().get(user.getUuid()));
            user.sendMessage("");
            if (t1 != null) t1.sendMessage("");
            if (t2 != null) t2.sendMessage("");

            PAServer.removeTeleportRequest(user.getUuid());
            PAServer.removeTeleportHereRequest(user.getUuid());
        } else {
            user.sendMessage("");
        }
    }  
}
