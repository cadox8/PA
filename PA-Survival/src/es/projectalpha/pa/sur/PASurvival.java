package es.projectalpha.pa.sur;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.cmd.*;
import es.projectalpha.pa.sur.events.*;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.PvPManager;
import es.projectalpha.pa.sur.tasks.TimeTask;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class PASurvival extends JavaPlugin {

    @Getter private static PASurvival instance;

    public static ArrayList<SurvivalUser> players = new ArrayList<>();

    @Getter private Files files = new Files();
    @Getter private PvPManager manager;
    @Getter private Economy vault;
    @Getter private TimeTask timeTask;

    public void onEnable() {
        instance = this;
        manager = new PvPManager();
        PACommands.register(new StonesCMD(), new MoneyCMD(), new PayCMD(), new PvPCMD(), new LoteriaCMD());
        files.setupFiles();

        registerEvents();

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) vault = economyProvider.getProvider();

        if (vault == null) {
            Log.log(Log.Level.SEVERE, "Vault no encontrado");
            getServer().getPluginManager().disablePlugin(this);
        }

        timeTask = new TimeTask(instance);
        timeTask.runTaskTimer(instance, 0, 20);
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new IronElevators(), instance);
        pm.registerEvents(new PlayerEvents(), instance);
        pm.registerEvents(new PvPEvent(), instance);
        pm.registerEvents(new Sit(), instance);
        pm.registerEvents(new StonesEvents(), instance);
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
}
