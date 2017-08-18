package es.projectalpha.pa.sur.files;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    @Getter public static File fileUser = new File("plugins/PA-Survival/", "users.yml");
    private static YamlConfiguration user = YamlConfiguration.loadConfiguration(fileUser);

    @Getter public static File fileStone = new File("plugins/PA-Survival/", "stones.yml");
    private static YamlConfiguration stone = YamlConfiguration.loadConfiguration(fileStone);

    @Getter public static File fileImp = new File("plugins/PA-Survival/", "impuestos.yml");
    private static YamlConfiguration imp = YamlConfiguration.loadConfiguration(fileImp);

    public void setupFiles() {
        if (!fileUser.exists()) {
            fileUser.mkdir();
            user.set("recaudado", 0);
            user.set("loteria", 100);
            imp.createSection("Users.");
        }

        if (!fileStone.exists()) {
            fileStone.mkdir();
            stone.set("piedras", 0);
        }

        if (!fileImp.exists()) {
            imp.createSection("impuestos");
            fileImp.mkdir();
        }
        saveFiles();
    }

    public static void saveFiles() {
        try {
            user.save(fileUser);
            user.load(fileUser);
            stone.save(fileStone);
            stone.load(fileStone);
            imp.save(fileImp);
            imp.load(fileImp);
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
    public YamlConfiguration getImp() {
        return YamlConfiguration.loadConfiguration(fileImp);
    }


}
