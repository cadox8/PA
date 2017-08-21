package es.projectalpha.pa.toa.abilities.tank;

import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

public class Berseker extends Ability {

    public Berseker() {
        super("Berseker", 15, Race.RaceType.WARRIOR);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        plugin.getHealth().regen(u, plugin.getHealth().getHealth(u) * 0.2);

        new Cooldown(getCooldown()).setOnCooldown(getName());
    }
}
