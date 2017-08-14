package es.projectalpha.pa.toa.abilities.archer;

import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

public class Fenix extends Ability {

    public Fenix() {
        super("Fenix", 24, Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        getFireArrow().add(u);
        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> getFireArrow().remove(u), 20 * 7);
        new Cooldown(getCooldown()).setOnCooldown(getName());
    }
}
