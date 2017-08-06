package es.projectalpha.pa.nexus.tasks;

import es.projectalpha.pa.nexus.NexusSiege;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final NexusSiege plugin;
    private int count = 180;

    public GameTask(NexusSiege instance) {
        this.plugin = instance;
    }

    public void run() {

        plugin.getGm().getPlaying().forEach(p -> NexusSiege.getPlayer(p).sendActionBar("&3Tiempo restante: &c" + count));

        count--;
    }
}
