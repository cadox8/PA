package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class InvSeeCMD extends PACmd {

    public InvSeeCMD() {
        super("invsee", Grupo.Mod, Arrays.asList("verinv"));
    }

    @Override
    public void run(PAUser user, String lbl, String[] args) {
        if (args.length < 1) {
            user.sendMessage(PAData.CORE.getPrefix() + "&cDebes poner un usuario");
            return;
        }

        PAUser target = PAServer.getUser(args[0]);
        if (target == null) {
            userNotOnline(user);
            return;
        }
        PlayerInventory inv = target.getPlayer().getInventory();
        user.getPlayer().openInventory(inv);
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
