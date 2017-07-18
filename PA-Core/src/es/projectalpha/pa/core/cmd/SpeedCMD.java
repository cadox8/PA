package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class SpeedCMD extends PACmd{

    public SpeedCMD(String name, Grupo grupo, String... aliases) {
        super("speed", Grupo.Builder, "velocidad", "speedygonzalez");
    }

    public void run(PAUser user, String label, String... args) {
        if(args.length == 0){
            user.sendMessage(ChatColor.RED + "Falta argumentos. (/speed int)");
        }
        if(args.length == 1){
            if(args[1].equals("1")){
               user.getPlayer().setFlySpeed(0.1f);
               user.getPlayer().setWalkSpeed(0.1f);
            }
            user.getPlayer().setFlySpeed(Float.parseFloat(args[1])/10);
        }
        if (args.length >= 2) {
            user.sendMessage(ChatColor.RED + "Demasiados argumentos. (/speed int)");
        }
    }
}
