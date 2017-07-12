package es.projectalpha.pa.lobby.api;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class LobbyPlayer extends PAUser {

    private PALobby plugin = PALobby.getInstance();

    public LobbyPlayer(UUID uuid){
        super(uuid);
    }

    public void lobbyScoreboard(){
        ScoreboardUtil board = new ScoreboardUtil(PAData.PAPlugins.LOBBY.getPrefix(), "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(PALobby.getInstance(), 0, 20);
    }

    public void sendToSpawn() {
        teleport(Utils.stringToLocation(plugin.getConfig().getString("spawn")));
    }
}
