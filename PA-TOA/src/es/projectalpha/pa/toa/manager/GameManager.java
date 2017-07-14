package es.projectalpha.pa.toa.manager;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import lombok.Getter;

import java.util.ArrayList;

public class GameManager {

    private TOA plugin;

    @Getter private ArrayList<TOAUser> inTower = new ArrayList<>();

    public GameManager(TOA instance) {
        this.plugin = instance;
    }

    public void joinTower(TOAUser u) {
        if (inTower.contains(u)) return;

        inTower.add(u);
    }

    public void leaveTower(TOAUser u) {
        if (!inTower.contains(u)) return;

        inTower.remove(u);
    }
}
