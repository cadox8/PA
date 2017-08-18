package es.projectalpha.pa.sur.manager;

import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.sur.files.Files;
import org.bukkit.entity.Player;

public class PvPManager {

    private Cooldown cmdc = new Cooldown(20);
    private Cooldown pvpc = new Cooldown(25);
    private Cooldown noobc = new Cooldown(1800);
    private Files files = new Files();

    public void setPvPState(Player p, boolean pvp){
        if(checkStatus(p, pvp)) return;
        files.getUser().set("Users." + p.getName() + ".pvp", pvp);
        addCooldown(p);
    }

    public void setPvPStateAdmin(Player p, boolean pvp){
        if(checkStatus(p, pvp)) return;
        files.getUser().set("Users." + p.getName() + ".pvp", pvp);
    }

    private boolean getPvPState(Player p){
        return files.getUser().getBoolean("Users." + p.getName() + ".pvp");
    }

    private boolean checkStatus(Player p, boolean pvp){
        return pvp == getPvPState(p);
    }

    public String parseStatus(Player p){
        if(getPvPState(p)){
            return "true";
        }
        return "false";
    }

    public void addCooldown(Player p){
        if(cmdc.isCoolingDown(p)) return;
        cmdc.setOnCooldown(p);
    }

    public boolean isInCooldown(Player p){
        return cmdc.isCoolingDown(p);
    }

    public void addPvp(Player p){
        if(pvpc.isCoolingDown(p)) return;
        pvpc.setOnCooldown(p);

    }

    public boolean isInPvP(Player p){
        return pvpc.isCoolingDown(p);
    }

    public void addNewbie(Player p){
        if(noobc.isCoolingDown(p)) return;
        noobc.setOnCooldown(p);
    }

    public void removePvP(Player p){
        pvpc.removeCooldown(p);
    }
    public void removeCooldown(Player p){
        cmdc.removeCooldown(p);
    }
    public void removeNoobie(Player p){
        noobc.removeCooldown(p);
    }

    public boolean isNewbie(Player p){
        return noobc.isCoolingDown(p);
    }

    public void check(){
        try {

            check();
        } catch (StackOverflowError e){}
    }
}
