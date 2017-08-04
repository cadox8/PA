package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.utils.GameState;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.utils.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
            if(d.equals(a)) return;
            e.setDamage(0);

            if(GameState.getState() == GameState.LOBBY) e.setCancelled(true);

            if (d.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
                Utils.broadcastMsg(ChatColor.GOLD + d.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + a.getName() + ChatColor.GREEN + " usando " + Items.getKnife().getItemMeta().getDisplayName() + ChatColor.GREEN + " (+20 puntos)");
                RageGames.getPlayer(a).resetPlayer();
                a.teleport(plugin.getAm().getRandomSpawn());
                plugin.getGm().addPoint(RageGames.getPlayer(d), 20);
            }
        }

        if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
            Player s = (Player) ((Projectile) e.getDamager()).getShooter();
            Player h = (Player) e.getEntity();
            Projectile a = (Projectile) e.getDamager();
            if(s.equals(h)) return;
            if (a instanceof Arrow) {
                s.getInventory().setItem(9, Items.getArrow());
                Utils.broadcastMsg(ChatColor.GOLD + h.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + s.getName() + ChatColor.GREEN + " usando " + Items.getArrow().getItemMeta().getDisplayName() + ChatColor.GREEN +  " (+30 puntos)");

                h.teleport(plugin.getAm().getRandomSpawn());
                RageGames.getPlayer(h).resetPlayer();
                plugin.getGm().addPoint(RageGames.getPlayer(s), 30);
            }
        }
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event) {
        Player l = event.getPlayer();

        if (l.getInventory().getItemInHand().getType() == Material.IRON_AXE) {
            final Item item = l.getWorld().dropItem(l.getEyeLocation(), Items.getAxe());
            l.getInventory().remove(Items.getAxe());
            item.setVelocity(l.getLocation().getDirection().multiply(2D));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (item.isOnGround()) {
                        item.remove();
                        cancel();
                        return;
                    }

                    item.getWorld().getNearbyEntities(item.getLocation(), 1d, 1d, 1d).stream().filter(e -> e.getType().equals(EntityType.PLAYER)).forEach(e -> {
                        if (e.equals(l)) return;
                        Utils.broadcastMsg(ChatColor.GOLD + l.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + e.getName() + ChatColor.GREEN + " usando " + Items.getAxe().getItemMeta().getDisplayName() + ChatColor.GREEN + " (+50 puntos)");
                        plugin.getGm().addPoint(RageGames.getPlayer(l), 50);
                        plugin.getGm().removePoint(RageGames.getPlayer((Player) e), 25);
                        e.teleport(plugin.getAm().getRandomSpawn());
                        RageGames.getPlayer((Player) e).resetPlayer();
                    });
                }
            }.runTaskTimer(plugin, 0,1);
        }
    }
}
