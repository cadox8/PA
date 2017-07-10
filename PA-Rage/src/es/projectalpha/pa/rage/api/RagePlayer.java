package es.projectalpha.pa.rage.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class RagePlayer extends PAUser {

    private RageGames plugin = RageGames.getInstance();

    public RagePlayer(UUID uuid){
        super(uuid);
    }

    public void setLobby() {
        ScoreboardUtil board = new ScoreboardUtil(PAData.RAGE.fullPrefix(), "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (plugin.getGm().acceptPlayers()) {
                    board.setName(PAData.RAGE.getPrefix());
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
        ScoreboardUtil board = new ScoreboardUtil(PAData.RAGE.fullPrefix(), "game");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (plugin.getGm().acceptPlayers()) {
                    board.setName(PAData.RAGE.getPrefix());
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
}
