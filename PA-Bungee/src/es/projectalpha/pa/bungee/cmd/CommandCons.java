package es.projectalpha.pa.bungee.cmd;

import es.projectalpha.pa.bungee.PABungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandCons extends Command {

    public CommandCons() {
        super("constructores", "bungee.cons", "cons");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = PABungee.getInstance().getProxy().getPlayer(sender.getName());

        if (p.getServer().getInfo().getName().equalsIgnoreCase("login")) return;

        if (args.length == 0) {
            p.connect(PABungee.getInstance().getCons());
        } else {
            sender.sendMessage(ChatColor.RED + "Este comando no necesita argumentos");
        }
    }
}
