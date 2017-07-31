package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.BossBarUtils;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTask extends BukkitRunnable {

    private final RageGames plugin;
    private int count = 210;
    private RagePlayer rp;
    ScoreboardUtil board = new ScoreboardUtil(PAData.RG.getOldPrefix(), "lobby");
    //El GameManager accedes por plugin.getGm()

    public GameTask(RageGames instance) {
        this.plugin = instance;
    }

    public void run() {
        plugin.getGm().getPlaying().forEach(p -> {
            BossBarUtils.create(p.getPlayer(), "&cTiempo restante: &6" + count, BossBarAPI.Color.BLUE, BossBarAPI.Style.PROGRESS);
        });

        switch (count) {
            case 210:
                plugin.getGm().getPlaying().forEach(p -> {
                    plugin.getGm().addPoint(p,0);
                    p.teleport(plugin.getAm().getRandomSpawn());
                    RageGames.getPlayer(p.getPlayer()).resetPlayer();
                });
                break;
            case 180:
                plugin.getGm().getPlaying().forEach(p -> {
                    plugin.getGm().addPoint(p,0);
                    p.teleport(plugin.getAm().getRandomSpawn());
                    RageGames.getPlayer(p.getPlayer()).resetPlayer();
                });
                break;
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
        System.out.println(list);
        plugin.getGm().getScore().keySet().forEach(k -> list.subList(0, 2).forEach(v -> {
            if (plugin.getGm().getScore().get(k).equals(v)) plugin.getGm().getTop().add(k);
        }));
        Bukkit.broadcastMessage("------------------------");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("1º " + plugin.getGm().getTop().get(0).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)) + " puntos.");
        Bukkit.broadcastMessage("2º " + plugin.getGm().getTop().get(1).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(1)) + " puntos.");
        if(plugin.getGm().getTop().get(2).getName() != null) {
            Bukkit.broadcastMessage("3º " + plugin.getGm().getTop().get(2).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(2)) + " puntos.");
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("------------------------");
        plugin.getGm().getPlaying().forEach(p ->{
            p.getPlayer().getInventory().clear();
            p.getPlayer().setGameMode(GameMode.SPECTATOR);
                });

    }
}
