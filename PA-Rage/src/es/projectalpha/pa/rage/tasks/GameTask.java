package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.utils.BossBarUtils;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<Integer> list = new ArrayList<>(plugin.getGm().getScore().values());
        Collections.sort(list, Collections.reverseOrder());

        plugin.getGm().getScore().keySet().forEach(k -> list.subList(0, 3).forEach(v -> {
            if (plugin.getGm().getScore().get(k).equals(v)) plugin.getGm().getTop().add(k);
        }));
        Bukkit.broadcastMessage("------------------------");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("1º " + plugin.getGm().getTop().get(0) + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)) + " puntos.");
        Bukkit.broadcastMessage("2º " + plugin.getGm().getTop().get(1) + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)) + " puntos.");
        Bukkit.broadcastMessage("3º " + plugin.getGm().getTop().get(2) + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)) + " puntos.");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("------------------------");

        plugin.getGm().getPlaying().forEach(p ->{
            p.getPlayer().getInventory().clear();
            p.getPlayer().setGameMode(GameMode.SPECTATOR);
                });

    }
}
