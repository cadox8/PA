package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.BossBarUtils;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.core.utils.Title;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.manager.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                    p.teleport(plugin.getAm().getRandomSpawn());
                    Title.sendTitle(p.getPlayer(),1,2,1, ChatColor.GREEN + "Ronda de calentamiento","");
                });
                break;
            case 180:
                plugin.getGm().getPlaying().forEach(p -> {
                    plugin.getGm().resetPoint(p);
                    p.teleport(plugin.getAm().getRandomSpawn());
                    Title.sendTitle(p.getPlayer(),1,2,1,ChatColor.RED + "¡Empieza el juego!","");
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

    private void checkWinner() {
        plugin.getGm().reorder();
        Utils.broadcastMsg("------------------------");
        Utils.broadcastMsg("");
        Utils.broadcastMsg("1º " + plugin.getGm().getTop().get(0).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)) + " puntos.");
        Utils.broadcastMsg("2º " + plugin.getGm().getTop().get(1).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(1)) + " puntos.");

        if(plugin.getGm().getTop().get(2).getName() != null) {
            Utils.broadcastMsg("3º " + plugin.getGm().getTop().get(2).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(2)) + " puntos.");
        }

        Utils.broadcastMsg("");
        Utils.broadcastMsg("------------------------");

        plugin.getGm().getPlaying().forEach(p ->{
            p.getPlayer().getInventory().clear();
            p.getPlayer().setGameMode(GameMode.SPECTATOR);
        });
    }
}
