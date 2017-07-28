package es.projectalpha.pa.toa.abilities.list;

import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;

public class Dagas extends Ability {

    public Dagas() {
        super("Dagas", Race.RaceType.PICARO);
    }

    public void play(TOAUser u){
        if (!canUse(u)) return;
        int lvl = u.getUserData().getLvl();

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
    }
}
