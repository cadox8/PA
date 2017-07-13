package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.utils.BossBarUtils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.manager.GameManager;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final RageGames plugin;
    private BossBar bossBar;

    public GameTask(RageGames instance) {
        this.plugin = instance;
        bossBar = BossBarUtils.bossBar("&cTiempo restante: ", BarColor.GREEN);
    }

    //El GameManager accedes por plugin.getGm()

    private int count = 180;

    public void run() {
        bossBar = BossBarUtils.editBossBar(bossBar, "&cTiempo restante: &6" + count);

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
