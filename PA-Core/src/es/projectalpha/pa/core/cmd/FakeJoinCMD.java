package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.api.PAUser;

public class FakeJoinCMD extends PACmd {

    public FakeJoinCMD(){
        super("fakejoin", Grupo.ADMIN, "fj");
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length == 0){
            user.sendMessage(Messages.help);
            return;
        }
        if (args.length == 1){
            plugin.getServer().getOnlinePlayers().forEach(p -> {
                PAServer.getUser(p).sendMessage(plugin.getConfig().getString("join").replace("{0}", args[0]));
            });
            return;
        }
        if (args.length == 2){
            user.sendMessage(Messages.help);
        }
    }
}
