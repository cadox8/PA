package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class LobbyPlayer extends PAUser {

    public LobbyPlayer(UUID uuid){
        super(uuid);
    }

    public LobbyPlayer(OfflinePlayer p){
        this(p.getUniqueId());
    }

    public void lobbyScoreboard(){
        ScoreboardUtil board = new ScoreboardUtil("&cLobby", "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(PALobby.getInstance(), 0, 20);
    }
}
