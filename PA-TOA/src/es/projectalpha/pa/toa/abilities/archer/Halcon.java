package es.projectalpha.pa.toa.abilities.archer;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.List;

public class Halcon extends Ability {

    public Halcon() {
        super("Halcon", 10, Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        List<TOAUser> h = getAbilities().get(AbilityType.HALCON);
        h.add(u);
        getAbilities().put(AbilityType.HALCON, h);

        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> {
            List<TOAUser> k = getAbilities().get(AbilityType.HALCON);
            k.remove(u);
            getAbilities().put(AbilityType.HALCON, k);
        }, 20 * 3);
        cool.setOnCooldown(getName());
    }
}
