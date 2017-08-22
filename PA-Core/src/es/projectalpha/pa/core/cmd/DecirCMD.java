package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Sounds;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class DecirCMD extends PACmd {

    public DecirCMD() {
        super("decir", Grupo.Usuario, Arrays.asList("w", "m", "msg", "mensaje"));
    }

    private static void sendPrivateMessage(PAUser target, PAUser from, String mensaje) {
        target.sendMessage("&2" + target.getName() + " &6-> &cYo &r: " + mensaje);
        from.sendMessage("&2Yo &6-> &c" + target.getName() + "&r: " + mensaje);
        target.sendSound(Sounds.LEVEL_UP);
    }

    @Override
    public void run(PAUser user, String lbl, String[] args) {
        PAUser target = PAServer.getUser(PACore.getInstance().getServer().getPlayer(args[0]));

        if (target.equals(user)) {
            user.sendMessage(PAData.CORE.getPrefix() + "&c¡No puedes enviarte mensajes a ti mismo!");
            return;
        }
        if (!target.isOnline()) {
            user.sendMessage(PAData.CORE.getPrefix() + "&cEl jugador está desconectado");
            return;
        }
        args[0] = "";
        sendPrivateMessage(target, user, Utils.buildString(args));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
