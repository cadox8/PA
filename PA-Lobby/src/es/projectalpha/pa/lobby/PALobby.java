package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.lobby.api.LobbyPlayer;
import es.projectalpha.pa.lobby.events.InventoryEvents;
import es.projectalpha.pa.lobby.events.PlayerEvents;
import es.projectalpha.pa.lobby.tasks.NoFallTasks;
import es.projectalpha.pa.lobby.utils.LobbyTeams;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class PALobby extends JavaPlugin {

    @Getter private static PACore plugin = PACore.getInstance();
    @Getter private static PALobby instance;

    public static ArrayList<LobbyPlayer> players = new ArrayList<>();

    public void onEnable(){
        instance = this;

        plugin.debugLog("Cargando Eventos, Clases, Equipos y Comandos...");
        registerEvents();
        LobbyTeams.initTeams();

        File fConf = new File(getDataFolder(), "config.yml");
        if (!fConf.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
                Log.debugLog("Generando archivo config.yml correctamente");
            } catch (Exception e) {
                Log.log(Log.Level.WARNING, "Fallo al generar el config.yml!");
                Log.debugLog("Causa: " + e.toString());
            }
        }

        new NoFallTasks(instance).runTaskTimer(instance, 0, 20);
    }


    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new InventoryEvents(instance), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
    }


    public static LobbyPlayer getPlayer(OfflinePlayer p){
        for (LobbyPlayer pl : players) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(p.getUniqueId())) return pl;
        }
        LobbyPlayer us = new LobbyPlayer(p.getUniqueId());
        if (us.isOnline()) players.add(us);
        return us;
    }
}
