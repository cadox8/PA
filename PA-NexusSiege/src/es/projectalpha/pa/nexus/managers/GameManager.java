package es.projectalpha.pa.nexus.managers;

import es.projectalpha.pa.nexus.NexusSiege;
import es.projectalpha.pa.nexus.tasks.LobbyTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private NexusSiege plugin;

    @Getter
    private ArrayList<Player> playing = new ArrayList<>();

    @Getter
    @Setter
    private boolean checkStart = true;

    public GameManager(NexusSiege instance) {
        this.plugin = instance;
    }

    public void checkStart() {
        if (checkStart && playing.size() >= plugin.getAm().getMinPlayers()) {
            checkStart = false;
            new LobbyTask(plugin).runTaskTimer(plugin, 0, 20);
        }
    }
}
