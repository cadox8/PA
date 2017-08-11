package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.sur.PASurvival;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class IronElevators implements Listener {

    int minElevation = 2;
    int maxElevation = 200;
    private Material elevatorMaterial = Material.IRON_BLOCK;

    @EventHandler(priority = EventPriority.HIGH)
    public void downElevator(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (!p.isSneaking()
                && b.getType() == elevatorMaterial) {
            b = b.getRelative(BlockFace.DOWN, minElevation);
            int i = maxElevation; //16
            while (i > 0 && !(
                    b.getType() == elevatorMaterial
                            && b.getRelative(BlockFace.UP).getType().isTransparent()
                            && b.getRelative(BlockFace.UP, 2).getType().isTransparent()
            )
                    ) {
                i--;
                b = b.getRelative(BlockFace.DOWN);
            }
            if (i > 0) {
                Location l = p.getLocation();
                l.setY(l.getY() - maxElevation - 3 + i);
                p.teleport(l);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 1, 1);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void upElevator(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block b = e.getTo().getBlock().getRelative(BlockFace.DOWN);
        if (p.hasPermission("ironelevators.use") && e.getFrom().getY() < e.getTo().getY()
                && b.getType() == elevatorMaterial) {
            b = b.getRelative(BlockFace.UP, minElevation);
            int i = maxElevation;
            while (i > 0 && !(
                    b.getType() == elevatorMaterial
                            && b.getRelative(BlockFace.UP).getType().isTransparent()
                            && b.getRelative(BlockFace.UP, 2).getType().isTransparent()
            )
                    ) {
                i--;
                b = b.getRelative(BlockFace.UP);
            }
            if (i > 0) {
                Location l = p.getLocation();
                l.setY(l.getY() + maxElevation + 3 - i);
                p.teleport(l);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 1, 1);
            }
        }
    }


}
