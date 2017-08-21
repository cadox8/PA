package es.projectalpha.pa.toa.abilities.tank;

import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.List;

public class Escudo extends Ability {

    public Escudo() {
        super("Escudo", 15, Race.RaceType.WARRIOR);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        List<TOAUser> h = getAbilities().get(AbilityType.ESCUDO);
        h.add(u);
        getAbilities().put(AbilityType.ESCUDO, h);

        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> {
            List<TOAUser> k = getAbilities().get(AbilityType.ESCUDO);
            k.remove(u);
            getAbilities().put(AbilityType.ESCUDO, k);
        }, 20 * 3);
        new Cooldown(getCooldown()).setOnCooldown(getName());
    }
}
