package es.projectalpha.pa.toa.atribs;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.HashMap;

public class Health {

    private HashMap<TOAUser, Double> health;

    private TOA plugin;

    public Health(TOA instance) {
        this.plugin = instance;
        health = new HashMap<>();
    }

    public void setHealth(TOAUser u, double value) {
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

    public void addHealth(TOAUser u, double value) {
        setHealth(u, getHealth(u) + value);
    }

    public void remHealth(TOAUser u, double value) {
        setHealth(u, getHealth(u) - value);
    }

    public double getHealth(TOAUser u) {
        return health.getOrDefault(u, 0D);
    }

    public void ajustHealth(TOAUser u) {
        setHealth(u, healthPerLevel(u));
    }

    public double healthPerLevel(TOAUser u) {
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
