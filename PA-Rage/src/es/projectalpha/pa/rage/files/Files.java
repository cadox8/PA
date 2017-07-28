package es.projectalpha.pa.rage.files;


import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    @Getter public static File fileConfig = new File("plugins/PA-Rage/", "config.yml");
    public static YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

    public void setupFiles() {
        if (!fileConfig.exists()) {
            fileConfig.mkdir();
            config.set("puntos", 0);
        }
        saveFiles();
    }

    public static void saveFiles() {
        try {
            config.save(fileConfig);
            config.load(fileConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(fileConfig);
    }
}
