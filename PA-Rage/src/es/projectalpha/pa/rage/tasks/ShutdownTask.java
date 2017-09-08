package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Log;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class ShutdownTask extends BukkitRunnable {

    private final RageGames plugin;
    private int count = 10;

    public ShutdownTask(RageGames instance) {
        this.plugin = instance;
    }

    public void run() {
        RageGames.players.forEach(r -> r.sendMessage(Messages.getMessage(Messages.SEND, PAData.BUNGEE, "%t%", count + "")));

        switch (count) {
            case 2:
                removeAll();
                break;
            case 0:
                cancel();
                plugin.getServer().shutdown();
                break;
        }
        count--;
    }

    private void removeAll() {
        try {
            plugin.getGm().getPlaying().forEach(p -> plugin.getGm().removePlayerFromGame(p));
            RageGames.players.forEach(RagePlayer::sendToLobby);
        } catch (NullPointerException e) {
            Log.debugLog("Error al mandar a los jugadores al Lobby");
            Log.debugLog(plugin.getGm().getPlaying().toString());
        }
    }
}
