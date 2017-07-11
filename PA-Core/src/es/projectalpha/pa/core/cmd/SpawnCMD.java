package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;

public class SpawnCMD extends PACmd {

    public SpawnCMD(){
        super("spawn", Grupo.Usuario, "");
    }

    @Override
    public void run(PAUser user, String label, String[] args){
        if (args.length == 0) {
            if (plugin.getConfig().getString("spawn").equalsIgnoreCase("NONE")) return;
            user.teleport(Utils.stringToLocation(plugin.getConfig().getString("spawn")));
            user.sendMessage(PAData.PAPlugins.CORE.getPrefix() + "&6Has sido teletransportado al spawn");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("set")) {
                if (!user.isOnRank(Grupo.Admin)) return;
                plugin.getConfig().set("spawn", Utils.locationToString(user.getPlayer().getLocation()));
                plugin.saveConfig();

                user.sendMessage(PAData.PAPlugins.CORE.getPrefix() + "&2Has puesto el punto de spawn satisfactoriamente");

                return;
            }

            PAUser target = PAServer.getUser(args[0]);

            if (target == null || !target.isOnline()){
                userNotOnline(user);
                return;
            }

            if (plugin.getConfig().getString("spawn").equalsIgnoreCase("NONE")) return;
            target.teleport(Utils.stringToLocation(plugin.getConfig().getString("spawn")));
            target.sendMessage(PAData.PAPlugins.CORE.getPrefix() + "&6Has sido teletransportado al spawn");
            user.sendMessage(PAData.PAPlugins.CORE.getPrefix() + "&6Has llevado a &c" + target.getName() + " &6al spawn");
        }
        if (args.length >= 2) {
            user.sendMessage(Messages.getMessage(Messages.INFO, PAData.PAPlugins.CORE));
        }
    }
}
