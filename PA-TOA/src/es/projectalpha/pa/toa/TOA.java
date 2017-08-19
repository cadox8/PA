package es.projectalpha.pa.toa;

import es.projectalpha.pa.core.PACommands;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.atribs.Armor;
import es.projectalpha.pa.toa.atribs.Health;
import es.projectalpha.pa.toa.cmd.MobsCMD;
import es.projectalpha.pa.toa.cmd.MochilaCMD;
import es.projectalpha.pa.toa.cmd.RaceCMD;
import es.projectalpha.pa.toa.cmd.ShopsCMD;
import es.projectalpha.pa.toa.drops.DropsManager;
import es.projectalpha.pa.toa.events.BagEvents;
import es.projectalpha.pa.toa.events.GameEvents;
import es.projectalpha.pa.toa.events.PlayerEvents;
import es.projectalpha.pa.toa.manager.ArenaManager;
import es.projectalpha.pa.toa.manager.GameManager;
import es.projectalpha.pa.toa.races.Race;
import es.projectalpha.pa.toa.tasks.InfoTask;
import es.projectalpha.pa.toa.tasks.SpawnTask;
import es.projectalpha.pa.toa.utils.FileUtils;
import es.projectalpha.pa.toa.utils.TOAMenu;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TOA extends JavaPlugin {

    public static ArrayList<TOAUser> users = new ArrayList<>();

    @Getter private static TOA instance;

    @Getter private ArenaManager am;
    @Getter private GameManager gm;
    @Getter private DropsManager drops;
    @Getter private FileUtils fileUtils;
    @Getter private Health health;
    @Getter private Armor armor;
    @Getter private TOAMenu toaMenu;

    @Getter private SpawnTask spawnTask;

    public void onEnable() {
        instance = this;

        if (getServer().getPluginManager().getPlugin("PA-Core") == null) getServer().getPluginManager().disablePlugin(this);

        FileUtils.init();
        registerClasses();
        registerEvents();

        spawnTask.removeAll();
        spawnTask.runTaskTimer(instance, 0, 25 * 20);
        new InfoTask(instance).runTaskTimer(instance, 0, 20);
    }

    private void registerClasses() {
        fileUtils = new FileUtils();
        am = new ArenaManager(instance);
        gm = new GameManager(instance);
        drops = new DropsManager();
        spawnTask = new SpawnTask(instance);
        health = new Health(instance);
        armor = new Armor(instance);
        toaMenu = new TOAMenu(instance);

        Race.registerRaces();

        PACommands.register(new MobsCMD(), new RaceCMD(), new MochilaCMD(), new ShopsCMD());
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BagEvents(instance), instance);
        pm.registerEvents(new GameEvents(instance), instance);
        pm.registerEvents(new PlayerEvents(instance), instance);
    }


    public static TOAUser getPlayer(OfflinePlayer p) {
        for (TOAUser pl : users) {
            if (pl.getName() == null || p.getName() == null) continue;
            if (pl.getName().equalsIgnoreCase(p.getName())) return pl;
        }
        TOAUser us = new TOAUser(p.getName());
        if (us.isOnline()) users.add(us);
        return us;
    }
}
