package es.projectalpha.pa.sur;

import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.events.IronElevators;
import es.projectalpha.pa.sur.events.PlayerEvents;
import es.projectalpha.pa.sur.events.PvPEvent;
import es.projectalpha.pa.sur.events.Sit;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.PvPManager;
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


    public void onEnable() {
        instance = this;
        manager = new PvPManager();
        manager.check();

        files.setupFiles();

        registerEvents();

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) vault = economyProvider.getProvider();
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new IronElevators(), instance);
        pm.registerEvents(new PlayerEvents(), instance);
        pm.registerEvents(new PvPEvent(), instance);
        pm.registerEvents(new Sit(), instance);
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
