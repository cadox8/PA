package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.utils.ExperienceManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class Cash2xp extends PACmd {

    public Cash2xp() {
        super("cash2xp", Grupo.Usuario, Arrays.asList("cash2exp", "c2xp", "c2exp"));
    }

    private Economy eco = PASurvival.getInstance().getVault();
    private ExperienceManager exp;

    public void run(PAUser user, String label, String[] args){
        if (args.length == 0) {
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&cPara usar este comando, haz /cash2exp <cantidad>");
        }

        if (args.length == 1) {
            if (args[0].equals(args[0])) {
                if(!Utils.isInt(args[0])){
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4Solo puedes usar números en este comando.");
                    return;
                }

                if (eco.getBalance(user.getPlayer()) < (Files.config.getDouble("Experiencia.comprar") * Double.parseDouble(args[0]))) {
                    user.sendMessage(PAData.SURVIVAL.getPrefix() + "&cNo tienes suficiente dinero para hacer esto. (" + (Files.user.getDouble("Experiencia.comprar") * Double.parseDouble(args[0])) + ")");
                    return;
                }

                eco.withdrawPlayer(user.getPlayer(), (Files.config.getDouble("Experiencia.comprar") * Double.parseDouble(args[0])));
                user.sendMessage(ChatColor.GREEN + " Has comprado " + ChatColor.YELLOW + args[0] + ChatColor.GREEN +" puntos de experiencia por " + ChatColor.YELLOW + (Files.config.getDouble("Experiencia.comprar") * Double.parseDouble(args[0])) + ChatColor.GREEN +"$");
                user.getPlayer().giveExp(Integer.parseInt(args[0]));
            }
        }
        if(args.length > 1){
            user.sendMessage("&cPara usar este comando, haz /cash2exp <cantidad>");
        }
    }
}
