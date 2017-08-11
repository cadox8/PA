package es.projectalpha.pa.toa.events;

import es.projectalpha.pa.core.api.PAData;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

        u.sendToCity();
        if (k != null) {
            k.addEffects(u);
            return;
        }
        u.getPlayer().getInventory().setItem(5, ItemUtil.createBook(PAData.TOA.getPrefix(), "&7libro de información", ItemUtil.pages));
    }

    @EventHandler
    public void onPlayerDie(EntityDamageByEntityEvent e) {
        DecimalFormat df = new DecimalFormat("0.00");

        //You hit me, bitch
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Monster) {
            TOAUser u = TOA.getPlayer((Player) e.getEntity());
            String name = e.getEntity().getCustomName().split(" ")[1];
            int level = Utils.isInt(name) ? Integer.parseInt(name) : 0;
            double damage = 10 + (level * 0.8);

            if (plugin.getHealth().getHealth(u) - damage <= 0) {
                u.death();
                return;
            }
            plugin.getHealth().damage(u, Double.valueOf(df.format(damage)));
        }


        //Normal
        if (e.getEntity() instanceof Monster && e.getDamager() instanceof Player) {
            TOAUser u = TOA.getPlayer((Player) e.getDamager());
            ItemStack i = u.getPlayer().getItemInHand() != null ? u.getPlayer().getItemInHand() : new ItemStack(Material.AIR);
            int damage = i.getItemMeta().hasLore() ? Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getLore().get(1))) : 0;

            e.setDamage(Double.valueOf(df.format(damage + (damage * u.getUserData().getLvl() * 0.2))));

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
                    u.setRace(Race.ARCHER);
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

        if (plugin.getGm().getInTower().contains(u)) return;

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
}
