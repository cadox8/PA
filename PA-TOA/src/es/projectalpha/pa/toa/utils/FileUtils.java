package es.projectalpha.pa.toa.utils;

import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.toa.TOA;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;

public class FileUtils {

    private static final TOA plugin = TOA.getInstance();

    public static File fConfig;
    public static File fInv;
    private static FileConfiguration fcInv = null;
    private static File InvFile = null;

    public static void init() {
        fConfig = new File(plugin.getDataFolder(), "config.yml");
        if (!fConfig.exists()) {
            try {
                plugin.getConfig().options().copyDefaults(true);
                plugin.saveConfig();
                Log.debugLog("Generando archivo config.yml correctamente");
            } catch (Exception e) {
                Log.log(Log.Level.WARNING, "Fallo al generar el config.yml!");
            }
        }

        fInv = new File(plugin.getDataFolder(), "inv.yml");
        if (!fInv.exists()) {
            try {
                getInv().options().copyDefaults(true);
                saveInv();
                Log.debugLog("Generando archivo inv.yml correctamente");
            } catch (Exception e) {
                Log.log(Log.Level.WARNING, "Fallo al generar el inv.yml!");
            }
        }
    }

    public static void reloadInv() {
        if (InvFile == null) InvFile = new File(plugin.getDataFolder(), "inv.yml");
        fcInv = YamlConfiguration.loadConfiguration(InvFile);

        InputStream defStream = plugin.getResource("inv.yml");
        if (defStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defStream);
            fcInv.setDefaults(defConfig);
        }
    }

    public static FileConfiguration getInv() {
        if (fcInv == null) reloadInv();
        return fcInv;
    }

    public static void saveInv() {
        if (fcInv == null || InvFile == null) {
            return;
        }
        try {
            getInv().save(InvFile);
        } catch (Exception ex) {
            Log.log(Log.Level.SEVERE, "No se ha podido guardar " + InvFile);
        }
    }
}
