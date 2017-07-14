package es.projectalpha.pa.rage.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.rage.utils.Items;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RagePlayer extends PAUser {

    private RageGames plugin = RageGames.getInstance();

    public RagePlayer(UUID uuid){
        super(uuid);
    }

    public void setLobby() {
        ScoreboardUtil board = new ScoreboardUtil(PAData.RG.getOldPrefix(), "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (plugin.getGm().acceptPlayers()) {
                    board.setName(PAData.RG.getOldPrefix());
                    board.text(5, "§d ");
                    board.text(4, "§6" + plugin.getGm().getPlaying().size() + "§d/§6" + plugin.getAm().getMaxPlayers());
                    board.text(3, "§a ");
                    board.text(2, "§eEsperando...");
                    board.text(1, "§e ");
                    board.text(0, PACore.getIP());
                    if (getPlayer() != null) board.build(getPlayer());
                } else {
                    board.reset();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void setGame() {
        ScoreboardUtil board = new ScoreboardUtil(PAData.RG.getOldPrefix(), "game");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (plugin.getGm().acceptPlayers()) {
                    board.setName(PAData.RG.getOldPrefix());
                    board.text(5, "§d ");
                    board.text(4, "§6Kills: ");
                    board.text(3, "§a ");
                    board.text(2, "§eMuertes: ");
                    board.text(1, "§e ");
                    board.text(0, PACore.getIP());
                    if (getPlayer() != null) board.build(getPlayer());
                } else {
                    board.reset();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
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
