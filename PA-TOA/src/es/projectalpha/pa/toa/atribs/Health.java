package es.projectalpha.pa.toa.atribs;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.HashMap;

public class Health {

    private HashMap<TOAUser, Integer> health;

    private TOA plugin;

    public Health(TOA instance) {
        this.plugin = instance;
        health = new HashMap<>();
    }

    public void setHealth(TOAUser u, int value) {
        if (value >= healthPerLevel(u)) {
            health.put(u, healthPerLevel(u));
            return;
        }
        if (value <= 0) {
            u.death();
            return;
        }
        health.put(u, value);
    }

    public void addHealth(TOAUser u, int value) {
        setHealth(u, getHealth(u) + value);
    }

    public void remHealth(TOAUser u, int value) {
        setHealth(u, getHealth(u) - value);
    }

    public int getHealth(TOAUser u) {
        return health.getOrDefault(u, -1);
    }

    public void ajustHealth(TOAUser u) {
        setHealth(u, healthPerLevel(u));
    }

    public int healthPerLevel(TOAUser u) {
        switch (u.getUserData().getKit()) {
            case 0:
                return 85 * u.getUserData().getLvl() + Race.WARRIOR.getHealth();
            case 1:
                return 76 * u.getUserData().getLvl() + Race.PICARO.getHealth();
            case 2:
                return 70 * u.getUserData().getLvl() + Race.ARCHER.getHealth();
            default:
                return 0;
        }
    }
}
