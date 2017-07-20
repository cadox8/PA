package es.projectalpha.pa.tower.managers;

import es.projectalpha.pa.tower.TowerBattle;
import es.projectalpha.pa.tower.tasks.LobbyTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private TowerBattle plugin;

    @Getter
    private ArrayList<Player> playing = new ArrayList<>();

    @Getter
    @Setter
    private boolean checkStart = true;

    public GameManager(TowerBattle instance) {
        this.plugin = instance;
    }

    public void checkStart() {
        if (checkStart && playing.size() >= plugin.getAm().getMinPlayers()) {
            checkStart = false;
            new LobbyTask(plugin).runTaskTimer(plugin, 0, 20);
        }
    }
}
