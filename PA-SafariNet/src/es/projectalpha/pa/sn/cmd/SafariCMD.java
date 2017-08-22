package es.projectalpha.pa.sn.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Sounds;
import es.projectalpha.pa.sn.recipes.PokeEgg;

import java.util.Arrays;

public class SafariCMD extends PACmd {

    private PokeEgg egg = new PokeEgg();

    public SafariCMD(){
        super("safarinet", Grupo.Admin, Arrays.asList("safari", "net", "sn"));
    }

    public void run(PAUser user, String label, String[] args){
        if (args.length == 1){
            if (args[0].equalsIgnoreCase("huevo")){
                user.getPlayer().getInventory().addItem(egg.getPokeEgg());
                user.sendSound(Sounds.ENDERDRAGON_DEATH);
            }
        }
    }
}
