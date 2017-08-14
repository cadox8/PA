package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import net.milkbowl.vault.economy.Economy;

public class Balance {
    private Economy eco;
    private Files files;

    public void saveBalance(SurvivalUser p){
        files.getUser().set("Users." + p.getName() + ".money", eco.getBalance(p.getPlayer()));
    }

    public void addBalace(SurvivalUser p, int v){
        if(files.getUser().getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        files.getUser().set("Users." + p.getName() + ".money", files.getUser().getInt("Users." + p.getName() + ".money") + v);
    }

    public void removeBalance(SurvivalUser p, int v){
        if(files.getUser().getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        files.getUser().set("Users." + p.getName() + ".money", files.getUser().getInt("Users." + p.getName() + ".money") - v);
    }

    public void resetBalance(SurvivalUser p){
        files.getUser().set("Users." + p.getName() + ".money",0);
    }

}
