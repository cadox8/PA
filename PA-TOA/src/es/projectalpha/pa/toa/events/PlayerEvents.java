package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.ItemUtil;
import es.projectalpha.pa.core.utils.ItemUtils;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.kits.Kit;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
import org.bukkit.event.player.PlayerMoveEvent;
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
        if (k != null) {
            k.addEffects(u);
            return;
        }

        u.getPlayer().getInventory().setItem(5, ItemUtil.createBook(PAData.TOA.getPrefix(), "&7libro de información", ItemUtil.pages));
    }

    @EventHandler
    public void onPlayerDie(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            TOAUser u = TOA.getPlayer((Player) e.getEntity());

            if (u.getPlayer().getHealth() - e.getDamage() <= 0) u.death();
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
                    u.sendMessage(PAData.TOA.getPrefix() + "&cClase no disponible por el momento");
                    //u.setKit(Kit.ARCHER);
                    break;
                case SHEARS:
                    u.setKit(Kit.PICARO);
                    break;
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Location l = u.getLoc();

        if (!plugin.getGm().getInTower().contains(u)) return;

        Block b1 = l.getWorld().getBlockAt(Utils.cuboidToLocation(plugin.getConfig().getString("JoinTower"), 0));
        Block b2 = l.getWorld().getBlockAt(Utils.cuboidToLocation(plugin.getConfig().getString("JoinTower"), 1));
        CuboidZone cz = new CuboidZone(b1, b2);

        if (cz.toLocations().contains(l)) u.sendToTower();
    }
}
