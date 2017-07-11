package es.projectalpha.pa.toa.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;

public class Mobs {

    private Location l;
    private int level;
    private World w;

    public Mobs(Location l, int level){
        this.l = l;
        this.level = level;
        this.w = l.getWorld();
    }

    public void spawnZombie(){
        Zombie z = (Zombie) w.spawnEntity(l, EntityType.ZOMBIE);

        z.setCustomName("Zombie " + level);
        z.setCustomNameVisible(false);
        z.setMaxHealth(100 + ((20 * 0.2) * level));
        z.setHealth(z.getMaxHealth());
        z.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(0.2);

        z.teleport(l);
    }

    public void spawnSkeleton(){
        Skeleton z = (Skeleton) w.spawnEntity(l, EntityType.SKELETON);

        z.setCustomName("Skeleton " + level);
        z.setCustomNameVisible(false);
        z.setMaxHealth(100 + ((20 * 0.1) * level));
        z.setHealth(z.getMaxHealth());
        z.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1.2);
        z.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(1.1);

        z.teleport(l);
    }

    public void spawnBlaze(){
        Blaze z = (Blaze) w.spawnEntity(l, EntityType.BLAZE);

        z.setCustomName("Blaze " + level);
        z.setCustomNameVisible(false);
        z.setMaxHealth(100 + ((20 * 0.3) * level));
        z.setHealth(z.getMaxHealth());
        z.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1.2);

        z.teleport(l);
    }
}
