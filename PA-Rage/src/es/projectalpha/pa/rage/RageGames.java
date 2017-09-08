package es.projectalpha.pa.rage;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.utils.GameState;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.cmd.PointSetCMD;
import es.projectalpha.pa.rage.events.GameEvents;
import es.projectalpha.pa.rage.events.PlayerEvents;
import es.projectalpha.pa.rage.files.Files;
import es.projectalpha.pa.rage.manager.ArenaManager;
import es.projectalpha.pa.rage.manager.GameManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class RageGames extends JavaPlugin {

    public static ArrayList<RagePlayer> players = new ArrayList<>();

    @Getter private static RageGames instance;

    @Getter private Files files;
    @Getter private GameManager gm;
    @Getter private ArenaManager am;

    public void onEnable() {
        instance = this;

        PACommands.register(new PointSetCMD());
        registerClasses();
        registerEvents();

        files.setupFiles();

        am.prepareWorld(Bukkit.getWorld("rga1"));
        getServer().getWorlds().forEach(w -> w.getLivingEntities().stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(Entity::remove));

        GameState.setState(GameState.LOBBY);
    }

    private void registerClasses() {
        files = new Files();
        gm = new GameManager(instance);
        am = new ArenaManager(instance);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerEvents(instance), instance);
        pm.registerEvents(new GameEvents(instance), instance);
    }

    public static RagePlayer getPlayer(OfflinePlayer p) {
        for (RagePlayer pl : players) {
            if (pl.getName() == null) continue;
            if (pl.getName().equalsIgnoreCase(p.getName())) return pl;
        }
        RagePlayer us = new RagePlayer(p.getName());
        if (us.isOnline()) players.add(us);
        return us;
    }
}
