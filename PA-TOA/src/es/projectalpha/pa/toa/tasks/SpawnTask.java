package es.projectalpha.pa.toa.tasks;

import es.projectalpha.pa.toa.TOA;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnTask extends BukkitRunnable {

    private TOA plugin;

    @Getter @Setter private int count = 0;

    public SpawnTask(TOA instance) {
        this.plugin = instance;
    }

    public void run() {
        if (count >= 300 || TOA.users.isEmpty()) return;

        plugin.getAm().mobs.forEach(m -> {
            count++;
            m.spawn();
        });
    }
}
