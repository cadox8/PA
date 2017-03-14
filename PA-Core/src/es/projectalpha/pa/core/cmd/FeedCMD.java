package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class FeedCMD extends PACmd {

    public FeedCMD(){
        super("feed", Grupo.Admin, "alimentar");
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length == 0){
            user.getPlayer().setFoodLevel(20);
            return;
        }
        if (args.length == 1){
            PAServer.getUser(args[0]).getPlayer().setFoodLevel(20);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
