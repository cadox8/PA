package es.projectalpha.pa.nexus;

import es.projectalpha.pa.nexus.api.NexusPlayer;
import es.projectalpha.pa.nexus.managers.ArenaManager;
import es.projectalpha.pa.nexus.managers.GameManager;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class NexusSiege extends JavaPlugin {

    public static ArrayList<NexusPlayer> players = new ArrayList<>();
    @Getter
    private static NexusSiege instance;
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


    public static NexusPlayer getPlayer(OfflinePlayer p) {
        for (NexusPlayer pl : players) {
            if (pl.getName() == null) continue;
            if (pl.getName().equalsIgnoreCase(p.getName())) return pl;
        }
        NexusPlayer us = new NexusPlayer(p.getName());
        if (us.isOnline()) players.add(us);
        return us;
    }
}
