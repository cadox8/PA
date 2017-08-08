package es.projectalpha.pa.toa.mobs.boss;

import es.projectalpha.pa.toa.mobs.Mob;
import es.projectalpha.pa.toa.mobs.MobType;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;

public class Boss {

    private Location l;

    public Boss(Location l) {
        this.l = l;
    }

    public void bossGiant() {
        Giant giant = (Giant) l.getWorld().spawnEntity(l, EntityType.GIANT);

        giant.setMaxHealth(1500);
        giant.setHealth(giant.getMaxHealth());

        giant.setCustomName("Zombie Gigante");

        giant.teleport(l);
    }
}
