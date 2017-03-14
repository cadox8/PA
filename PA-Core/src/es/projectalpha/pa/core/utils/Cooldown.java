package es.projectalpha.pa.core.utils;

import lombok.Getter;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Cooldown {

    @Getter private final int time;
    @Getter private final HashMap<String, Long> cooldowns;
    @Getter private final HashMap<Chest, Long> chests;

    public Cooldown(int time) {
        this.time = time;
        this.cooldowns = new HashMap<>();
        this.chests = new HashMap<>();
    }

    public void setOnCooldown(Player player) {
        getCooldowns().put(player.getName(), System.currentTimeMillis());
    }

    public void removeCooldown(Player player){
        getCooldowns().remove(player);
    }

    public void setOnCooldown(Chest chest) {
        getChests().put(chest, System.currentTimeMillis());
    }

    public void removeCooldown(Chest chest){
        getChests().remove(chest);
    }


    public int getTimeLeft(Chest chest) {
        if (!isCoolingDown(chest)) {
            return 0;
        }
        return (int) (((getChests().get(chest) - (System.currentTimeMillis() - (getTime() * 1000))) / 1000) + 1);
    }

    public int getTimeLeft(String str) {
        if (!isCoolingDown(str)) {
            return 0;
        }
        return (int) (((getCooldowns().get(str) - (System.currentTimeMillis() - (getTime() * 1000))) / 1000) + 1);
    }


    public boolean isCoolingDown(Player player) {
        return isCoolingDown(player.getName());
    }

    public boolean isCoolingDown(String str) {
        if (!getCooldowns().containsKey(str)) {
            return false;
        }
        return getCooldowns().get(str) >= (System.currentTimeMillis() - (getTime() * 1000));
    }

    public boolean isCoolingDown(Chest chest) {
        if (!getChests().containsKey(chest)) {
            return false;
        }
        return getChests().get(chest) >= (System.currentTimeMillis() - (getTime() * 1000));
    }
}
