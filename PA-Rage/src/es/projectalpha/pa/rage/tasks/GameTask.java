package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.utils.BossBarUtils;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBarAPI;

public class GameTask extends BukkitRunnable {

    private final RageGames plugin;
    private int count = 180;

    //El GameManager accedes por plugin.getGm()

    public GameTask(RageGames instance) {
        this.plugin = instance;
    }

    public void run() {
        plugin.getGm().getPlaying().forEach(p -> {
            BossBarUtils.create(p.getPlayer(), "&cTiempo restante: &6" + count, BossBarAPI.Color.BLUE, BossBarAPI.Style.PROGRESS);
        });

        switch (count) {
            case 3:
                checkWinner();
                break;
            case 0:
                new ShutdownTask(plugin).runTaskTimer(plugin, 0, 20);
                cancel();
                break;
        }
        count--;
    }

    private void checkWinner() { //And StopGame

    }
}
