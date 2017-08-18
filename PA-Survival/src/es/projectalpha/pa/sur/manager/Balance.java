package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import net.milkbowl.vault.economy.Economy;




public final class Balance {

    private Economy eco = PASurvival.getInstance().getVault();
    private Files files = PASurvival.getInstance().getFiles();
    private PASurvival plugin;

    public void saveBalance(SurvivalUser p){
        if (eco == null) {
            Log.debugLog("Oye, que el Economy es null");
            return;
        }
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

    public void cobrarImpuestos(SurvivalUser p){
            saveBalance(p);
            Files.user.set("recaudado", Files.user.getInt("recaudado") + (Files.user.getInt("Users." + p.getName() + ".money")*0.01));
            Files.user.set("recaudado", Files.user.getInt("recaudado") - (Files.user.getInt("recaudado")*0.1));
            Files.user.set("loteria", Files.user.getInt("loteria") + (Files.user.getInt("recaudado")*0.1));
            Files.user.set("Users." + p.getName() + ".imprec",Files.user.getInt("Users." + p.getName() + ".imprec") + (eco.getBalance(p.getPlayer())*0.01));
            removeBalance(p, eco.getBalance(p.getPlayer())*0.01);
            saveBalance(p);

       }

}
