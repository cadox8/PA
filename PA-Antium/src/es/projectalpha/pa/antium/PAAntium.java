package es.projectalpha.pa.antium;

import es.projectalpha.pa.antium.cmd.AntiumCMD;
import es.projectalpha.pa.antium.cmd.LoginCMD;
import es.projectalpha.pa.antium.cmd.RegisterCMD;
import es.projectalpha.pa.antium.events.PlayerEvents;
import es.projectalpha.pa.antium.manager.PassManager;
import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.MySQL;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class PAAntium extends JavaPlugin {

    @Getter
    private static PACore plugin = PACore.getInstance();
    @Getter
    private static PAAntium instance;

    @Getter
    private PassManager passManager;

    @Getter
    private MySQL mysql = null;
    @Getter
    private Connection connection = null;

    public static PAUser getUser(OfflinePlayer p) {
        return PAServer.getUser(p);
    }

    public void onEnable() {
        instance = this;

        plugin.debugLog("Cargando MySQL...");
        try {
            mysql = new MySQL("projectalpha.es", "projectalpha", "root", "vivalapepa123");
            connection = mysql.openConnection();
        } catch (SQLException | ClassNotFoundException exc) {
            getLogger().severe("Error al abrir la conexion MySQL!");
            plugin.debugLog("Causa: " + exc.toString());
            getLogger().severe("PACore desactivado por imposibilidad de conexiones");
            getServer().getPluginManager().disablePlugin(this); //Desactivar si no hay MySQL (Solo dará errores si esta activo)
        }

        plugin.debugLog("Registrando clases, eventos y permisos...");
        registerClasses();
        registerEvents();
        registerCommands();
    }

    public void onDisable() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }

    private void registerClasses() {
        passManager = new PassManager(instance);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerEvents(instance), instance);
    }

    private void registerCommands() {
        PACommands.register(new LoginCMD(), new RegisterCMD(), new AntiumCMD());
    }
}
