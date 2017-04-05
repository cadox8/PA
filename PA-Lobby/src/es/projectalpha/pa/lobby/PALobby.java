package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.lobby.events.InventoryEvents;
import es.projectalpha.pa.lobby.events.PlayerEvents;
import es.projectalpha.pa.lobby.utils.LobbyTeams;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PALobby extends JavaPlugin {

    @Getter private static PACore plugin = PACore.getInstance();
    @Getter private static PALobby instance;

    public void onEnable(){
        instance = this;

        plugin.debugLog("Cargando Eventos, Clases, Equipos y Comandos...");
        registerEvents();
        LobbyTeams.initTeams();
    }


    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new InventoryEvents(instance), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
    }


    public static LobbyPlayer getPlayer(OfflinePlayer p){
        return new LobbyPlayer(p);
    }

    public LobbyPlayer getPlayer2(OfflinePlayer p){
        return getPlayer(p);
    }
}
