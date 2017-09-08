package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

public class SpectatorTask extends BukkitRunnable {

    private RagePlayer ragePlayer;
    private RageGames plugin;

    public SpectatorTask(RageGames plugin, RagePlayer ragePlayer) {
        this.plugin = plugin;
        this.ragePlayer = ragePlayer;
        ragePlayer.getPlayer().setGameMode(GameMode.SPECTATOR);
        ragePlayer.getPlayer().teleport(plugin.getAm().getRandomSpawn());
    }

    public void run() {
        if (!ragePlayer.isOnline() || ragePlayer == null) cancel();

        ragePlayer.sendActionBar("&cEstas en modo espectador, pon &6/lobby &cpara salir");

        if (ragePlayer.getPlayer().getGameMode() != GameMode.SPECTATOR) ragePlayer.getPlayer().setGameMode(GameMode.SPECTATOR);
    }
}
