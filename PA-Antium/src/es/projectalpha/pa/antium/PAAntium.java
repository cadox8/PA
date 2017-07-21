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
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class PAAntium extends JavaPlugin {

    @Getter private static PACore plugin;
    @Getter private static PAAntium instance;

    @Getter private PassManager passManager;

    public static PAUser getUser(OfflinePlayer p) {
        return PAServer.getUser(p);
    }

    public void onEnable() {
        instance = this;

        if (getServer().getPluginManager().getPlugin("PA-Core") == null) getServer().getPluginManager().disablePlugin(this);

        filter();

        plugin = PACore.getInstance();
        plugin.debugLog("Registrando clases, eventos y permisos...");

        registerClasses();
        registerEvents();
        registerCommands();
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

    private void filter() {
        Filter f = new Filter() {
            public boolean isLoggable(LogRecord line) {
                return !(line.getMessage().toLowerCase().startsWith("/login") || line.getMessage().toLowerCase().startsWith("/register"));
            }

            public String doFilter(String arg0) {
                return null;
            }
            public String doFilterUrl(String arg0) {
                return null;
            }
        };
        getLogger().setFilter(f);
    }
}
