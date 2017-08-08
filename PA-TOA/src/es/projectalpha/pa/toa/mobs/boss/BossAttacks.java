package es.projectalpha.pa.toa.mobs.boss;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.inventivetalent.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossAttacks {

    private static TOA plugin = TOA.getInstance();
    private static Random r = new Random();

    public static void giantAttacks(Giant boss, Player damager){
        int attack = r.nextInt(8);
        List<Player> near = new ArrayList<>();

        boss.getNearbyEntities(7, 7, 7).forEach(en -> {
            if (en instanceof Player) {
                near.add((Player) en);
            }
        });

        switch (attack){
            case 0:
                near.forEach(p -> {
                    if (!p.equals(damager)) {
                        p.getWorld().strikeLightningEffect(p.getLocation());
                        plugin.getHealth().damage(TOA.getPlayer(p), 10);

                        if (r.nextBoolean()) p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 0));
                        if (r.nextBoolean()) p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30, 0));
                    }
                });

                damager.getWorld().strikeLightningEffect(damager.getLocation());
                plugin.getHealth().damage(TOA.getPlayer(damager), 20);

                if (r.nextBoolean()) damager.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 0));
                if (r.nextBoolean()) damager.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 30, 0));
                break;
            case 1:
                ArrayList<Location> locs = Utils.getCircle(boss.getEyeLocation(), 7, 30);

                locs.forEach(l -> {
                    ParticleEffect.REDSTONE.sendColor(plugin.getServer().getOnlinePlayers(), l, Color.fromBGR(100, 60, 50));
                    ParticleEffect.REDSTONE.sendColor(plugin.getServer().getOnlinePlayers(), l.subtract(0, 7, 0), Color.fromBGR(200, 60, 50));
                });

                near.forEach(p -> {
                    if (!p.equals(damager)) {
                        ParticleEffect.EXPLOSION_NORMAL.send(plugin.getServer().getOnlinePlayers(), p.getLocation(), 0, 0, 0, 1, 10);
                        plugin.getHealth().damage(TOA.getPlayer(p), 30);
                    }
                });
                ParticleEffect.EXPLOSION_NORMAL.send(plugin.getServer().getOnlinePlayers(), damager.getLocation(), 0, 0, 0, 1, 10);
                plugin.getHealth().damage(TOA.getPlayer(damager), 30);
                break;
            case 2:
                if (boss.getHealth() + 20 >= boss.getMaxHealth()) boss.setHealth(boss.getMaxHealth());
                boss.setHealth(boss.getHealth() + 20);
                break;
            default:
                break;
        }
    }
}
