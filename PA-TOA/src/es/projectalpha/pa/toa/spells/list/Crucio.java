package es.projectalpha.pa.toa.spells.list;

import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.spells.Spell;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.util.Vector;

public class Crucio extends Spell {

    public Crucio(int difficulty) {
        super("Crucio", 4, difficulty);
    }

    public void spell(TOAUser u) {
        u.getWorld().getNearbyEntities(u.getLoc(), 2, 2, 2).stream().filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e instanceof Monster).forEach(e -> {
            Monster m = (Monster) e;
            m.damage(20 + (u.getUserData().getLvl() * 0.3));
            m.setVelocity(new Vector(0, 4, 0));
        });
    }
}
