package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.utils.Stones;
import org.bukkit.ChatColor;
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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;


public class StonesEvents implements Listener {

    private Files files = new Files();
    private Stones stones = new Stones();
    private PASurvival plugin;
    private int i = Files.stone.getInt("tstones");

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Block b1;
        Block b2;
        CuboidZone cz;

        System.out.println(1);
        if (e.getItem() == null) {
            System.out.println(2);
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.getInventory().getItemInMainHand() != null) return;
                System.out.println(3);
                b = e.getClickedBlock();
                if (isStairs(b.getType())) {
                    if (!p.isSneaking() && p.getVehicle() != null) {
                        p.getVehicle().remove();
                        return;
                    }
                    p.setSneaking(false);
                    System.out.println(4);

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

        Location l = b.getLocation();

        for (Stones.StoneType st : Stones.StoneType.values()) {
            if (st.getItemStack().getType() == e.getItem().getType()) {
                if(!p.getInventory().getItemInMainHand().equals(st.getItemStack())) return;
                int area = st.getArea();

                if(st == Stones.StoneType.STAFF && !PASurvival.getPlayer(p).isOnRank(PACmd.Grupo.Builder)) {
                    p.sendMessage(ChatColor.RED + "No puedes usar esta piedra de protección, ya que es sólo para el staff.");
                    return;
                }

                b1 = l.getWorld().getBlockAt(l.add(area, area, area));
                b2 = l.getWorld().getBlockAt(l.add(-area, -area, -area));
                cz = new CuboidZone(b1, b2);
                if(i != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 1; s <= i; s++){

                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(g ->{
                                if(g.getLocation() == bl.getLocation()){
                                    p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                    e.setCancelled(true);
                                    return;
                                }
                            });
                        }
                    });
                }
                i++;

                Files.stone.set("tstones", i);
                Files.stone.set("piedras." + i + ".b1", Utils.locationToString(b1.getLocation()));
                Files.stone.set("piedras." + i + ".b2", Utils.locationToString(b2.getLocation()));
                Files.stone.set("piedras." + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño " + area + "x" + area + "x" + area);
                Files.saveFiles();
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        if (Files.stone.getInt("piedras") != 0) {
            for (int s = 0; s <= i; s++) {
                Block fb1, fb2;
                fb1 = l.getWorld().getBlockAt(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b1")));
                fb2 = l.getWorld().getBlockAt(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b2")));
                CuboidZone fcz = new CuboidZone(fb1, fb2);
                Boolean owner = Files.stone.getString("piedras." + s + ".owner").equals(p.getName());
                Files.stone.getList("piedras." + s + ".perm").forEach(j -> {
                    fcz.toArray().forEach(st -> {
                        if (p.getLocation() == st.getLocation() && !owner || !j.toString().equals(p.getName())) {
                            p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No tienes permisos para construir aquí.");
                            e.setCancelled(true);
                        }
                    });
                });
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        if (Files.stone.getInt("piedras") != 0) {
            for (int s = 0; s <= i; s++) {
                Block fb1, fb2;
                fb1 = l.getWorld().getBlockAt(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b1")));
                fb2 = l.getWorld().getBlockAt(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b2")));
                CuboidZone fcz = new CuboidZone(fb1, fb2);
                Boolean owner = Files.stone.getString("piedras." + s + ".owner").equals(p.getName());
                Files.stone.getList("piedras." + s + ".perm").forEach(j -> {
                    fcz.toArray().forEach(st -> {
                        if (p.getLocation() == st.getLocation() && !owner || !j.toString().equals(p.getName())) {
                            p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No tienes permisos para construir aquí.");
                            e.setCancelled(true);
                        }
                    });
                });
            }

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAttack(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        Location l = p.getLocation();

        if (i == 0) return;
        if(Files.stone.getInt("piedras") == 0) return;
        for(int s = 0; s <= i; s++){
            Block fb1,fb2;
            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b1"))));
            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(Files.stone.getString("piedras." + s + ".b2"))));
            CuboidZone fcz = new CuboidZone(fb1, fb2);
            Boolean owner = Files.stone.getString("piedras." + s + ".owner").equals(p.getName());
            Files.stone.getList("piedras." + s + ".perm").forEach(j ->{
                fcz.toArray().forEach(st ->{
                    if(p.getLocation() == st.getLocation() && !owner || !j.toString().equals(p.getName())){
                        p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No tienes permisos para atacar a entidades aquí.");
                        e.setCancelled(true);
                    }
                });
            });
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
