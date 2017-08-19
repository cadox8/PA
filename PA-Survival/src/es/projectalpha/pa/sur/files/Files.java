package es.projectalpha.pa.sur.files;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    @Getter public static File fileUser = new File("plugins/PA-Survival/", "users.yml");
    public static YamlConfiguration user = YamlConfiguration.loadConfiguration(fileUser);

    @Getter public static File fileStone = new File("plugins/PA-Survival/", "stones.yml");
    public static YamlConfiguration stone = YamlConfiguration.loadConfiguration(fileStone);

    public void setupFiles() {
        if (!fileUser.exists()) {
            fileUser.mkdir();
            user.set("recaudado", 0);
            user.set("loteria", 100);
            user.set("Users.0", "");
        }

        if (!fileStone.exists()) {
            fileStone.mkdir();
            stone.set("tstones", 0);
        }

        saveFiles();
    }

    public static void saveFiles() {
        try {
            user.save(fileUser);
            user.load(fileUser);

            stone.save(fileStone);
            stone.load(fileStone);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getUser() {
        return YamlConfiguration.loadConfiguration(fileUser);
    }
    public YamlConfiguration getStone() {
        return YamlConfiguration.loadConfiguration(fileStone);
    }


}
