package es.projectalpha.pa.towerbattle;

import es.projectalpha.pa.towerbattle.api.TowerPlayer;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public class TowerBattle {

    @Getter
    private static TowerBattle instance;

    public static ArrayList<TowerPlayer> players = new ArrayList<>();

    public void onEnable() {
        instance = this;

        registerClasses();
        registerEvents();
    }

    private void registerClasses(){
    }

    private void registerEvents() {
    }


    public static TowerPlayer getPlayer(OfflinePlayer p) {
        for (TowerPlayer pl : players) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(p.getUniqueId())) return pl;
        }
        TowerPlayer us = new TowerPlayer(p.getUniqueId());
        if (us.isOnline()) players.add(us);
        return us;
    }

}
