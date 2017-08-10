package es.projectalpha.pa.rage.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.*;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.manager.ArenaManager;
import es.projectalpha.pa.rage.utils.Items;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
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
            p.getPlayer().setFireTicks(0);
            p.getPlayer().setHealthScale(20d);
        });

        switch (count) {
            case 210:
                plugin.getGm().getPlaying().forEach(p -> {
                    p.teleport(plugin.getAm().getRandomSpawn());
                    Title.sendTitle(p.getPlayer(),1,2,1, ChatColor.GREEN + "Ronda de calentamiento","");
                    p.getPlayer().setWalkSpeed(0.3f);
                });
                break;
            case 180:
                plugin.getGm().getPlaying().forEach(p -> {
                    plugin.getGm().resetPoint(p);
                    p.resetPlayer();
                    p.teleport(plugin.getAm().getRandomSpawn());
                    Title.sendTitle(p.getPlayer(),1,2,1,ChatColor.RED + "¡Empieza el juego!","");
                    p.getPlayer().setWalkSpeed(0.3f);
                });
                break;
            case 60:
                plugin.getGm().getPlaying().forEach(p -> {
                    Title.sendTitle(p.getPlayer(),1,2,1,ChatColor.RED + "¡60 segundos!",ChatColor.GOLD + "¡Velocidad x2!");
                    p.getPlayer().setWalkSpeed(0.4f);
                });

                break;
            case 3:
                checkWinner();
                break;
            case 0:
                new ShutdownTask(plugin).runTaskTimer(plugin, 0, 20);
                GameState.setState(GameState.FINISHED);
                cancel();
                break;
        }
        count--;
    }

    private void checkWinner() {
        RagePlayer[] users = plugin.getGm().reorder().keySet().toArray(new RagePlayer[plugin.getGm().reorder().size()]);

        Utils.broadcastMsg("------------------------");
        Utils.broadcastMsg("");
        Utils.broadcastMsg("1º " + users[0].getName() + ": " + plugin.getGm().getScore().get(users[0]) + " puntos.");
        Utils.broadcastMsg("2º " + users[1].getName() + ": " + plugin.getGm().getScore().get(users[1]) + " puntos.");

        if(users.length >= 3) {
            Utils.broadcastMsg("3º " + users[2].getName() + ": " + plugin.getGm().getScore().get(users[2]) + " puntos.");
        }

        Utils.broadcastMsg("");
        Utils.broadcastMsg("------------------------");

        plugin.getGm().getPlaying().forEach(p ->{
            p.getPlayer().getInventory().clear();
            p.getPlayer().setGameMode(GameMode.SPECTATOR);
        });
    }
}
