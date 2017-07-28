package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.ItemUtil;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerEvents implements Listener {

    private TOA plugin;

    public PlayerEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Race k = Race.parseRace(u.getUserData().getKit());

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

        if (e.getEntity() instanceof Monster && e.getDamager() instanceof Snowball) {
            Monster m = (Monster) e.getEntity();
            TOAUser u = TOA.getPlayer((Player)((Snowball) e.getDamager()).getShooter());

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Entity en = e.getRightClicked();

        System.out.println("Entidad");

        if (en instanceof ArmorStand) {
            ArmorStand ar = (ArmorStand) en;

            System.out.println(ar.getItemInHand().getType().toString());

            switch (ar.getItemInHand().getType()) {
                case DIAMOND_SWORD:
                    u.setRace(Race.WARRIOR);
                    break;
                case BOW:
                    u.sendMessage(PAData.TOA.getPrefix() + "&cClase no disponible por el momento");
                    //u.setRace(Race.ARCHER);
                    break;
                case SHEARS:
                    u.setRace(Race.PICARO);
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
