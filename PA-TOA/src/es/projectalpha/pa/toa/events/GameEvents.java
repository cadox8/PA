package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Sounds;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.drops.Drop;
import es.projectalpha.pa.toa.manager.Experience;
import es.projectalpha.pa.toa.mobs.Mob;
import es.projectalpha.pa.toa.mobs.MobType;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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

            e.getDrops().clear();
            e.setDroppedExp(0);

            Drop.drop(MobType.parseMobType(e.getEntityType()), u.getUserData().getKit()).forEach(d -> {
                BagEvents.addItem(u, d);
                u.sendSound(Sounds.ITEM_PICKUP);
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

        if (e.getItem() != null) {
            Material m = e.getItem().getType();
            if (m == Material.WRITTEN_BOOK || m == Material.BOW || m == Material.SHEARS || m == Material.DIAMOND_SWORD) return;
            Ability.useAbility(u, e.getItem().getType());
            e.setCancelled(true);
        }

        if (!u.isOnRank(PACmd.Grupo.Builder)) {
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getType().equals(Material.OAK_TRAPDOOR) || e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)
                        || e.getClickedBlock().getType().equals(Material.OAK_FENCE_GATE) || e.getClickedBlock().getType().equals(Material.FIRE)
                        || e.getClickedBlock().getType().equals(Material.CAULDRON) || e.getClickedBlock().getRelative(BlockFace.UP).getType().equals(Material.FIRE)
                        || e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST
                        || e.getClickedBlock().getType() == Material.DROPPER || e.getClickedBlock().getType() == Material.DISPENSER
                        || e.getClickedBlock().getType() == Material.WHITE_BED
                        || e.getClickedBlock().getType() == Material.LEGACY_WORKBENCH || e.getClickedBlock().getType() == Material.BREWING_STAND
                        || e.getClickedBlock().getType() == Material.ANVIL || e.getClickedBlock().getType() == Material.DARK_OAK_FENCE_GATE
                        || e.getClickedBlock().getType() == Material.SPRUCE_FENCE_GATE || e.getClickedBlock().getType() == Material.FURNACE
                        || e.getClickedBlock().getType() == Material.FURNACE || e.getClickedBlock().getType() == Material.HOPPER
                        || e.getClickedBlock().getType() == Material.STONE_BUTTON || e.getClickedBlock().getType() == Material.OAK_BUTTON) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
