package es.projectalpha.pa.core;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.events.PlayerListener;
import es.projectalpha.pa.core.managers.WorldManager;
import es.projectalpha.pa.core.utils.MySQL;
import es.projectalpha.pa.core.utils.Utils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class PACore extends JavaPlugin {

    @Getter private static PACore instance;
    @Getter private static final String prefix = ChatColor.GRAY + " || " + ChatColor.RED + "PA" + ChatColor.GRAY + " || " + ChatColor.RESET;
    @Getter private static final String IP = "&bmc.projectalpha.es";

    @Getter private Utils utils;
    @Getter private WorldManager worldManager;

    @Getter private MySQL mysql = null;
    private Connection connection = null;

    @Override
    public void onEnable() {
        instance = this;

        try {
            debugLog("Cargando modulo de MySQL");
            mysql = new MySQL("projectalpha.es", "pa", "root", "vivalapepa123");
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
                    log("Generando archivo config.yml correctamente");
                } catch (Exception e) {
                    log(PAServer.Level.WARNING, "Fallo al generar el config.yml!");
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

            log("PACore v" + getDescription().getVersion() + " ha sido cargado completamente!");
        } catch (Throwable t) {
            log("No se ha podido cargar PACore v" + getDescription().getVersion());
            debugLog("Causa: " + t.toString());
            getPluginLoader().disablePlugin(this);
        }
    }

    private void registerEvent() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(instance), this);
    }

    private void register() {
        utils = new Utils(this);
        worldManager = new WorldManager(this);
    }




    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            PACommands.onCmd(sender, cmd, label, args);
        } catch (Exception ex) {
            log(PAServer.Level.SEVERE, "Error al ejecutar el comando '" + label + Arrays.toString(args) + "'");
            ex.printStackTrace();
        }
        return true;
    }



    //Log
    public void log(String msg) {
        log(PAServer.Level.INFO, msg);
    }

    public void log(PAServer.Level level, String msg) {
        PAServer.log(level, msg);
    }

    public boolean isDebug() {
        return getConfig().getBoolean("debug");
    }

    public void debugLog(String msg) {
        PAServer.debugLog(msg);
    }

    //Maintenance/Pruebas
    public boolean isMaintenance(){
        return getConfig().getBoolean("maintenance");
    }

    public boolean isPruebas(){
        return getConfig().getBoolean("pruebas");
    }
}
