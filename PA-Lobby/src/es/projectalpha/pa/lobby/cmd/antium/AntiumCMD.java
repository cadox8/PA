package es.projectalpha.pa.lobby.cmd.antium;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AntiumCMD extends PACmd {

    public AntiumCMD() {
        super("antium", Grupo.Usuario);
    }

    @Override
    public void run(PAUser u, String label, String[] args) {
        if (!u.isOnRank(Grupo.Admin)) {
            def(u);
            return;
        }

        if (args.length <= 1) admin(u);

        switch (args.length) {
            case 2:
                if (args[0].equalsIgnoreCase("borrar")) {
                    String name = args[1];

                    if (PACore.getInstance().getMysql().deleteUserAntium(name)) {
                        u.sendMessage(PAData.ANTIUM.getPrefix() + "&5" + name + " &6ha sido eliminado correctamente");
                    } else {
                        u.sendMessage(PAData.ANTIUM.getPrefix() + "&5" + name + " &cno existe en la base de datos o ha habido un error");
                    }
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("pass")) {
                    String name = args[1];

                    if (PACore.getInstance().getMysql().changePassword(name, args[2])) {
                        u.sendMessage(PAData.ANTIUM.getPrefix() + "&6La pass ha sido cambiada correctamente");
                    } else {
                        u.sendMessage(PAData.ANTIUM.getPrefix() + "&5" + name + " &cno existe en la base de datos o ha habido un error");
                    }
                }
                break;

            default:
                admin(u);
                break;
        }
    }

    @Override
    public void run(CommandSender u, String label, String[] args) {
        switch (args.length) {
            case 2:
                if (args[0].equalsIgnoreCase("borrar")) {
                    String name = args[1];

                    if (PACore.getInstance().getMysql().deleteUserAntium(name)) {
                        u.sendMessage(Utils.colorize(PAData.ANTIUM.getPrefix() + "&5" + name + " &6ha sido eliminado correctamente"));
                    } else {
                        u.sendMessage(Utils.colorize(PAData.ANTIUM.getPrefix() + "&5" + name + " &cno existe en la base de datos o ha habido un error"));
                    }
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("pass")) {
                    String name = args[1];

                    if (PACore.getInstance().getMysql().changePassword(name, args[2])) {
                        u.sendMessage(Utils.colorize(PAData.ANTIUM.getPrefix() + "&6La pass ha sido cambiada correctamente"));
                    } else {
                        u.sendMessage(Utils.colorize(PAData.ANTIUM.getPrefix() + "&5" + name + " &cno existe en la base de datos o ha habido un error"));
                    }
                }
                break;
        }
    }


    private void def(PAUser u) {
        u.sendMessage(PAData.ANTIUM.getPrefix() + "&bSistema de ProjectAlpha que administra todos las entradas y los registros del servidor.");
        u.sendMessage(PAData.ANTIUM.getPrefix() + "&cPor desgracia no tienes los permisos necesarios para ejecutar este comando");
    }

    private void admin(PAUser u) {
        u.sendMessage(PAData.ANTIUM.getPrefix());
        u.sendMessage(formatedCMD("antium borrar <usuario>", " &7-> &6Elimina al usuario de la base de datos"));
        u.sendMessage(formatedCMD("antium pass <usuario> <nuevaPass>", " &7-> &6Cambia la pass a un usuario"));
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
