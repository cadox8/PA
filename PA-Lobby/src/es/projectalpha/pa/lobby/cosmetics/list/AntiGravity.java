package es.projectalpha.pa.lobby.cosmetics.list;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.core.utils.FireworkAPI;
import es.projectalpha.pa.lobby.cosmetics.Cosmetic;
import es.projectalpha.pa.lobby.utils.Helpers;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class AntiGravity extends Cosmetic {

    public AntiGravity() {
        super("&bArco Dimensional", Material.DIAMOND_BARDING, new Cooldown(20));
    }

    private int count = 10;
    private BukkitTask bt;

    @Override
    public void play(PAUser u) {
        if (isInCooldown(u, getName())) return;

        final ArmorStand as = (ArmorStand) spawnEntity(u.getLoc(), EntityType.ARMOR_STAND);
        as.setGravity(false);
        as.setSmall(true);
        as.setVisible(false);
        as.setHelmet(new ItemStack(Material.SEA_LANTERN));
        as.setPassenger(u.getPlayer());

        as.teleport(as.getLocation().add(0, 5, 0));

        bt = plugin.getServer().getScheduler().runTaskTimer(plugin, ()-> {
            as.teleport(as.getLocation().add(0, 0.2, 0));

            if (count <= 0) {
                remove(u, as);
                bt.cancel();
                return;
            }
            count--;
        }, 0, 20);
    }

    private void remove(PAUser u, ArmorStand as) {
        as.eject();
        u.getPlayer().eject();

        FireworkAPI.spawnFirework(u.getLoc(), FireworkEffect.Type.BALL_LARGE, Color.GRAY, Color.MAROON, 10).detonate();

        u.getPlayer().setNoDamageTicks(100);
        new Helpers(u).sendToSpawn();

        as.remove();
    }
}
