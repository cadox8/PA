package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.core.utils.Utils;

public class HelpOPCMD extends PACmd {

    public HelpOPCMD() {
        super("helpop", Grupo.Usuario, "hp");
    }

    private final Cooldown temp = new Cooldown(30);

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (temp.isCoolingDown(user.getName())){
            user.sendMessage(PAData.CORE.getPrefix() + "&cEl comando está en cooldown");
            return;
        }

        String message = Utils.buildString(args);
        hp(user, message);
        temp.setOnCooldown(user.getPlayer());
        user.sendMessage(PAData.CORE.getPrefix() + "&2El mensaje ha sido enviado al Staff");
    }

    private void hp(PAUser user, String msg){
        plugin.getServer().getOnlinePlayers().forEach(p -> {
            PAUser u = PAServer.getUser(p);
            if (u.isOnRank(Grupo.Admin)) {
                u.sendMessage("&4AYUDA: &3" + user.getName() + "&r: " + msg);
            }
        });
    }
}
