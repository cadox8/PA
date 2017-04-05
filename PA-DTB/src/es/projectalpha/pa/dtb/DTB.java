package es.projectalpha.pa.dtb;

import es.projectalpha.pa.dtb.managers.ArenaManager;
import es.projectalpha.pa.dtb.managers.GameManager;
import es.projectalpha.pa.dtb.tasks.LobbyTask;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DTB extends JavaPlugin {

    @Getter private static DTB instance;

    @Getter private ArenaManager arenaManager;
    @Getter private GameManager gameManager;

    public void onEnable(){
        instance = this;

        createConfig();
        registerClasses();
    }


    private void registerClasses(){
        arenaManager = new ArenaManager(instance);
        gameManager = new GameManager(instance);

        new LobbyTask(instance).runTaskTimer(instance, 0, 20);
    }

    private void createConfig(){
        File fConf = new File(getDataFolder(), "config.yml");
        if (!fConf.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
            } catch (Exception e) {}
        }
    }

    public static DTBPlayer getPlayer(OfflinePlayer p){
        return new DTBPlayer(p.getUniqueId());
    }
}
