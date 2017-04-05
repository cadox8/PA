package es.projectalpha.pa.dtb.tasks;

import es.projectalpha.pa.dtb.DTB;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {

    private DTB plugin;

    private int count = 30;

    public LobbyTask(DTB instance){
        this.plugin = instance;
    }

    public void run(){
        if (plugin.getGameManager().getPlaying().size() < 3) return;

        switch (count){
            case 30:
                plugin.getGameManager().getPlaying().forEach(u -> u.sendMessagePrefix("&6El juego comentazá en &c30 &6segundos"));
                break;
        }

        count--;
    }
}
