package es.projectalpha.pa.core.cmd;

import es.projectalpha.pa.core.api.PAUser;
import net.md_5.bungee.api.ChatColor;

public class SpeedCMD extends PACmd {

    public SpeedCMD() {
        super("speed", Grupo.Builder, "velocidad", "speedygonzalez");
    }

    public void run(PAUser user, String label, String... args) {
        if (args.length == 0) {
            user.sendMessage(ChatColor.RED + "Falta argumentos. (/speed <numero>)");
        }
        if (args.length == 1) {
            if (args[0].equals("1")) {
                user.getPlayer().setFlySpeed(0.2f);
                user.getPlayer().setWalkSpeed(0.2f);
                user.getPlayer().sendMessage(ChatColor.GRAY + "Velocidad cambiada a: " + args[0]);
                return;
            }

            if (args[0].equals("10")) {//aaaaaaaaaa
                user.getPlayer().setFlySpeed(1f);
                user.getPlayer().setWalkSpeed(1f);
                user.getPlayer().sendMessage(ChatColor.GRAY + "Velocidad cambiada a: " + args[0]);
                return;
            }

            user.getPlayer().setFlySpeed((Float.parseFloat(args[0]) + 1) / 10);
            user.getPlayer().setWalkSpeed((Float.parseFloat(args[0]) + 1) / 10);
            user.getPlayer().sendMessage(ChatColor.GRAY + "Velocidad cambiada a: " + args[0]);
        }
        if (args.length >= 2) {
            user.sendMessage(ChatColor.RED + "Demasiados argumentos. (/speed <numero>)");
        }
    }
}
