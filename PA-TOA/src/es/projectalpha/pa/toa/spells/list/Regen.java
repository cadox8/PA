package es.projectalpha.pa.toa.spells.list;

import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.spells.Spell;

public class Regen extends Spell {

    public Regen(int difficulty) {
        super("Regeneración", 30, difficulty);
    }

    public void spell(TOAUser u) {
        int percent = (int)(plugin.getHealth().healthPerLevel(u) * (20 + (u.getUserData().getLvl() * 0.2)));
        plugin.getHealth().addHealth(u, percent);
    }
}
