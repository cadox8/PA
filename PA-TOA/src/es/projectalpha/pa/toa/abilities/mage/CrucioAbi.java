package es.projectalpha.pa.toa.abilities.mage;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import es.projectalpha.pa.toa.spells.Spell;

public class CrucioAbi extends Ability {

    public CrucioAbi() {
        super("Crucio", 1, Race.RaceType.MAGE);
    }

    public void play(TOAUser u) {
        if (!canUse(u)) return;
        plugin.getGm().selectSpell(u, Spell.CRUCIO);
    }
}
