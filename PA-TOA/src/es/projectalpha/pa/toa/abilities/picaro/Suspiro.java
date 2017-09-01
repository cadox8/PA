package es.projectalpha.pa.toa.abilities.picaro;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.List;

public class Suspiro extends Ability {

    public Suspiro() {
        super("Suspiro", 23, Race.RaceType.PICARO);
    }

    public void play(TOAUser u){
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        List<TOAUser> h = getAbilities().get(AbilityType.SUSPIRO);
        h.add(u);
        getAbilities().put(AbilityType.SUSPIRO, h);

        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> {
            List<TOAUser> k = getAbilities().get(AbilityType.SUSPIRO);
            k.remove(u);
            getAbilities().put(AbilityType.SUSPIRO, k);
        }, 20 * 3);
        cool.setOnCooldown(getName());
    }
}
