package es.projectalpha.pa.toa.abilities.archer;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.List;

public class Fenix extends Ability {

    public Fenix() {
        super("Fenix", 24, Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        List<TOAUser> h = getAbilities().get(AbilityType.FENIX);
        h.add(u);
        getAbilities().put(AbilityType.FENIX, h);

        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> {
            List<TOAUser> k = getAbilities().get(AbilityType.FENIX);
            k.remove(u);
            getAbilities().put(AbilityType.FENIX, k);
        }, 20 * 7);
        cool.setOnCooldown(getName());
    }
}
