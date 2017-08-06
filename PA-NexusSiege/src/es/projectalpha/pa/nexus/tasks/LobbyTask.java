package es.projectalpha.pa.nexus.tasks;

import es.projectalpha.pa.core.utils.GameState;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.nexus.NexusSiege;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {

    private final NexusSiege plugin;
    private int count = 40;

    public LobbyTask(NexusSiege instance) {
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

        plugin.getGm().getPlaying().forEach(p -> {
            NexusSiege.getPlayer(p).sendActionBar("&a&lEl juego empieza en: " + count);
        });

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
                plugin.getGm().getPlaying().forEach(p -> p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F));
                break;
            case 0:
                new GameTask(plugin).runTaskTimer(plugin, 0, 20);
                cancel();
                break;
        }
        --count;
    }
}
