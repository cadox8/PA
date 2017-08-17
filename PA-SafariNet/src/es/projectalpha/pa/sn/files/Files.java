package es.projectalpha.pa.sn.files;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cadox on 12/12/2016.
 */
public class Files {

    private File fileMobs = new File("plugins/SafariNet/", "mobs.yml");
    @Getter private YamlConfiguration mobs = YamlConfiguration.loadConfiguration(fileMobs);

    private File fileConfig = new File("plugins/SafariNet/", "config.yml");
    @Getter private YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

    public void setupFiles(){
        if (!fileMobs.exists()){
            fileMobs.mkdir();
        }
        if (!fileConfig.exists()){
            fileConfig.mkdir();

            config.set("coste.SHEEP", 10);
            config.set("coste.COW", 10);
            config.set("coste.MUSHROOM_COW", 10);
            config.set("coste.CHICKEN", 10);
            config.set("coste.HORSE", 10);
            config.set("coste.DONKEY", 10);
            config.set("coste.MULE", 10);
            config.set("coste.LLAMA", 10);
            config.set("coste.ZOMBIE", 10);
            config.set("coste.PIG_ZOMBIE", 10);
            config.set("coste.ZOMBIE_VILLAGER", 10);
            config.set("coste.ZOMBIE_HORSE", 10);
            config.set("coste.SKELETON_HORSE", 10);
            config.set("coste.SKELETON", 10);
            config.set("coste.WITHER_SKELETON", 10);
            config.set("coste.CREEPER", 10);
            config.set("coste.SPIDER", 10);
            config.set("coste.CAVE_SPIDER", 10);
            config.set("coste.BLAZE", 10);
            config.set("coste.GHAST", 10);
            config.set("coste.ENDERMAN", 10);
            config.set("coste.POLAR_BEAR", 10);
            config.set("coste.VINDICATOR", 10);
            config.set("coste.RABBIT", 10);
            config.set("coste.SQUID", 10);
            config.set("coste.WOLF", 10);
            config.set("coste.OCELOT", 10);
            config.set("coste.PIG", 10);
            config.set("coste.GUARDIAN", 10);

            //Words
            List<String> worlds = new ArrayList<>();

            worlds.add("pve");
            worlds.add("recursos");

            config.set("mundosPermitidos", worlds);
        }
        saveFiles();
    }

    public void saveFiles(){
        try {
            mobs.save(fileMobs);
            mobs.load(fileMobs);
            config.save(fileConfig);
            config.load(fileConfig);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public int getMobsCount(Player p){
        if (!mobs.contains("mobs." + p.getName())) {
            return 0;
        }
        return mobs.getInt("mobs." + p.getName());
    }

    public void addMob(Player p){
        int mob = getMobsCount(p);
        mob++;
        mobs.set("mobs." + p.getName(), mob);
        saveFiles();
    }
}
