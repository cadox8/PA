package es.projectalpha.pa.sur;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.cmd.*;
import es.projectalpha.pa.sur.events.IronElevators;
import es.projectalpha.pa.sur.events.PlayerEvents;
import es.projectalpha.pa.sur.events.PvPEvent;
import es.projectalpha.pa.sur.events.Sit;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.PvPManager;
import es.projectalpha.pa.sur.tasks.TimeTask;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class PASurvival extends JavaPlugin {

    @Getter private static PASurvival instance;

    public static ArrayList<SurvivalUser> players = new ArrayList<>();

    @Getter private Files files = new Files();
    @Getter private PvPManager manager;
    @Getter private Economy vault;
    @Getter private TimeTask timeTask;
    @Getter private HashMap<Player, PermissionAttachment> perms = new HashMap<>();
    @Getter private static ArrayList<String> imp = new ArrayList<>();
    @Getter private HashMap<SurvivalUser, ArrayList<Integer>> bol = new HashMap<>();

    public void onEnable() {
        instance = this;
        manager = new PvPManager();
        files.setupFiles();
        setupEconomy();
        PACommands.register(new StonesCMD(), new RecaudadoCMD(), new PvPCMD(), new LoteriaCMD(),
                new Cash2xp(), new Exp2cash(), new XPbalance(), new Cadox8CMD());
        registerEvents();


        timeTask = new TimeTask(instance);
        timeTask.runTaskTimer(instance, 0, 15);
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new IronElevators(), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
        pm.registerEvents(new PvPEvent(), instance);
        pm.registerEvents(new Sit(), instance);
        //pm.registerEvents(new StonesEvents(), instance);
    }

    public static SurvivalUser getPlayer(OfflinePlayer p) {
        for (SurvivalUser pl : players) {
            if (pl.getName() == null) continue;
            if (pl.getName().equalsIgnoreCase(p.getName())) return pl;
        }
        SurvivalUser us = new SurvivalUser(p.getName());
        if (us.isOnline()) players.add(us);
        return us;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null){
            Log.debugLog("Oye, que el Economy es null 1");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null){
            Log.debugLog("Oye, que el Economy es null 2");
            return false;
        }

        vault = rsp.getProvider();
        Log.debugLog("Oye, que el Economy ha cargao mi arma");
        return vault != null;
    }
}
