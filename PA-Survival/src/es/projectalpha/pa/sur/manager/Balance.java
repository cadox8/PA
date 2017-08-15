package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import net.milkbowl.vault.economy.Economy;

import static es.projectalpha.pa.sur.files.Files.saveFiles;

public final class Balance {

    private Economy eco = PASurvival.getInstance().getVault();
    private Files files = PASurvival.getInstance().getFiles();

    public void saveBalance(SurvivalUser p){
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
}
