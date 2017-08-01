package es.projectalpha.pa.toa.abilities.list;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;

public class Fenix extends Ability {

    public Fenix() {
        super("Fenix", Race.RaceType.ARCHER);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;

        getFireArrow().add(u);

        plugin.getServer().getScheduler().runTaskLater(plugin, ()-> getFireArrow().remove(u), 20 *7);
    }
}
