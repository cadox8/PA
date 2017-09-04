package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;
import java.util.List;

public class AdminChatCMD extends PACmd {

    public AdminChatCMD() {
        super("a", Grupo.Builder, Arrays.asList("adminchat", "ac"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length > 0) {
            String message = Utils.buildString(args);
            Utils.sendAdminMsg(user.getName(), message);
        } else {
            user.toggleAdminChat();
        }
    }

    @Override
    public void run(ConsoleCommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            String message = Utils.buildString(args);
            Utils.sendAdminMsg("Consola", message);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
