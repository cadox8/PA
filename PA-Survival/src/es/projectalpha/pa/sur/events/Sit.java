package es.projectalpha.pa.sur.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.List;

public class Sit implements Listener {

    @EventHandler
    public void onSit(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b;

        if (e.getItem() == null) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.getInventory().getItemInMainHand() != null) return;
                b = e.getClickedBlock();
                if (isStairs(b.getType())) {
                    if (!p.isSneaking() && p.getVehicle() != null) {
                        p.getVehicle().remove();
                        return;
                    }
                    p.setSneaking(false);

                    Location l = b.getLocation().add(0.5, -1.3, 0.3);

                    switch (b.getState().getData().toItemStack().getDurability()) {
                        case 0: //west
                            l.setYaw(90f);
                            l.setZ(l.getZ() + 0.2);
                            break;
                        case 1: //east
                            l.setYaw(-90f);
                            l.setZ(l.getZ() + 0.2);
                            break;
                        case 2: //north
                            l.setYaw(-180f);
                            break;
                        case 3: //south
                            l.setYaw(0);
                            l.setZ(l.getZ() + 0.2);
                            break;
                    }

                    if (b.getState().getData().toItemStack().getDurability() >= 4) return;

                    ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
                    as.teleport(l);
                    as.setVisible(false);
                    as.setGravity(false);
                    as.setMaxHealth(1);
                    as.setHealth(1);
                    as.setCustomName("pa_silla");
                    as.setCustomNameVisible(false);
                    as.setPassenger(p);

                    e.setCancelled(true);
                }
            }
        }
    }

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

    private boolean isStairs(Material m) {
        List<Material> stairs = new ArrayList<>();
        stairs.add(Material.ACACIA_STAIRS);
        stairs.add(Material.BIRCH_WOOD_STAIRS);
        stairs.add(Material.BRICK_STAIRS);
        stairs.add(Material.COBBLESTONE_STAIRS);
        stairs.add(Material.DARK_OAK_STAIRS);
        stairs.add(Material.JUNGLE_WOOD_STAIRS);
        stairs.add(Material.NETHER_BRICK_STAIRS);
        stairs.add(Material.PURPUR_STAIRS);
        stairs.add(Material.QUARTZ_STAIRS);
        stairs.add(Material.RED_SANDSTONE_STAIRS);
        stairs.add(Material.SANDSTONE_STAIRS);
        stairs.add(Material.SPRUCE_WOOD_STAIRS);
        stairs.add(Material.WOOD_STAIRS);
        stairs.add(Material.SMOOTH_STAIRS);
        return stairs.contains(m);
    }
}
