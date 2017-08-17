package es.projectalpha.wc.sn.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.wc.sn.recipes.PokeEgg;
import org.bukkit.Sound;

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
                user.sendSound(Sound.ENTITY_ENDERDRAGON_DEATH);
            }
        }
    }
}
