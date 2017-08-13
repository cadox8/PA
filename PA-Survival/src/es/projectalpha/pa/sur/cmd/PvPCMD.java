package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.Files.Files;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class PvPCMD extends PACmd{

    public PvPCMD() {
        super("pvp", PACmd.Grupo.Admin, Arrays.asList("pvpmanager", "pvpm"));
    }
    private Files files = new Files();

        @Override
        public void run(PAUser user, String label, String[] args) {
            if (args.length == 0) {
                if(files.getUser().getString(user.getName() + ".pvp").equals("on")){
                    files.getUser().set(user.getName() + ".pvp", "off");
                    return;
                }
                files.getUser().set(user.getName() + ".pvp", "on");
            }

            if (args.length == 1) {
                switch (args[0]){
                    case "on":
                        if(files.getUser().getString(user.getName() + ".pvp").equals("on")){
                            user.sendMessage(ChatColor.RED + "Tu pvp ya está activado.");
                        }
                        files.getUser().set(user.getName() + ".pvp", "on");
                        user.sendMessage(ChatColor.RED + "Pvp activado.");
                        break;
                    case "off":
                        if(files.getUser().getString(user.getName() + ".pvp").equals("off")){
                            user.sendMessage(ChatColor.RED + "Tu pvp ya está desactivado.");
                        }
                        files.getUser().set(user.getName() + ".pvp", "off");
                        user.sendMessage(ChatColor.RED + "Pvp desactivado.");
                        break;
                    case "status":
                        switch (files.getUser().getString(user.getName() + ".pvp")){
                            case "on":
                                user.sendMessage(ChatColor.DARK_RED + "Actualmente tu pvp está activado.");
                                break;
                            case "off":
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

                }
        }

    }

}
