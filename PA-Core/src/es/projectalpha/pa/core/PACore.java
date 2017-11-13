package es.projectalpha.pa.core;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.events.PlayerListener;
import es.projectalpha.pa.core.logros.LogrosManager;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.core.utils.MySQL;
import es.projectalpha.pa.core.utils.Utils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class PACore extends JavaPlugin {

    @Getter private static final String IP = "&bmc.projectalpha.es";
    @Getter private static final String OLD_IP = "§bmc.projectalpha.es";
    @Getter private static PACore instance;
    @Getter private HashMap<Player, PermissionAttachment> perms = new HashMap<>();
    @Getter private static ViaAPI api = Via.getAPI();

    @Getter private Utils utils;
    @Getter private LogrosManager logros;

    @Getter private MySQL mysql = null;
    private Connection connection = null;

    @Override
    public void onEnable() {
        instance = this;

        try {
            debugLog("Cargando modulo de MySQL");
            mysql = new MySQL("localhost", "pa", "root", "password");
            connection = mysql.openConnection();
        } catch (SQLException | ClassNotFoundException exc) {
            getLogger().severe("Error al abrir la conexion MySQL!");
            debugLog("Causa: " + exc.toString());
            getLogger().severe("PACore desactivado por imposibilidad de conexiones");
            getServer().getPluginManager().disablePlugin(this); //Desactivar si no hay MySQL (Solo dará errores si esta activo)
        }

        try {
            debugLog("Cargando Archivos...");
            File fConf = new File(getDataFolder(), "config.yml");
            if (!fConf.exists()) {
                try {
                    getConfig().options().copyDefaults(true);
                    saveConfig();
                    Log.debugLog("Generando archivo config.yml correctamente");
                } catch (Exception e) {
                    Log.log(Log.Level.WARNING, "Fallo al generar el config.yml!");
                    debugLog("Causa: " + e.toString());
                }
            }

            debugLog("Conectando con BungeeCord...");
            getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
            getServer().getMessenger().registerOutgoingPluginChannel(instance, "PA");

            debugLog("Cargando Clases y Eventos...");
            register();
            registerEvent();
            new PAServer();

            debugLog("Cargando comandos...");
            PACommands.load();

            Log.debugLog("PACore v" + getDescription().getVersion() + " ha sido cargado completamente!");
        } catch (Throwable t) {
            Log.debugLog("No se ha podido cargar PACore v" + getDescription().getVersion());
            debugLog("Causa: " + t.toString());
            getPluginLoader().disablePlugin(this);
        }
    }

    private void registerEvent() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(instance), this);
    }

    private void register() {
        utils = new Utils(instance);
        logros = new LogrosManager(instance);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            PACommands.onCmd(sender, cmd, label, args);
        } catch (Exception ex) {
            Log.log(Log.Level.SEVERE, "Error al ejecutar el comando '" + label + Arrays.toString(args) + "'");
            ex.printStackTrace();
        }
        return true;
    }

    public void debugLog(String msg) {
        Log.debugLog(msg);
    }
}
