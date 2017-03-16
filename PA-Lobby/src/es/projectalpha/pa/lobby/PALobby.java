package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.lobby.events.InventoryEvents;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PALobby extends JavaPlugin {

    @Getter private static PACore plugin = PACore.getInstance();
    @Getter private static PALobby instance;

    public void onEnable(){
        instance = this;

        plugin.debugLog("Cargando Eventos...");
        registerEvents();
    }


    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new InventoryEvents(instance), instance);
    }


    public static LobbyPlayer getPlayer(OfflinePlayer p){
        return new LobbyPlayer(p);
    }

    public LobbyPlayer getPlayer2(OfflinePlayer p){
        return getPlayer(p);
    }
}
