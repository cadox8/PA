package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.Files.Files;
import es.projectalpha.pa.sur.manager.PvPManager;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class PvPCMD extends PACmd{

    public PvPCMD() {
        super("pvp", PACmd.Grupo.Admin, Arrays.asList("pvpmanager", "pvpm"));
    }
    private Files files = new Files();
    private PvPManager pvpm = new PvPManager();

        @Override
        public void run(PAUser user, String label, String[] args) {
            if (args.length == 0) {
                if(pvpm.isInCooldown(user.getPlayer())) user.sendMessage(ChatColor.DARK_RED + "¡Tienes que esperar para volver a usar este comando!");
                if(files.getUser().getBoolean(user.getName() + ".pvp") == true){
                    files.getUser().set(user.getName() + ".pvp", false);
                    pvpm.addCooldown(user.getPlayer());
                    return;
                }
                files.getUser().set(user.getName() + ".pvp", true);
                pvpm.addCooldown(user.getPlayer());
            }

            if (args.length == 1) {
                switch (args[0]){
                    case "on":
                        if(pvpm.isInCooldown(user.getPlayer())) user.sendMessage(ChatColor.DARK_RED + "¡Tienes que esperar para volver a usar este comando!");
                        if(files.getUser().getBoolean(user.getName() + ".pvp") == (true)){
                            user.sendMessage(ChatColor.RED + "Tu pvp ya está activado.");
                        }
                        files.getUser().set(user.getName() + ".pvp", true);
                        pvpm.addCooldown(user.getPlayer());
                        user.sendMessage(ChatColor.RED + "Pvp activado.");
                        break;
                    case "off":
                        if(pvpm.isInCooldown(user.getPlayer())) user.sendMessage(ChatColor.DARK_RED + "¡Tienes que esperar para volver a usar este comando!");
                        if(files.getUser().getBoolean(user.getName() + ".pvp") == true){
                            user.sendMessage(ChatColor.RED + "Tu pvp ya está desactivado.");
                        }
                        files.getUser().set(user.getName() + ".pvp", false);
                        pvpm.addCooldown(user.getPlayer());
                        user.sendMessage(ChatColor.RED + "Pvp desactivado.");
                        break;
                    case "status":
                        switch (files.getUser().getString(user.getName() + ".pvp")){
                            case "true":
                                user.sendMessage(ChatColor.DARK_RED + "Actualmente tu pvp está activado.");
                                break;
                            case "false":
                                user.sendMessage(ChatColor.DARK_GREEN + "Actualmente tu pvp está desactivado.");
                                break;
                            default:
                                break;
                        }
                        break;
                }
            }

            if (args.length == 2) {
                if(args[0].equals("inspect")){
                    //lo haré en algun futuro
                }
        }

    }

}
