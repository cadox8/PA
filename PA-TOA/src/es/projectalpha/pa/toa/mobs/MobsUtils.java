package es.projectalpha.pa.toa.mobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;


public class MobsUtils {

    @AllArgsConstructor
    public enum MobType {
        ZOMBIE(0), SKELETON(1), BLAZE(2);

        @Getter private int id;
    }

    public static void spawnMob(int id, Location l, int level){
        spawnMob(parseMobType(id), l, level);
    }

    public static void spawnMob(MobType mt, Location l, int level){
        Mobs mobs = new Mobs(l, level);
        switch (mt){
            case ZOMBIE:
                mobs.spawnZombie();
                break;
            case SKELETON:
                mobs.spawnSkeleton();
                break;
            case BLAZE:
                mobs.spawnBlaze();
                break;
        }
    }

    public static MobType parseMobType(int id){
        switch (id){
            case 0:
                return MobType.ZOMBIE;
            case 1:
                return MobType.SKELETON;
            case 2:
                return MobType.BLAZE;
        }
        return MobType.ZOMBIE;
    }
}
