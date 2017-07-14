package es.projectalpha.pa.lobby.tasks;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.lobby.PALobby;
import es.projectalpha.pa.lobby.utils.Helpers;
import org.bukkit.scheduler.BukkitRunnable;

public class NoFallTasks extends BukkitRunnable {

    private PALobby plugin;

    public NoFallTasks(PALobby instance) {
        this.plugin = instance;
    }

    public void run() {
        if (PAServer.users.isEmpty()) return;
        PAServer.users.forEach(l -> {
            if (l.getLoc().getY() <= 0) new Helpers(l).sendToSpawn();
        });
    }
}
