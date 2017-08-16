package es.projectalpha.pa.toa.tasks;

import es.projectalpha.pa.toa.TOA;
import org.bukkit.scheduler.BukkitRunnable;

public class InfoTask extends BukkitRunnable {

    private TOA plugin;

    public InfoTask(TOA instance) {
        this.plugin = instance;
    }

    public void run() {
        plugin.getGm().getInTower().forEach(u -> u.sendActionBar("&4VIDA: &6" + plugin.getHealth().getHealth(u)));
    }
}
