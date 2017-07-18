package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.kits.Kit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class PlayerEvents implements Listener {

    private TOA plugin;

    public PlayerEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Kit k = Kit.parseKit(u.getToaUserData().getKit());

        u.sendToCity();
        if (k != null) k.addEffects(u);
    }

    @EventHandler
    public void onPlayerDie(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            TOAUser u = TOA.getPlayer((Player) e.getEntity());

            if (u.getPlayer().getHealth() - e.getDamage() <= 0) {
                u.death();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Entity en = e.getRightClicked();

        if (en instanceof ArmorStand) {
            ArmorStand ar = (ArmorStand) en;

            switch (ar.getItemInHand().getType()) {
                case DIAMOND_SWORD:
                    u.setKit(Kit.WARRIOR);
                    break;
                case BOW:
                    u.setKit(Kit.ARCHER);
                    break;
                case SHEARS:
                    u.setKit(Kit.PICARO);
                    break;
            }
        }
    }
}
