package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.utils.ExperienceManager;
import net.milkbowl.vault.economy.Economy;

import java.util.Arrays;
import java.util.List;

public class Exp2cash extends PACmd{
    public Exp2cash() {
        super("exp2cash", Grupo.Usuario, Arrays.asList("xp2c", "exp2c", "xp2cash"));
    }

    private Economy eco = PASurvival.getInstance().getVault();
    private Files files = PASurvival.getInstance().getFiles();
    private ExperienceManager exp;

    @Override
    public void run(PAUser user, String label, String[] args){
        exp = new ExperienceManager(user.getPlayer());

        if(args.length == 0){
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&cPara user este comando haz /exp2cash <numero:all>.");
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("all")) {
                eco.depositPlayer(user.getPlayer(), (Files.config.getDouble("Experiencia.vender") * exp.getCurrentExp()));
                user.sendMessage("&2Has vendido toda tu experiencia por &e" +(Files.config.getDouble("Experiencia.vender") * exp.getCurrentExp()) + "&2$");
                user.getPlayer().setTotalExperience(0);
                user.getPlayer().setExp(0);
                user.getPlayer().setLevel(0);
                return;
            }
            if(args[0].equalsIgnoreCase(args[0])){
                if(!Utils.isInt(args[0])){
                    user.sendMessage("&4Solo se puedes usar números o all en este comando.");
                    return;
                }
                int xp = (exp.getCurrentExp() - Integer.parseInt(args[0]));

                if(exp.getCurrentExp() < Integer.parseInt(args[0])){
                    user.sendMessage("&cNo tienes suficiente experiencia para hacer esto");
                    return;
                }

                eco.depositPlayer(user.getPlayer(), (Files.config.getDouble("Experiencia.vender")*Double.parseDouble(args[0])));
                user.sendMessage("&2Has vendido &e" + args[0] + " &2puntos de experiencia por &e" + (Files.config.getDouble("Experiencia.vender")*Double.parseDouble(args[0])) + "&2$");
                exp.setExp(xp);
            }
        }
        if(args.length >= 2){
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&cPara user este comando haz /exp2cash <numero:all>.");
        }
    }

}
