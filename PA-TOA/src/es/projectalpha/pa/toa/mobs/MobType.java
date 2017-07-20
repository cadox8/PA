package es.projectalpha.pa.toa.mobs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MobType {
    UKNOWN(-1), ZOMBIE(0), SKELETON(1), BLAZE(2);

    @Getter
    private int id;

    public static MobType parseMobType(int id) {
        switch (id) {
            case 0:
                return MobType.ZOMBIE;
            case 1:
                return MobType.SKELETON;
            case 2:
                return MobType.BLAZE;
        }
        return MobType.UKNOWN;
    }
}
