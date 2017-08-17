package es.projectalpha.pa.core.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkAPI {

    public static Firework spawnFirework(Location l, FireworkEffect.Type type, Color color, Color fade, int power){
        return spawnFirework(l, type, color, fade, true, true, power);
    }

    public static Firework spawnFirework(Location l, FireworkEffect.Type type, Color color, Color fade, boolean trail, boolean flicker, int power) {
        Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder().flicker(flicker).withColor(color).withFade(fade).with(type).trail(trail).build();

        fwm.addEffect(effect);
        fwm.setPower(power);

        fw.setFireworkMeta(fwm);

        return fw;
    }
}
