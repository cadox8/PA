package es.projectalpha.pa.toa.mobs;

import lombok.Getter;
import org.bukkit.Location;

public class Mob {

    @Getter private int level;
    @Getter private MobType mobType;
    @Getter private Location l;

    private final double XP_BASE = 10;

    public Mob(int level, MobType mobType, Location l) {
        this.level = level;
        this.mobType = mobType;
        this.l = l;
    }

    public void spawn() {
        Mobs mobs = new Mobs(l, level);
        switch (mobType){
            case ZOMBIE:
                mobs.spawnZombie();
                break;
            case SKELETON:
                mobs.spawnSkeleton();
                break;
            case BLAZE:
                mobs.spawnBlaze();
                break;
            case UKNOWN:
                break;
        }
    }

    public int exp() {
        return (int)((XP_BASE * level) * ((XP_BASE * level) * 0.08));
    }
}
