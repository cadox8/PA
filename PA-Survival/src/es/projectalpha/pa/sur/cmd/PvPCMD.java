package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.PASurvival;
import org.bukkit.ChatColor;

import java.util.Arrays;

import static es.projectalpha.pa.sur.files.Files.saveFiles;

public class PvPCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public PvPCMD() {
        super("pvp", Grupo.Usuario, Arrays.asList("pvpmanager", "pvpm"));
    }

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            if (plugin.getManager().isInCooldown(user.getPlayer()))
                user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "¡Tienes que esperar para volver a usar este comando!");
            if (plugin.getFiles().getUser().getBoolean(user.getName() + ".pvp") == true) {
                plugin.getFiles().getUser().set(user.getName() + ".pvp", false);
                user.sendMessage(PAData.SURVIVAL.getPrefix() +"&aPvp desactivado.");
                plugin.getManager().addCooldown(user.getPlayer());
                saveFiles();
                return;
            }
            plugin.getFiles().getUser().set(user.getName() + ".pvp", true);
            user.sendMessage(PAData.SURVIVAL.getPrefix() +"&cPvp activado.");
            plugin.getManager().addCooldown(user.getPlayer());
            saveFiles();
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "on":
                    if (plugin.getManager().isInCooldown(user.getPlayer()))
                        user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "¡Tienes que esperar para volver a usar este comando!");
                    if (plugin.getFiles().getUser().getBoolean(user.getName() + ".pvp") == (true)) {
                        user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.RED + "Tu pvp ya está activado.");
                    }
                    plugin.getFiles().getUser().set(user.getName() + ".pvp", true);
                    plugin.getManager().addCooldown(user.getPlayer());
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.RED + "Pvp activado.");
                    saveFiles();
                    break;
                case "off":
                    if (plugin.getManager().isInCooldown(user.getPlayer()))
                        user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "¡Tienes que esperar para volver a usar este comando!");
                    if (plugin.getFiles().getUser().getBoolean(user.getName() + ".pvp") == true) {
                        user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.RED + "Tu pvp ya está desactivado.");
                    }
                    plugin.getFiles().getUser().set(user.getName() + ".pvp", false);
                    plugin.getManager().addCooldown(user.getPlayer());
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.RED + "Pvp desactivado.");
                    saveFiles();
                    break;
                case "status":
                    switch (plugin.getFiles().getUser().getString(user.getName() + ".pvp")) {
                        case "true":
                            user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "Actualmente tu pvp está activado.");
                            break;
                        case "false":
                            user.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + "Actualmente tu pvp está desactivado.");
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }

        if (args.length == 2) {
            if (args[0].equals("inspect")) {
                //lo haré en algun futuro
            }
        }

    }

}
