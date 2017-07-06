package es.projectalpha.pa.rage.manager;

import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.tasks.LobbyTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private RageGames plugin;

    @Getter private ArrayList<Player> playing = new ArrayList<>();

    @Getter @Setter private boolean checkStart = true;

    public GameManager(RageGames instance) {
        this.plugin = instance;
    }

    public void checkStart() {
        if (checkStart && playing.size() >= plugin.getAm().getMinPlayers()) {
            checkStart = false;
            new LobbyTask(plugin).runTaskTimer(plugin, 0, 20);
        }
    }

    public void addPlayerToGame(Player player) {
        if (playing.contains(player)) {
            playing.remove(player);
            playing.add(player);
        } else {
            playing.add(player);
        }
    }

    public void removePlayerFromGame(Player p) {
        playing.remove(p);
    }

    public boolean acceptPlayers() {
        return GameState.getState() == GameState.LOBBY;
    }

    public boolean isInLobby() {
        return GameState.getState() == GameState.LOBBY;
    }
}
