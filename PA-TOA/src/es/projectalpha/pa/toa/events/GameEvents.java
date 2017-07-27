package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.kits.Race;
import es.projectalpha.pa.toa.manager.Experience;
import es.projectalpha.pa.toa.mobs.Mob;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.HashMap;

public class GameEvents implements Listener {

    private TOA plugin;

    public GameEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        TOAUser u;
        if (e.getEntity() instanceof Monster && e.getEntity().getKiller() instanceof Player) {
            u = TOA.getPlayer(e.getEntity().getKiller());
            String name = e.getEntity().getCustomName().split(" ")[1];
            int level = Utils.isInt(name) ? Integer.parseInt(name) : 0;

            new Experience(u).addExp(Mob.getXP(level));

            if (plugin.getSpawnTask().getCount() == 0) return;
            plugin.getSpawnTask().setCount(plugin.getSpawnTask().getCount() - 1);
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Animals) e.setCancelled(true);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final TOAUser u = TOA.getPlayer(e.getPlayer());
        final Race r = Race.parseRace(u.getUserData().getKit());
        final int lvl = u.getUserData().getLvl();

        e.setCancelled(true);

        switch (u.getUserData().getKit()) {
            case 0:

                break;
            case 1:
                switch (e.getItem().getType()) {
                    case ARMOR_STAND:

                        break;
                    case SUGAR:
                        if (r != null) u.getPlayer().setWalkSpeed(0.4f);
                        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                            if (r != null) r.addEffects(u);
                        }, 100);
                        break;
                    case FEATHER:
                        final Item d1 = u.getLoc().getWorld().dropItemNaturally(u.getLoc(), new ItemStack(Material.IRON_SWORD));
                        final Item d2 = u.getLoc().getWorld().dropItemNaturally(u.getLoc(), new ItemStack(Material.IRON_SWORD));

                        d1.setVelocity(u.getPlayer().getVelocity().multiply(2));
                        d2.setVelocity(u.getPlayer().getVelocity().multiply(2));

                        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
                            World w = d1.getLocation().getWorld();

                            w.getNearbyEntities(d1.getLocation(), 1, 1, 1).stream()
                                    .filter(en -> !en.getType().equals(EntityType.PLAYER)).forEach(en -> {
                                if (en instanceof Monster) ((Monster) en).damage(40 + (lvl * 0.7));
                                d1.remove();
                            });
                            w.getNearbyEntities(d2.getLocation(), 1, 1, 1).stream()
                                    .filter(en -> !en.getType().equals(EntityType.PLAYER)).forEach(en -> {
                                if (en instanceof Monster) ((Monster) en).damage(40 + (lvl * 0.7));
                                d2.remove();
                            });

                        }, 0, 20);
                        break;
                    case APPLE:
                        u.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, true, false));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
