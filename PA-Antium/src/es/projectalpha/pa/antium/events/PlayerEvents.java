package es.projectalpha.pa.antium.events;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Utils;
import org.bukkit.entity.Player;
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());
        Player p = e.getPlayer();

        u.getPlayer().getInventory().clear();
        p.getPlayer().teleport(Utils.stringToLocation("login%4%32%-8%0%0"));
        if (PACore.getInstance().getMysql().isRegistered(u)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Por favor, escribe &c/login <contraseña> &3para acceder al servidor");
            return;
        }
        u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Por favor, escribe &c/register <contraseña> <contraseña> &3para acceder al servidor");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLeave(PlayerQuitEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) plugin.getPassManager().removeLogged(u);
        u.save();
        PAServer.users.remove(u);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) u.teleport(e.getFrom());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        PAUser u = PAAntium.getUser(e.getPlayer());

        if (!plugin.getPassManager().getLogged().contains(u)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCombust(EntityCombustEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDamage(BlockDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityTarget(EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }
}
