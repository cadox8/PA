package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.utils.GameState;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.manager.GameManager;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

import static es.projectalpha.pa.rage.RageGames.gs;

public class LobbyTask extends BukkitRunnable {

    private final RageGames plugin;
    public int count = 40;

    public LobbyTask(RageGames instance) {
        this.plugin = instance;
    }

    @Override
    public void run() {
        if (plugin.getGm().getPlaying().size() < plugin.getAm().getMinPlayers()) {
            plugin.getGm().setCheckStart(true);
            plugin.getServer().getOnlinePlayers().forEach(pl -> pl.setLevel(0));
            GameState.setState(GameState.LOBBY);
            cancel();
            return;
        }

        plugin.getGm().getPlaying().forEach(p -> p.sendActionBar("&a&lEl juego empieza en: " + count));

        switch (count) {
            case 30:
                Utils.broadcastMsg("&7El juego empezará en &c30 &7segundos");
                break;
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                Utils.broadcastMsg("&7El juego empezará en &c" + count + " &7segundos");
                plugin.getGm().getPlaying().forEach(p -> p.sendSound(Sound.NOTE_PLING));
                break;
            case 0:
                plugin.getGm().getPlaying().forEach(RagePlayer::sendToGame);
                GameState.setState(GameState.INGAME);
                gs.put("rage", true);
                new GameTask(plugin).runTaskTimer(plugin, 0, 20);
                cancel();
                break;
        }
        --count;
    }
}
