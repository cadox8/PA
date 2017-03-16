package es.projectalpha.pa.antium.cmd;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AntiumCMD extends PACmd {

    public AntiumCMD(){
        super("antium", Grupo.Usuario);
    }

    @Override
    public void run(PAUser u, String label, String[] args){
        if (args.length <= 1){
            if (!u.isOnRank(Grupo.Admin)){
                def(u);
                return;
            }
            admin(u);
        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("borrar")){
                if (!u.isOnRank(Grupo.Admin)){
                    u.sendMessagePrefix(Messages.noPerms);
                    return;
                }
                PAUser target = PAServer.getUser(args[1]);

                if (target == null || !target.isOnline()){
                    userNotOnline(u);
                    return;
                }
                if (PAAntium.getInstance().getMysql().deleteUserAntium(target)){
                    u.sendMessagePrefix("&5" + target.getName() + " &6ha sido eliminado correctamente");
                } else{
                    u.sendMessagePrefix("&5" + target.getName() + " &cNO ha sido eliminado correctamente");
                }
            }
        }
    }

    private void def(PAUser u){
        u.sendMessagePrefix("&cAntium");
        u.sendMessage("Sistema de ProjectAlpha que administra todos los logeos y registros del servidor.");
        u.sendMessage("Con esto se puede hacer algo, pero no tienes los suficientes permisos para eso :D");
    }

    private void admin(PAUser u){
        u.sendMessagePrefix("&cAntium");
        u.sendMessage(formatedCMD("antium borrar <usuario>", " &7-> &6Elimina al usuario de la base de datos"));
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
