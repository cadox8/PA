package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static es.projectalpha.pa.sur.files.Files.saveFiles;

public final class Balance {

    private Economy eco = PASurvival.getInstance().getVault();
    private Files files = PASurvival.getInstance().getFiles();
    private PASurvival plugin;

    public void saveBalance(SurvivalUser p){
        if (eco == null) {
            Log.debugLog("Oye, que el Economy es null");
            return;
        }
        if(files.getUser().getInt("Users." + p.getName() + ".money") == eco.getBalance(p.getPlayer())) return;
        files.getUser().set("Users." + p.getName() + ".money", eco.getBalance(p.getPlayer()));
        saveFiles();
    }

    public void addBalace(SurvivalUser p, double v){
        if(files.getUser().getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        files.getUser().set("Users." + p.getName() + ".money", files.getUser().getInt("Users." + p.getName() + ".money") + v);
        eco.depositPlayer(p.getPlayer(), v);
        saveFiles();
    }

    public void removeBalance(SurvivalUser p, double v){
        if(files.getUser().getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        files.getUser().set("Users." + p.getName() + ".money", files.getUser().getInt("Users." + p.getName() + ".money") - v);
        eco.withdrawPlayer(p.getPlayer(), v);
        saveFiles();
    }

    public void resetBalance(SurvivalUser p){
        eco.withdrawPlayer(p.getPlayer(), eco.getBalance(p.getPlayer()));
        saveBalance(p);
        saveFiles();
    }

    public void cobrarImpuestos(SurvivalUser p){
            saveBalance(p);
            files.getUser().set("recaudado", files.getUser().getInt("recaudado") + (files.getUser().getInt("Users." + p.getName() + ".money")*0.01));
            files.getUser().set("recaudado", files.getUser().getInt("recaudado") - (files.getUser().getInt("recaudado")*0.1));
            files.getUser().set("loteria", files.getUser().getInt("loteria") + (files.getUser().getInt("recaudado")*0.1));
            removeBalance(p, eco.getBalance(p.getPlayer())*0.01);
            saveBalance(p);

       }

}
