package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.utils.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameEvents implements Listener {

    private RageGames plugin;

    public GameEvents(RageGames instance) {
        this.plugin = instance;
    }


    @EventHandler
    public void OnHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {

            Player d = (Player) e.getDamager();
            Player a = (Player) e.getEntity();

            if(d.getInventory().getItemInHand() == null){
                e.setCancelled(true);
                e.setDamage(0);
            }

            if (d.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
                e.setCancelled(true);
                Utils.broadcastMsg(ChatColor.GOLD + d.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + a.getName() + ChatColor.GREEN + " usando " + Items.getKnife().getItemMeta().getDisplayName());
                RageGames.getPlayer(a).resetPlayer();
                a.teleport(plugin.getAm().getRandomSpawn());
                plugin.getGm().addPoint(RageGames.getPlayer(d), 20);
            }
        }

        if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
            Player s = (Player) ((Projectile) e.getDamager()).getShooter();
            Player h = (Player) e.getEntity();
            Projectile a = (Projectile) e.getDamager();

            if (a instanceof Arrow) {
                e.setCancelled(true);
                s.getInventory().setItem(9, Items.getArrow());
                Utils.broadcastMsg(ChatColor.GOLD + h.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + s.getName() + ChatColor.GREEN + " usando " + Items.getArrow().getItemMeta().getDisplayName());

                h.teleport(plugin.getAm().getRandomSpawn());
                RageGames.getPlayer(h).resetPlayer();
                plugin.getGm().addPoint(RageGames.getPlayer(s), 30);
            }

            if (a instanceof Snowball) {
                e.setCancelled(true);
                Utils.broadcastMsg(ChatColor.GOLD + h.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + s.getName() + ChatColor.GREEN + " usando " + Items.getAxe().getItemMeta().getDisplayName());

                h.teleport(plugin.getAm().getRandomSpawn());
                RageGames.getPlayer(h).resetPlayer();
                a.getPassenger().remove();
                plugin.getGm().addPoint(RageGames.getPlayer(s), 50);
                plugin.getGm().removePoint(RageGames.getPlayer(h), 25);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().getType() == Material.IRON_AXE) {
            final Snowball sb = p.launchProjectile(Snowball.class);
            sb.setPassenger(p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.IRON_AXE)));
            p.getInventory().setItemInHand(null);
            sb.setShooter(p);
        }
    }
}
