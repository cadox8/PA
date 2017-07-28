package es.projectalpha.pa.rage.files;


import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    @Getter private File fileConfig = new File("plugins/PA-Rage/", "config.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

    public void setupFiles() {
        System.out.println("SetupFiles antes de detectar fileConfig");
        if (!fileConfig.exists()) {
            System.out.println("SetupFiles generandose");
            fileConfig.mkdir();
            //config.set("puntos", 0);
        }
        System.out.println("SetupFiles detectado, no se regenera");
        saveFiles();
        System.out.println("Archivo guardado y cargados, setupFiles finalizado.");
    }

    public void saveFiles() {
        try {
            config.save(fileConfig);
            System.out.println("Guardando archivos");
            config.load(fileConfig);
            System.out.println("Cargando archivos");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(fileConfig);
    }
}
