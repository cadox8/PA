package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PingCMD extends PACmd {

    public PingCMD() {
        super("ping", Grupo.Usuario, Arrays.asList("pong"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 1 && user.isOnRank(Grupo.Mod)) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                userNotOnline(user);
                return;
            }
            user.sendMessage(PAData.CORE.getPrefix() + "Ping de &2" + target.getDisplayName() + "&r: " + format(PAServer.getUser(target).getPing()));
            return;
        }
        if (user.getPing() < 0) {
            user.sendMessage(PAData.CORE.getPrefix() + "&c¡Ha ocurrido un error obteniendo tu ping! Intentalo más tarde");
            return;
        }
        user.sendMessage(PAData.CORE.getPrefix() + "Tu Ping: " + format(user.getPing()));
    }

    private String format(int ping) {
        String color;
        if (ping <= 130) {
            color = "&a";
        } else if (ping <= 250) {
            color = "&e";
        } else if (ping <= 500) {
            color = "&c";
        } else {
            color = "&4";
        }
        return color + ping;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
