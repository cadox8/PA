package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import net.milkbowl.vault.economy.Economy;

import java.text.DecimalFormat;


public final class Balance {

    private Economy eco = PASurvival.getInstance().getVault();
    private Files files = PASurvival.getInstance().getFiles();
    private PASurvival plugin;

    public void saveBalance(SurvivalUser p){
        if(Files.user.getInt("Users." + p.getName() + ".money") == eco.getBalance(p.getPlayer())) return;
        Files.user.set("Users." + p.getName() + ".money", eco.getBalance(p.getPlayer()));
        Files.saveFiles();
    }

    public void addBalace(SurvivalUser p, double v){
        if(Files.user.getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        Files.user.set("Users." + p.getName() + ".money", Files.user.getInt("Users." + p.getName() + ".money") + v);
        eco.depositPlayer(p.getPlayer(), v);
        Files.saveFiles();
    }

    public void removeBalance(SurvivalUser p, double v){
        if(Files.user.getInt("Users." + p.getName() + ".money") != eco.getBalance(p.getPlayer())) saveBalance(PASurvival.getPlayer(p.getPlayer()));
        Files.user.set("Users." + p.getName() + ".money", Files.user.getInt("Users." + p.getName() + ".money") - v);
        eco.withdrawPlayer(p.getPlayer(), v);
        Files.saveFiles();
    }

    public void resetBalance(SurvivalUser p){
        eco.withdrawPlayer(p.getPlayer(), eco.getBalance(p.getPlayer()));
        saveBalance(p);
        Files.saveFiles();
    }

    public void cobrarImpuestos(SurvivalUser p, double amount) {
        DecimalFormat df = new DecimalFormat("#.00");
        saveBalance(p);
        Files.user.set("recaudado", Files.user.getInt("recaudado") + amount);
        Files.user.set("Users." + p.getName() + ".imprec", Files.user.getInt("Users." + p.getName() + ".imprec") + amount);
        removeBalance(p, amount);
        saveBalance(p);
    }
}
