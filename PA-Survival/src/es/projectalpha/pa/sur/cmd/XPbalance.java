package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.utils.ExperienceManager;

import java.util.Arrays;

public class XPbalance extends PACmd{

    private ExperienceManager expManager;

    public XPbalance() {
        super("xpbalance", Grupo.Usuario, Arrays.asList("xpb", "expb", "expbalance", "bxp", "balancexp", "bexp", "balanceexp"));
    }

    @Override
    public void run(PAUser user, String label, String[] args){
        if (args.length == 0){
            expManager = new ExperienceManager(user.getPlayer());
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&2Tu experiencia es &6" + expManager.getCurrentExp());
        }
        if(args.length >= 1){
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&cPara usar este comando haz /expbalance");
        }
    }
}
