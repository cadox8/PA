package es.projectalpha.pa.toa;

import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.atribs.Health;
import es.projectalpha.pa.toa.drops.DropsManager;
import es.projectalpha.pa.toa.events.BagEvents;
import es.projectalpha.pa.toa.events.GameEvents;
import es.projectalpha.pa.toa.events.PlayerEvents;
import es.projectalpha.pa.toa.manager.ArenaManager;
import es.projectalpha.pa.toa.manager.GameManager;
import es.projectalpha.pa.toa.tasks.SpawnTask;
import es.projectalpha.pa.toa.utils.FileUtils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TOA extends JavaPlugin {

    public static ArrayList<TOAUser> users = new ArrayList<>();

    @Getter private static TOA instance;
    @Getter private static String prefix = ChatColor.GRAY + " || " + ChatColor.RED + "TOA" + ChatColor.GRAY + " || ";

    @Getter private ArenaManager am;
    @Getter private GameManager gm;
    @Getter private DropsManager drops;
    @Getter private FileUtils fileUtils;
    @Getter private Health health;

    @Getter private SpawnTask spawnTask;

    public static TOAUser getPlayer(OfflinePlayer p) {
        for (TOAUser pl : users) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(p.getUniqueId())) return pl;
        }
        TOAUser us = new TOAUser(p.getUniqueId());
        if (us.isOnline()) users.add(us);
        return us;
    }

    public void onEnable() {
        instance = this;
        FileUtils.init();
        registerClasses();
        registerEvents();

        spawnTask.runTaskTimer(instance, 0, 20);
    }

    private void registerClasses() {
        fileUtils = new FileUtils();
        am = new ArenaManager(instance);
        gm = new GameManager(instance);
        drops = new DropsManager();
        spawnTask = new SpawnTask(instance);
        health = new Health(instance);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BagEvents(instance), instance);
        pm.registerEvents(new GameEvents(instance), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
    }
}
