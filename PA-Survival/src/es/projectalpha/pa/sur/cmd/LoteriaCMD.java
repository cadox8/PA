package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Random;


public class LoteriaCMD extends PACmd {
    public LoteriaCMD() {
            super("loteria", Grupo.Usuario);
        }

    private Files files = new Files();
    private PASurvival plugin;

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
            int apos = Files.user.getInt("Users." + user.getName() + ".apos");
            if(bol > 10){
                user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4El máximo de boletos que puedes comprar son 10.");
                return;
            }
            if(bol < 1){
                user.sendMessage(PAData.SURVIVAL.getPrefix() + "&4El mínimo de boletos que puedes comprar es 1");
                return;
            }
            if(apos + bol > 10){
                user.sendMessage("&4Ya has comprado 10 boletos, mañana podrás apostar 10 boletos más.");
                return;
            }
            user.sendMessage(PAData.SURVIVAL.getPrefix() + "&aHas comprado " + bol + " boletos, tus números son: ");
            for(int b = 1; b <= bol; b++){
                ArrayList<Integer> arbol = new ArrayList<>();
                plugin.getBol().put(PASurvival.getPlayer(user.getPlayer()), arbol);
                Files.user.set("Users." + user.getName() + ".apos", bol);
                Files.user.set("numeros." + new Random().nextInt(9999) + ".owner", user.getName());
                user.sendMessage(".");
                Files.saveFiles(); //Que si no no se guarda, joder. #SuicideIsNear
            }
        }
    }
}

