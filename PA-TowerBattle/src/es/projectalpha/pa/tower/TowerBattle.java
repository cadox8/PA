package es.projectalpha.pa.tower;

import es.projectalpha.pa.tower.api.TowerPlayer;
import es.projectalpha.pa.tower.managers.ArenaManager;
import es.projectalpha.pa.tower.managers.GameManager;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TowerBattle extends JavaPlugin {

    public static ArrayList<TowerPlayer> players = new ArrayList<>();
    @Getter
    private static TowerBattle instance;
    @Getter
    private GameManager gm;
    @Getter
    private ArenaManager am;

    public void onEnable() {
        instance = this;

        registerClasses();
        registerEvents();
    }

    private void registerClasses() {
        gm = new GameManager(instance);
        am = new ArenaManager();
    }

    private void registerEvents() {
    }


    public static TowerPlayer getPlayer(OfflinePlayer p) {
        for (TowerPlayer pl : players) {
            if (pl.getName() == null) continue;
            if (pl.getName().equalsIgnoreCase(p.getName())) return pl;
        }
        TowerPlayer us = new TowerPlayer(p.getName());
        if (us.isOnline()) players.add(us);
        return us;
    }
}
