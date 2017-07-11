package es.projectalpha.pa.toa.manager;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;

public class ArenaManager {

    private TOA plugin;

    @Getter private ArrayList<Location> spawns = new ArrayList<>();

    public ArenaManager(TOA instance) {
        this.plugin = instance;
    }

    public void initArena() {
        for (String str : plugin.getConfig().getConfigurationSection("TOA.spawns").getKeys(false)) {
            spawns.add(Utils.stringToLocation(str));
        }
    }

    public Location getCity() {
        return Utils.stringToLocation(plugin.getConfig().getString("Spawns.city"));
    }

    public Location getTower() {
        return Utils.stringToLocation(plugin.getConfig().getString("Spawns.tower"));
    }
}
