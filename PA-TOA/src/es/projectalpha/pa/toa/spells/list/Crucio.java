package es.projectalpha.pa.toa.spells.list;

import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.spells.Spell;
import org.bukkit.entity.Monster;

public class Crucio extends Spell {

    public Crucio(int difficulty) {
        super("Crucio", 20, difficulty);
    }

    public void spell(TOAUser u, Monster m) {

    }
}
