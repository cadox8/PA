package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.rage.RageGames;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final RageGames plugin;

    public GameTask(RageGames instance) {
        this.plugin = instance;
    }

    private int count = 180;

    public void run() {

        count--;
    }
}
