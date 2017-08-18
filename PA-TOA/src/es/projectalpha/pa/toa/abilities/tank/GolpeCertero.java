package es.projectalpha.pa.toa.abilities.tank;

import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

import java.util.List;

public class GolpeCertero extends Ability {

    public GolpeCertero() {
        super("Golpe Certero", 24, Race.RaceType.WARRIOR);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        if (isInCooldown(u, getName())) return;

        List<TOAUser> h = getAbilities().get(AbilityType.GOLPE_CERTERO);
        h.add(u);
        getAbilities().put(AbilityType.GOLPE_CERTERO, h);

        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> {
            List<TOAUser> k = getAbilities().get(AbilityType.GOLPE_CERTERO);
            k.remove(u);
            getAbilities().put(AbilityType.GOLPE_CERTERO, k);
        }, 20 * 10);
        new Cooldown(getCooldown()).setOnCooldown(getName());
    }
}
