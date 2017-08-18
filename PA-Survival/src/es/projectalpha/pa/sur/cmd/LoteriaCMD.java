package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.files.Files;
import org.bukkit.ChatColor;

import java.util.Random;

public class LoteriaCMD extends PACmd {
    public LoteriaCMD() {
            super("loteria", Grupo.Usuario);
        }

    private Files files = new Files();

    public void run(PAUser user, String label, String[] args) {
        if(args.length == 0){
            user.sendMessage("&aLa lotería es un juego del Survival que cada 24 horas se sortea.");
            user.sendMessage("&aSi hay un ganador se lleva el dinero en el bote, si no sale ninguno");
            user.sendMessage("&ase guarda ese dinero junto al que se añade cada día hasta que salga");
            user.sendMessage("&algún ganador. Para comprar un boleto haz /loteria <numero>, ");
            user.sendMessage("&apuedes comprar un máximo de 10 boletos. Ejemplo: /lotaria 10. ¡Suerte!");
        }
        if(args.length == 1){
            int bol = Integer.parseInt(args[0]);
            int rd = new Random().nextInt(9999);
            if(bol > 10) user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4El máximo de boletos que puedes comprar son 10.");
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&aHas comprado " + bol + " boletos, tus números son: ");
            for(int b = 0; b <= bol; b++){
               files.getUser().set("Users." + user.getName() + ".bol." + b, rd);
               user.sendMessage(ChatColor.GOLD + files.getUser().getString("Users." + user.getName() + ".bol." + b));
            }
        }
    }
}

