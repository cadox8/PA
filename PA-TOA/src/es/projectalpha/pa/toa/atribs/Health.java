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

    public void regen(TOAUser u, double value) {
        setHealth(u, getHealth(u) + value);
    }

    public void damage(TOAUser u, double value) {
        double realValue = value - plugin.getArmor().armorPerLevel(u);
        setHealth(u, getHealth(u) - realValue);
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
                return 38 * u.getUserData().getLvl() + Race.WARRIOR.getHealth();
            case 1:
                return 35 * u.getUserData().getLvl() + Race.PICARO.getHealth();
            case 2:
                return 33 * u.getUserData().getLvl() + Race.ARCHER.getHealth();
            case 3:
                return 27 * u.getUserData().getLvl() + Race.MAGE.getHealth();
            default:
                return 0;
        }
    }
}
