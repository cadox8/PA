package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Messages;

public class FakeLeaveCMD extends PACmd {

    public FakeLeaveCMD(){
        super("fakeleave", Grupo.ADMIN, "fl");
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length == 0){
            user.sendMessage(Messages.help);
            return;
        }
        if (args.length == 1){
            plugin.getServer().getOnlinePlayers().forEach(p -> {
                PAServer.getUser(p).sendMessage(plugin.getConfig().getString("leave").replace("{0}", args[0]));
            });
            return;
        }
        if (args.length == 2){
            user.sendMessage(Messages.help);
        }
    }
}
