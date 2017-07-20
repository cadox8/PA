package es.projectalpha.pa.antium.events;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;

public class PlayerEvents implements Listener {

    private PAAntium plugin;

    public PlayerEvents(PAAntium instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (plugin.getMysql().isRegistered(u)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Por favor, escribe &c/login <contraseña> &3para acceder al servidor");
            return;
        }
        u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Por favor, escribe &c/register <contraseña> <contraseña> &3para acceder al servidor");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) plugin.getPassManager().removeLogged(u);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCombust(EntityCombustEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockDamage(BlockDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityTarget(EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }
}
