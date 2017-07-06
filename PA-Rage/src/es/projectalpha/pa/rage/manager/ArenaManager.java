package es.projectalpha.pa.rage.manager;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Random;

public class ArenaManager {

    private RageGames plugin;

    @Getter private int minPlayers = 2;
    @Getter private int maxPlayers = 8;

    private ArrayList<Location> spawns = new ArrayList<>();

    public ArenaManager(RageGames instance) {
        this.plugin = instance;
    }

    public void prepareWorld(World w) {
        w.setPVP(true);
        w.setGameRuleValue("doDaylightCycle", "false");
        w.setStorm(false);
        w.setDifficulty(Difficulty.PEACEFUL);
        w.setTime(14000);
        w.getLivingEntities().stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(e -> e.damage(e.getMaxHealth()));
        initArena();
        w.setAutoSave(false);
    }

    public void initArena() {
        for (String str : plugin.getConfig().getConfigurationSection("Rage.spawns").getKeys(false)) {
            spawns.add(Utils.stringToLocation(str));
        }
    }

    public Location getRandomSpawn() {
        return spawns.get(new Random().nextInt(spawns.size()));
    }
}
