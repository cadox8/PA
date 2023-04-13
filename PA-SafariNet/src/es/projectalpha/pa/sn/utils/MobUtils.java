package es.projectalpha.pa.sn.utils;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cadox on 12/12/2016.
 */
public class MobUtils {

    public List<EntityType> bannedMobs(){
        List<EntityType> mobs = new ArrayList<>();

        mobs.add(EntityType.WITHER);
        mobs.add(EntityType.ELDER_GUARDIAN);
        mobs.add(EntityType.ENDER_DRAGON);
        mobs.add(EntityType.EVOKER);
        mobs.add(EntityType.VEX);
        mobs.add(EntityType.SLIME);
        mobs.add(EntityType.ILLUSIONER);
        return mobs;
    }


    public boolean isAnimal(Entity e){
        return e instanceof Animals;
    }

    public boolean isSheep(Entity e){
        return e.getType() == EntityType.SHEEP;
    }

    public boolean isVillager(Entity e){
        return e.getType() == EntityType.VILLAGER;
    }

    public boolean isHorse(Entity e) {
        return e.getType() == EntityType.HORSE;
    }

    public boolean isDonkey(Entity e){
        return e.getType() == EntityType.DONKEY;
    }

    public boolean isMule(Entity e){
        return e.getType() == EntityType.MULE;
    }

    public boolean isSkeletonHorse(Entity e){
        return e.getType() == EntityType.SKELETON_HORSE;
    }

    public boolean isZombieHorse(Entity e){
        return e.getType() == EntityType.ZOMBIE_HORSE;
    }

    public boolean isLlama(Entity e){
        return e.getType() == EntityType.LLAMA;
    }

    public boolean isZombie(Entity e){
        return e.getType() == EntityType.ZOMBIE;
    }

    public boolean isPigZombie(Entity e){
        return e.getType() == EntityType.ZOMBIFIED_PIGLIN;
    }

    public boolean isZombieVillager(Entity e){
        return e.getType() == EntityType.ZOMBIE_VILLAGER;
    }

    public boolean isCreeper(Entity e){
        return e.getType() == EntityType.CREEPER;
    }

    public boolean isRabbit(Entity e){
        return e.getType() == EntityType.RABBIT;
    }

    public boolean isParrot(Entity e) {
        return e.getType() == EntityType.PARROT;
    }

    public boolean isShulker(Entity e) {
        return e.getType() == EntityType.SHULKER;
    }
}

