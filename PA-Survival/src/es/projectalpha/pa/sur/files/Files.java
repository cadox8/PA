package es.projectalpha.pa.sur.files;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

        @Getter public static File fileUser = new File("plugins/PA-Survival/", "config.yml");
        private static YamlConfiguration user = YamlConfiguration.loadConfiguration(fileUser);

    public void setupFiles() {
        if (!fileUser.exists()) {
            fileUser.mkdir();
        }
        saveFiles();
    }

    public static void saveFiles() {
        try {
            user.save(fileUser);
            user.load(fileUser);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getUser() {
        return YamlConfiguration.loadConfiguration(fileUser);
    }

}
