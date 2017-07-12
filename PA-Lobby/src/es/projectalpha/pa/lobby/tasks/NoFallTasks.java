package es.projectalpha.pa.lobby.tasks;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class NoFallTasks extends BukkitRunnable {

    private PALobby plugin;

    public NoFallTasks(PALobby instance) {
        this.plugin = instance;
    }

    public void run() {
        PALobby.players.forEach(l -> {
            if (l.getLoc().getY() <= 0) l.sendToSpawn();
        });
    }
}
