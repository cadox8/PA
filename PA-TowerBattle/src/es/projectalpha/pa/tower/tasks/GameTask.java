package es.projectalpha.pa.tower.tasks;

import es.projectalpha.pa.tower.TowerBattle;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final TowerBattle plugin;
    private int count = 180;

    public GameTask(TowerBattle instance) {
        this.plugin = instance;
    }

    public void run() {

        plugin.getGm().getPlaying().forEach(p -> TowerBattle.getPlayer(p).sendActionBar("&3Tiempo restante: &c" + count));

        count--;
    }
}
