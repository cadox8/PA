package es.projectalpha.pa.toa;

import es.projectalpha.pa.toa.manager.ArenaManager;
import es.projectalpha.pa.toa.manager.GameManager;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class TOA extends JavaPlugin {

    @Getter private static TOA instance;
    @Getter private static String prefix = ChatColor.GRAY + " || " + ChatColor.RED + "TOA" + ChatColor.GRAY + " || ";

    @Getter private ArenaManager am;
    @Getter private GameManager gm;

    public void onEnable(){
        instance = this;
        registerClasses();
    }

    private void registerClasses() {
        am = new ArenaManager(instance);
        gm = new GameManager(instance);
    }
}
