package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.drops.DropsManager;
import es.projectalpha.pa.toa.mobs.MobType;
import es.projectalpha.pa.toa.races.Race;
import es.projectalpha.pa.toa.manager.Experience;
import es.projectalpha.pa.toa.mobs.Mob;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameEvents implements Listener {

    private TOA plugin;

    public GameEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Monster && e.getEntity().getKiller() instanceof Player) {
            TOAUser u = TOA.getPlayer(e.getEntity().getKiller());
            String name = e.getEntity().getCustomName().split(" ")[1];
            int level = Utils.isInt(name) ? Integer.parseInt(name) : 0;

            new Experience(u).addExp(Mob.getXP(level));

            DropsManager.drop(MobType.parseMobType(e.getEntityType()), u.getUserData().getKit()).forEach(d -> {
                BagEvents.addItem(u, d);
                u.sendSound(Sound.ITEM_PICKUP);
            });

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
        System.out.println(e.getClickedBlock().getType());
        if(e.getClickedBlock() == null) return;
        if(e.getClickedBlock().getType() == Material.WALL_SIGN) return;
        e.setCancelled(true);

        if (e.getItem() == null || !plugin.getGm().getInTower().contains(u)) return;
        Ability.useAbility(u, e.getItem().getType());
    }
}
