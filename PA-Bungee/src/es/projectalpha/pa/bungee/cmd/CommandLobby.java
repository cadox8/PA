package es.projectalpha.pa.bungee.cmd;

import es.projectalpha.pa.bungee.PABungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandLobby extends Command {

    public CommandLobby() {
        super("lobby", "", "");
    }

    public void execute(CommandSender sender, String[] args) {
        if (PABungee.getInstance().getProxy().getPlayer(sender.getName()).getServer().getInfo().getName().equalsIgnoreCase("login")) return;
        if (args.length == 0) {
            PABungee.getInstance().getProxy().getPlayer(sender.getName()).connect(PABungee.getInstance().getLobby());
        } else {
            sender.sendMessage(ChatColor.RED + "Este comando no necesita argumentos");
        }
    }
}
