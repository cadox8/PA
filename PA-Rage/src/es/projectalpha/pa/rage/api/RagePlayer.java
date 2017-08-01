package es.projectalpha.pa.rage.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.GameState;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.tasks.GameTask;
import es.projectalpha.pa.rage.tasks.LobbyTask;
import es.projectalpha.pa.rage.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RagePlayer extends PAUser {

    private RageGames plugin = RageGames.getInstance();

    public RagePlayer(String name) {
        super(name);
    }

    public void setLobby() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(RageGames.gs.get("rage").equals(false)){
                    ScoreboardUtil lboard = new ScoreboardUtil(PAData.RG.getOldPrefix(), "lobby");
                    if (getPlayer() == null) cancel();
                    if (plugin.getGm().acceptPlayers()) {
                    lboard.setName(PAData.RG.getOldPrefix());
                        lboard.text(5, "§d ");
                        lboard.text(4, "§6" + plugin.getGm().getPlaying().size() + "§d/§6" + plugin.getAm().getMaxPlayers());
                        lboard.text(3, "§a ");
                        lboard.text(2, "§eEsperando...");
                        lboard.text(1, "§e ");
                        lboard.text(0, "§b" + PACore.getIP());
                        if (getPlayer() != null) lboard.build(getPlayer());
                    } else {
                    lboard.reset();
                        cancel();
                    }
                }
                if(RageGames.gs.get("rage").equals(true)) {
                    ScoreboardUtil gboard = new ScoreboardUtil(PAData.RG.getOldPrefix(), "game");
                    if (getPlayer() == null) cancel();
                    if (GameState.getState() == GameState.INGAME) {
                        plugin.getGm().reorder();
                        gboard.setName(PAData.RG.getOldPrefix());

                        int x = 0;
                        for (RagePlayer u : plugin.getGm().getTop()) {
                            gboard.text(x, u.getName() + ": " + plugin.getGm().getScore().get(u));
                            x++;
                        }
                        gboard.text(-1, "§e ");
                        gboard.text(-2, "§b" + PACore.getIP());

                        if (getPlayer() != null) gboard.build(getPlayer());
                    } else {
                        gboard.reset();
                        cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }

    /*public void setGame() {
        ScoreboardUtil board = new ScoreboardUtil(PAData.RG.getOldPrefix(), "game");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();
                if (GameState.getState() == GameState.INGAME) {
                    plugin.getGm().reorder();
                    board.setName(PAData.RG.getOldPrefix());

                    int x = 0;
                    for (RagePlayer u : plugin.getGm().getTop()) {

                        board.text(x, u.getName() + ": " + plugin.getGm().getScore().get(u));

                        x++;
                    }
                    board.text(-1, "§e ");
                    board.text(-2, "§b" + PACore.getIP());

                    if (getPlayer() != null) board.build(getPlayer());
                } else {
                    board.reset();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }*/

    public void sendToGame() {
        resetPlayer();
        teleport(plugin.getAm().getRandomSpawn());
        plugin.getGm().getScore().put(this, 0);
    }

    public void resetPlayer() {
        getPlayer().setHealth(getPlayer().getMaxHealth());
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setItem(0, Items.getKnife());
        getPlayer().getInventory().setItem(1, Items.getBow());
        getPlayer().getInventory().setItem(2, Items.getAxe());
        getPlayer().getInventory().setItem(9, Items.getArrow());
    }
}
