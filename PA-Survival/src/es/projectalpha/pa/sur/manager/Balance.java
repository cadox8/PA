package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import net.milkbowl.vault.economy.Economy;

public final class Balance {
    private Economy eco;
    private Files files;

    public void saveBalance(SurvivalUser p){
        if(files.getUser().getInt("Users." + p.getName() + ".money") == eco.getBalance(p.getPlayer())) return;
        files.getUser().set("Users." + p.getName() + ".money", eco.getBalance(p.getPlayer()));
    }

    public void addBalace(SurvivalUser p, double v){
        if(files.getUser().getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        files.getUser().set("Users." + p.getName() + ".money", files.getUser().getInt("Users." + p.getName() + ".money") + v);
    }

    public void removeBalance(SurvivalUser p, double v){
        if(files.getUser().getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        files.getUser().set("Users." + p.getName() + ".money", files.getUser().getInt("Users." + p.getName() + ".money") - v);
    }

    public void resetBalance(SurvivalUser p){
        eco.withdrawPlayer(p.getPlayer(), eco.getBalance(p.getPlayer()));
        saveBalance(p);
    }

}
