package es.projectalpha.pa.rage.Files;


import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    private File fileConfig = new File("plugins/PA-Rage/", "config.yml");
    @Getter
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

    public void setupFiles() {

        if (!fileConfig.exists()) {
            fileConfig.mkdir();
            config.set("Rage.puntos", 0);
        }
        saveFiles();
    }

    public void saveFiles() {
        try {
            config.save(fileConfig);
            config.load(fileConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
