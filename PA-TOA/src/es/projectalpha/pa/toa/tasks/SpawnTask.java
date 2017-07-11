package es.projectalpha.pa.toa.tasks;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTask extends BukkitRunnable {

    private TOA plugin;

    public SpawnTask(TOA instance) {
        this.plugin = instance;
    }

    public void run(){
        for (String str : plugin.getConfig().getConfigurationSection("TOA.mobs").getKeys(false)) {

        }
    }
}
