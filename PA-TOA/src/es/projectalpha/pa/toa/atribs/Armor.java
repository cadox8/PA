package es.projectalpha.pa.toa.atribs;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.HashMap;

public class Armor {

    private TOA plugin;

    public Armor(TOA instance) {
        this.plugin = instance;
    }

    public double armorPerLevel(TOAUser u) {
        switch (u.getUserData().getKit()) {
            case 0:
                return 1.1 * u.getUserData().getLvl();
            case 1:
                return 0.7 * u.getUserData().getLvl();
            case 2:
                return 0.5 * u.getUserData().getLvl();
            case 3:
                return 0.3 * u.getUserData().getLvl();
            default:
                return 0;
        }
    }
}
