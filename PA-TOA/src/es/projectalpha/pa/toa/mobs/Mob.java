package es.projectalpha.pa.toa.mobs;

import lombok.Getter;
import org.bukkit.Location;

public class Mob {

    private final int HEALTH_BASE = 500;

    @Getter private int level;
    @Getter private MobType mobType;
    @Getter private Location l;

    public Mob(int level, MobType mobType, Location l) {
        this.level = level;
        this.mobType = mobType;
        this.l = l;
    }

    public void spawn() {
        Mobs mobs = new Mobs(l, level);
        int health = getHealth(mobType);
        switch (mobType) {
            case ZOMBIE:
                mobs.spawnZombie(health);
                break;
            case SKELETON:
                mobs.spawnSkeleton(health);
                break;
            case BLAZE:
                mobs.spawnBlaze(health);
                break;
            case UKNOWN:
                break;
        }
    }

    private int getHealth(MobType mt) {
        int mob = 0;

        switch (mt) {
            case ZOMBIE:
                mob = 35;
                break;
            case SKELETON:
                mob = 25;
                break;
            case BLAZE:
                mob = 30;
                break;
        }
        return ((HEALTH_BASE + mob) + ((HEALTH_BASE + mob) * (level / 10)));
    }

    public static int getXP(int level) {
        int XP_BASE = 10;
        return (int)((XP_BASE * level) + ((XP_BASE * level) * 0.08));
    }
}
