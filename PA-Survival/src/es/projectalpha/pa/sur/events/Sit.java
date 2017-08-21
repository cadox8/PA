package es.projectalpha.pa.sur.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Sit implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.isInsideVehicle() && p.getVehicle() instanceof ArmorStand) {

            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDismount(EntityDismountEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDismounted() instanceof ArmorStand) {
                ArmorStand w = (ArmorStand) e.getDismounted();

                e.getEntity().eject();
                w.remove();
                e.getEntity().teleport(e.getEntity().getLocation().add(0, 4, 0));
            }
        }
    }
}
