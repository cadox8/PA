package es.projectalpha.pa.toa.abilities.list;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.Material;

public class Frenesi extends Ability {

    public Frenesi() {
        super("Frenesi", Race.RaceType.PICARO);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;

        u.getPlayer().setWalkSpeed(0.4f);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            Race.parseRace(getRace().getId()).addEffects(u);
        }, 100);
    }
}
