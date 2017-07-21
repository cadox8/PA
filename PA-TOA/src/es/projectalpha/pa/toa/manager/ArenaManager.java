package es.projectalpha.pa.toa.manager;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.mobs.Mob;
import es.projectalpha.pa.toa.mobs.MobType;
import org.bukkit.Location;

import java.util.ArrayList;

public class ArenaManager {

    public ArrayList<Mob> mobs = new ArrayList<>();
    private TOA plugin;

    public ArenaManager(TOA instance) {
        this.plugin = instance;
        initArena();
    }

    private void initArena() {
        for (String key : plugin.getConfig().getConfigurationSection("Mobs").getKeys(false)) {
            System.out.println(plugin.getConfig().getString("Mobs." + key + ".loc"));
            Location l = Utils.stringToLocation(plugin.getConfig().getString("Mobs." + key + ".loc"));
            int level = plugin.getConfig().getInt("Mobs." + key + ".level");
            MobType mt = MobType.parseMobType(plugin.getConfig().getInt("Mobs." + key + ".type"));
            mobs.add(new Mob(level, mt, l));
        }
    }

    public Location getCity() {
        return Utils.stringToLocation(plugin.getConfig().getString("Spawns.city"));
    }

    public Location getTower() {
        return Utils.stringToLocation(plugin.getConfig().getString("Spawns.tower"));
    }
}
