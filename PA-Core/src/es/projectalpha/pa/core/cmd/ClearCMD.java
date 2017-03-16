package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class ClearCMD extends PACmd {

    public ClearCMD() {
        super("clear", Grupo.Mod, Arrays.asList("limpiar", "limpia"));
    }

    //TODO: Mensages

    @Override
    public void run(PAUser user, String lbl, String[] args) {
        if (args.length < 1) {
            user.sendMessagePrefix("&6Has borrado tu inventario");
            int invsize = user.getPlayer().getInventory().getSize() - 5;
            for (int i = 0; i < invsize; i++) {
                user.getPlayer().getInventory().clear(i);
            }
            return;
        }

        PAUser target = PAServer.getUser(plugin.getServer().getPlayer(args[0]));

        if (target == null || !target.isOnline()){
            user.sendMessagePrefix("&cEL jugador debe estar conectado");
            return;
        }

        target.sendMessagePrefix("&6Tu inventario ha sido borrado");
        user.sendMessagePrefix("&6Has borrado el inventario de &c" + target.getName());

        int invsize = user.getPlayer().getInventory().getSize() - 5;

        for (int i = 0; i < invsize; i++) {
            target.getPlayer().getInventory().clear(i);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
