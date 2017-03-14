package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;

import java.util.Arrays;

public class AfkCMD extends PACmd {

    public AfkCMD() {
        super("afk", Grupo.Usuario, Arrays.asList("away", "aefecá", "aefeka", "adefeka"));
    }

    @Override
    public void run(PAUser user, String lbl, String[] args) {
        if (PAServer.afkMode.contains(user)) {
            plugin.getServer().getOnlinePlayers().forEach(p -> PAServer.getUser(p).sendMessagePrefix("&3" + user.getName() + " &6ya no está afk"));
            PAServer.afkMode.remove(user);
        } else {
            PAServer.afkMode.add(user);
            plugin.getServer().getOnlinePlayers().forEach(p -> PAServer.getUser(p).sendMessagePrefix("&3" + user.getName() + " &6está afk"));
        }
    }
}
