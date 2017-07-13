package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.utils.Items;
import es.projectalpha.pa.rage.RageGames;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameEvents implements Listener {

    private RageGames plugin;

    public GameEvents(RageGames instance) {
        this.plugin = instance;
    }


    @EventHandler
    public void OnHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){

            Player d = (Player) e.getDamager();
            Player a = (Player) e.getEntity();

            if(d.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
               e.setCancelled(true);
               Utils.broadcastMsg(ChatColor.GOLD + d.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + a.getName() + ChatColor.GREEN + " usando " + Items.getKnife().getItemMeta().getDisplayName());
               RageGames.getPlayer(a).resetPlayer();
               a.teleport(plugin.getAm().getRandomSpawn());
            }

        }
    }

    @EventHandler
    public void OnProjectile(ProjectileHitEvent e){
        if (e.getEntity().getShooter() instanceof Player && e.getHitEntity() instanceof Player){
            Player s = (Player) e.getEntity().getShooter();
            Player h = (Player) e.getHitEntity();
            Projectile a = e.getEntity();

            if(a instanceof Arrow){
                s.getInventory().setItem(9, Items.getArrow());
                Utils.broadcastMsg(ChatColor.GOLD + h.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + s.getName() + ChatColor.GREEN + " usando " + Items.getArrow().getItemMeta().getDisplayName());

                h.teleport(plugin.getAm().getRandomSpawn());
                RageGames.getPlayer(h).resetPlayer();
            }

            if(a instanceof Snowball){
                Utils.broadcastMsg(ChatColor.GOLD + h.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + s.getName() + ChatColor.GREEN + " usando " + Items.getAxe().getItemMeta().getDisplayName());

                h.teleport(plugin.getAm().getRandomSpawn());
                RageGames.getPlayer(h).resetPlayer();
                a.getPassengers().forEach(Entity::remove);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if (p.getInventory().getItemInMainHand().getType() == Material.IRON_AXE){
            final Snowball sb = p.launchProjectile(Snowball.class);
            sb.addPassenger(p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.IRON_AXE)));
            p.getInventory().setItemInMainHand(null);
            sb.setShooter(p);
        }
    }
}
