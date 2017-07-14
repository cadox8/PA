package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.lobby.cmd.SpawnCMD;
import es.projectalpha.pa.lobby.events.InventoryEvents;
import es.projectalpha.pa.lobby.events.PlayerEvents;
import es.projectalpha.pa.lobby.tasks.NoFallTasks;
import es.projectalpha.pa.lobby.utils.LobbyMenu;
import es.projectalpha.pa.lobby.utils.LobbyTeams;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class PALobby extends JavaPlugin {

    @Getter private static PALobby instance;

    public void onEnable(){
        instance = this;

        registerEvents();
        LobbyTeams.initTeams();

        PACommands.register(new SpawnCMD());
        new LobbyMenu(instance);

        new NoFallTasks(instance).runTaskTimer(instance, 0, 20);
    }


    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new InventoryEvents(instance), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
    }
}
