package es.projectalpha.pa.sur.tasks;

import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.casino.Casino;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimationTask extends BukkitRunnable {

    private Casino casino;
    private SurvivalUser u;
    private int in;

    private int count = 5;

    public AnimationTask(Casino casino, SurvivalUser u, int in) {
        this.casino = casino;
        this.u = u;
        this.in = in;
    }

    public void run() {
        switch (count) {
            case 0:
                casino.reward(u, 0, null);
                break;
        }

        count--;
    }
}
