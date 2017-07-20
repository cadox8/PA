package es.projectalpha.pa.toa.atribs;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.kits.Kit;

import java.util.HashMap;
import java.util.Map;

public class Health {

    private HashMap<TOAUser, Integer> health;

    private TOA plugin;

    public Health(TOA instance) {
        this.plugin = instance;
        health = new HashMap<>();
    }

    public void setHealth(TOAUser u, int value) {
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
        switch (u.getToaUserData().getKit()) {
            case 0:
                return 85 * u.getToaUserData().getLvl() + Kit.WARRIOR.getHealth();
            case 1:
                return 76 * u.getToaUserData().getLvl() + Kit.PICARO.getHealth();
            case 2:
                return 70 * u.getToaUserData().getLvl() + Kit.ARCHER.getHealth();
            default:
                return 0;
        }
    }
}
