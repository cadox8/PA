package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MoneyCMD extends PACmd {

    private PASurvival plugin = PASurvival.getInstance();

    public MoneyCMD() {
        super("dinero", Grupo.Usuario, "balance", "money");
    }

    private Files files = plugin.getFiles();
    private Balance balance = new Balance();
    private Economy eco = plugin.getVault();

    @Override
    public void run(PAUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("&cDinero: &6" + eco.getBalance(user.getOfflinePlayer()));
        }
        if (args.length == 1) {

            switch (args[0]){
                case "recaudado":
                    user.sendMessage("&cDinero recaudado: &6" + files.getUser().getInt("recaudado"));
                    break;
                default:
                    if(user.isOnRank(Grupo.Mod)){
                        Player p = Bukkit.getPlayerExact(args[0]);
                        if(!p.isOnline()){ user.sendMessage("&4El jugador no existe o está desconectado"); return;}
                        if(!files.getUser().contains(args[0])) return;
                        user.sendMessage("&cDinero de " + args[0] + ": &6" + eco.getBalance(p));
                    }else{
                        user.sendMessage(ChatColor.DARK_RED + "Demasiados argumentos, /money");
                    }
                    break;
            }
        }

        if (args.length > 1) {
            user.sendMessage(ChatColor.DARK_RED + "Demasiados argumentos, /money");
        }
    }

}
