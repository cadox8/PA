package es.projectalpha.pa.toa;

import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.drops.DropsManager;
import es.projectalpha.pa.toa.manager.ArenaManager;
import es.projectalpha.pa.toa.manager.GameManager;
import es.projectalpha.pa.toa.tasks.SpawnTask;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TOA extends JavaPlugin {

    @Getter private static TOA instance;
    @Getter private static String prefix = ChatColor.GRAY + " || " + ChatColor.RED + "TOA" + ChatColor.GRAY + " || ";

    public static ArrayList<TOAUser> users = new ArrayList<>();

    @Getter private ArenaManager am;
    @Getter private GameManager gm;
    @Getter private DropsManager drops;

    @Getter private SpawnTask spawnTask;

    public void onEnable(){
        instance = this;
        registerClasses();

        spawnTask.runTaskTimer(instance, 0, 20);
    }

    private void registerClasses() {
        am = new ArenaManager(instance);
        gm = new GameManager(instance);
        drops = new DropsManager();
        spawnTask = new SpawnTask(instance);
    }



    public static TOAUser getPlayer(OfflinePlayer p) {
        for (TOAUser pl : users) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(p.getUniqueId())) return pl;
        }
        TOAUser us = new TOAUser(p.getUniqueId());
        if (us.isOnline()) users.add(us);
        return us;
    }
}
