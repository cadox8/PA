package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NickCMD extends PACmd {

    public NickCMD() {
        super("nick", Grupo.VIP, "nickname");
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        String nick;
        switch (args.length) {
            case 1:
                nick = args[0];
                break;
            default:
                String s = user.getUserData().getNickname().equalsIgnoreCase("") ? user.getName() : user.getUserData().getNickname();
                user.sendMessage(PAData.CORE.getPrefix() + "&6Tu nick es " + s);
                return;
        }
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.equals(user.getPlayer())) continue;
            if (p.getName().equalsIgnoreCase(ChatColor.stripColor(nick)) || PAServer.getUser(p).getDisplayName().equalsIgnoreCase(ChatColor.stripColor(nick))) {
                user.sendMessage(PAData.CORE.getPrefix() + "&cEl usuario &6" + p.getName() + " &cya tiene ese nick");
                return;
            }
        }

        nick = Utils.colorize(nick);
        user.getPlayer().setDisplayName(nick.equalsIgnoreCase("off") ? user.getName() : nick);
        user.getUserData().setNickname(nick.equalsIgnoreCase("off") ? null : nick);
        user.sendMessage(PAData.CORE.getPrefix() + "&6Tu nuevo nick es " + nick);

        user.save();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
