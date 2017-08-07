package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.lobby.cmd.KittyCMD;
import es.projectalpha.pa.lobby.cmd.SetSpawnCMD;
import es.projectalpha.pa.lobby.cmd.SpawnCMD;
import es.projectalpha.pa.lobby.events.InventoryEvents;
import es.projectalpha.pa.lobby.events.PlayerEvents;
import es.projectalpha.pa.lobby.tasks.NoFallTask;
import es.projectalpha.pa.lobby.utils.LobbyMenu;
import es.projectalpha.pa.lobby.utils.LobbyTeams;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PALobby extends JavaPlugin {

    @Getter
    private static PALobby instance;

    public void onEnable() {
        instance = this;

        if (getServer().getPluginManager().getPlugin("PA-Core") == null) getServer().getPluginManager().disablePlugin(this);

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
            }
        }

        PACommands.register(new SpawnCMD(), new KittyCMD(), new SetSpawnCMD());
        new LobbyMenu(instance);
        new NoFallTask(instance).runTaskTimer(instance, 0, 20);
    }


    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new InventoryEvents(instance), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
    }
}
