package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.manager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final RageGames plugin;

    public GameTask(RageGames instance) {
        this.plugin = instance;
    }
    private GameManager gm;

    private int count = 180;
    private int fcount = 10;

    public void run() {

        count--;

        if(count == 0){
            fcount--;
            if(fcount == 0){
                gm.getPlaying().forEach(p ->{
                gm.removePlayerFromGame(p);
            });
            }
        }
    }
}
