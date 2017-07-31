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
    private LobbyTask gt = new LobbyTask(plugin);

    public void setLobby() {
        ScoreboardUtil board = new ScoreboardUtil(PAData.RG.getOldPrefix(), "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();
                if(plugin.rage.get("rage").equals(true)){
                    if (plugin.getGm().acceptPlayers()) {
                        board.setName(PAData.RG.getOldPrefix());
                        board.text(5, "§d ");
                        board.text(4, "§6" + plugin.getGm().getPlaying().size() + "§d/§6" + plugin.getAm().getMaxPlayers());
                        board.text(3, "§a ");
                        board.text(2, "§eEsperando...");
                        board.text(1, "§e ");
                        board.text(0, "§b" + PACore.getIP());
                        if (getPlayer() != null) board.build(getPlayer());
                    } else {
                        board.reset();
                        cancel();
                    }
                }

                if(plugin.rage.get("rage").equals(false)){
                    board.reset();
                    for(int p = 1; p <= Bukkit.getOnlinePlayers().size(); p++) {
                        System.out.println(p);
                        final int s = p;
                        List<Integer> list = new ArrayList<>(plugin.getGm().getScore().values());
                        Collections.sort(list, Collections.reverseOrder());
                        plugin.getGm().getScore().keySet().forEach(k -> list.subList(0, s).forEach(v -> {
                            board.setName(PAData.RG.getOldPrefix());
                            plugin.getGm().getScore().keySet().forEach(u ->{
                                System.out.println(s);
                                System.out.println(plugin.getGm().getTop().get(0).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)));
                                board.text(0, plugin.getGm().getTop().get(7).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(7)));
                                board.text(1, plugin.getGm().getTop().get(6).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(6)));
                                board.text(2, plugin.getGm().getTop().get(5).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(5)));
                                board.text(3, plugin.getGm().getTop().get(4).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(4)));
                                board.text(4, plugin.getGm().getTop().get(3).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(3)));
                                board.text(5, plugin.getGm().getTop().get(2).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(2)));
                                board.text(6, plugin.getGm().getTop().get(1).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(1)));
                                board.text(7, plugin.getGm().getTop().get(0).getName() + ": " + plugin.getGm().getScore().get(plugin.getGm().getTop().get(0)));
                                    });
                            board.text(-1, "§e ");
                            board.text(-2, "§b" + PACore.getIP());
                            if (getPlayer() != null) board.build(getPlayer());
                        }));
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
                    board.setName(PAData.RG.getOldPrefix());
                    plugin.getGm().getScore().keySet().forEach(u -> board.text(plugin.getGm().getScore().get(u), u.getName()));
                    board.text(-1, "§e ");
                    board.text(-2, PACore.getIP());
                    if (getPlayer() != null) board.build(getPlayer());
                } else {
                    board.reset();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
*/
    public void resetPlayer() {
        getPlayer().setHealth(getPlayer().getMaxHealth());
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setItem(0, Items.getKnife());
        getPlayer().getInventory().setItem(1, Items.getBow());
        getPlayer().getInventory().setItem(2, Items.getAxe());
        getPlayer().getInventory().setItem(9, Items.getArrow());
    }
}
