package es.projectalpha.pa.sn.utils;

import es.projectalpha.pa.sn.SafariNet;
import es.projectalpha.pa.sn.files.Files;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by cadox on 23/12/2016.
 */
public class VaultUtils {

    private Files files = new Files();

    private double moneyPerCatch(String s){
        return files.getConfig().getDouble("coste." + s);
    }

    public boolean hasEnoughMoney(Player p, String s){
        if (SafariNet.getInstance().getEco().getBalance(p) < moneyPerCatch(s)) {
            return false;
        }
        return true;
    }

    public void removeMoney(Player p, String s){
        if (!hasEnoughMoney(p, s)){
            p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.RED + "No tienes dinero suficiente para hacer esto");
            return;
        }
        SafariNet.getInstance().getEco().withdrawPlayer(p, moneyPerCatch(s));
        p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.YELLOW + moneyPerCatch(s) + ChatColor.GREEN + "$ han sido retirados de tu cuenta por coger el mob");
    }
}
