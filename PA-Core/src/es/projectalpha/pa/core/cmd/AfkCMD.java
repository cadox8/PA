package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;

import java.util.Arrays;

public class AfkCMD extends PACmd {

    public AfkCMD() {
        super("afk", Grupo.Usuario, Arrays.asList("away", "aefecá", "aefeka", "adefeka"));
    }

    @Override
    public void run(PAUser user, String lbl, String[] args) {
        if (PAServer.afkMode.contains(user)) {
            Utils.broadcastMsg(Messages.getMessage(Messages.NO_AFK, PAData.CORE, "%player%", user.getName()));
            PAServer.afkMode.remove(user);
        } else {
            PAServer.afkMode.add(user);
            Utils.broadcastMsg(Messages.getMessage(Messages.AFK, PAData.CORE, "%player%", user.getName()));
        }
    }
}
