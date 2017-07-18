package es.projectalpha.pa.lobby.files;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Files {

    private File fileConfig = new File("plugins/PA-Lobby/", "config.yml");
    @Getter private YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

    public void setupFiles() {

        if (!fileConfig.exists()) {
            fileConfig.mkdir();
        }
        try{
            config.save(fileConfig);
            config.load(fileConfig);
        }catch (java.io.IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

}

