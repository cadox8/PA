package es.projectalpha.pa.toa.mobs;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

public class Mobs {

    private Location l;
    private int level;
    private World w;

    public Mobs(Location l, int level) {
        this.l = l;
        this.level = level;
        this.w = l.getWorld();
    }

    public void spawnZombie(int health) {
        Zombie z = (Zombie) w.spawnEntity(l, EntityType.ZOMBIE);

        z.setCustomName("Zombie " + level);
        z.setCustomNameVisible(false);
        z.setMaxHealth(health);
        z.setHealth(z.getMaxHealth());

        z.teleport(l);
    }

    public void spawnSkeleton(int health) {
        Skeleton z = (Skeleton) w.spawnEntity(l, EntityType.SKELETON);

        z.setCustomName("Skeleton " + level);
        z.setCustomNameVisible(false);
        z.setMaxHealth(health);
        z.setHealth(z.getMaxHealth());

        z.teleport(l);
    }

    public void spawnBlaze(int health) {
        Blaze z = (Blaze) w.spawnEntity(l, EntityType.BLAZE);

        z.setCustomName("Blaze " + level);
        z.setCustomNameVisible(false);
        z.setMaxHealth(health);
        z.setHealth(z.getMaxHealth());

        z.teleport(l);
    }
}
