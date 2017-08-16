package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.ItemUtil;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.abilities.Ability;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.mobs.boss.BossAttacks;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class PlayerEvents implements Listener {

    private TOA plugin;

    public PlayerEvents(TOA instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Race k = Race.parseRace(u.getUserData().getKit());

        if (k != null) {
            k.addEffects(u);
            u.teleport(k.spawn());
            return;
        }
        u.getPlayer().getInventory().clear();
        u.heal();
        u.teleport(plugin.getAm().getSpawn());
        u.getPlayer().getInventory().setItem(4, ItemUtil.createBook(PAData.TOA.getPrefix(), "&7libro de información", ItemUtil.pages));
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (!plugin.getGm().getInTower().contains(TOA.getPlayer((Player)e.getEntity()))) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        DecimalFormat df = new DecimalFormat("0.00");

        //You hit me, bitch
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Monster) {
            TOAUser u = TOA.getPlayer((Player) e.getEntity());
            String name = e.getDamager().getCustomName().split(" ")[1];
            int level = Utils.isInt(name) ? Integer.parseInt(name) : 0;
            double damage = 10 + (level * 0.8);

            if (plugin.getHealth().getHealth(u) - damage <= 0) {
                u.death();
                return;
            }
            plugin.getHealth().damage(u, Double.valueOf(df.format(damage)));
            e.setDamage(0);
        }


        //Normal
        if (e.getEntity() instanceof Monster && e.getDamager() instanceof Player) {
            TOAUser u = TOA.getPlayer((Player) e.getDamager());
            ItemStack i = u.getPlayer().getItemInHand() != null ? u.getPlayer().getItemInHand() : new ItemStack(Material.AIR);
            int damage;

            if (i.hasItemMeta()) {
                damage = i.getItemMeta().hasLore() ? Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getLore().get(1))) : 20;
            } else {
                damage = 20;
            }

            e.setDamage(Double.valueOf(df.format(damage + (damage * u.getUserData().getLvl() * 0.2)).replace(",", ".")));

            if (e.getEntity() instanceof Giant) { //Boss Attack
                BossAttacks.giantAttacks((Giant) e.getEntity(), u.getPlayer());
                return;
            }
        }


        //Archer
        if (e.getEntity() instanceof Monster && e.getDamager() instanceof Arrow) {
            Monster m = (Monster) e.getEntity();
            TOAUser u = TOA.getPlayer((Player)((Arrow) e.getDamager()).getShooter());
            int multi = 0;

            if (Ability.getFireArrow().contains(u)) {
                m.setFireTicks(40);
                multi = 1;
            }

            double damage = (u.getUserData().getLvl() * 0.2) + 20;

            damage = multi != 0 ? damage + (damage * 0.1) : damage;

            e.setDamage(Double.valueOf(df.format(damage)));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());

        e.setCancelled(true);

        if (e.getRightClicked() instanceof Villager) {
            Villager v = (Villager) e.getRightClicked();
            if (v.getCustomName().equalsIgnoreCase("") || v.getCustomName() == null) return;

            switch (ChatColor.stripColor(v.getCustomName())) {
                case "Comprador Variado":
                    u.sendMessage("Oh, un menú");
                    break;
                case "Comprador Armas":
                    u.sendMessage("Oh, un menú");
                    break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());
        Location l = new Location(u.getWorld(), (int)u.getLoc().getX(), (int)u.getLoc().getY(), (int)u.getLoc().getZ());

        if (!l.getWorld().equals(Utils.cuboidToLocation(plugin.getConfig().getString("JoinTower"), 0).getWorld())) return;

        Block b1 = l.getWorld().getBlockAt(Utils.cuboidToLocation(plugin.getConfig().getString("JoinTower"), 0));
        Block b2 = l.getWorld().getBlockAt(Utils.cuboidToLocation(plugin.getConfig().getString("JoinTower"), 1));
        CuboidZone cz = new CuboidZone(b1, b2);

        if (cz.toLocations().contains(l)) u.sendToTower();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        TOAUser u = TOA.getPlayer(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Builder) || plugin.getGm().getInTower().contains(u)) e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        TOAUser u = TOA.getPlayer(e.getPlayer());

        if (!u.isOnRank(PACmd.Grupo.Builder) || plugin.getGm().getInTower().contains(u)) e.setCancelled(true);
    }
}
