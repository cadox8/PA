package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PayCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public PayCMD() {
        super("pvp", PACmd.Grupo.Admin);
    }

    private Files files = plugin.getFiles();
    private Balance balance = new Balance();

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("&4Necesitas definir el jugador y el dinero que vas a pagar. /pay <jugador> <cantidad>");
        }

        if (args.length == 1) {
            user.sendMessage("&4Necesitas definir el jugador y el dinero que vas a pagar. /pay <jugador> <cantidad>");
        }

        if (args.length == 2) {
            Player u = Bukkit.getPlayerExact(args[0]);
            if(!u.isOnline()){ user.sendMessage("&4El jugador no existe o está desconectado"); return;}
            balance.addBalace(PASurvival.getPlayer(u), Double.parseDouble(args[1]));
            balance.saveBalance(PASurvival.getPlayer(u));
            user.sendMessage("&Has pagado a " + u.getName() + " " + args[1] + "$");
        }
        if(args.length >= 3){
            user.sendMessage("&4Demasiados argumentos. /pay <jugador> <cantidad>");
        }

    }
}
