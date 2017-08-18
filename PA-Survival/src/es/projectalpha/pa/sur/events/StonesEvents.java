package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.utils.Stones;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class StonesEvents implements Listener {

    private Files files = new Files();
    private Stones stones = new Stones();
    private double l1,l2,l3;
    private double fl1,fl2,fl3;
    private PASurvival plugin;
    private int i = files.getStone().getInt("piedras");

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        Block b1;
        Block b2;
        CuboidZone cz;
        Location l = b.getLocation();

        switch(b.getType()){

            case COAL_ORE:
                if(!p.getInventory().getItemInMainHand().equals(stones.stone1)) return;
                l1 = 5; l2 = 5; l3 = 5;
                fl1 = -5; fl2 = -5; fl3 = -5;
                b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                cz = new CuboidZone(b1,b2);
                if(files.getStone().getInt("piedras") != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 0; s <= i; s++){
                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(st ->{
                                if(bl.getLocation() == st.getLocation()) return;
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                e.setCancelled(true);
                            });
                        }
                    });
                }
                i++;
                files.getStone().set("piedras" + i + ".b1", b1);
                files.getStone().set("piedras" + i + ".b2", b2);
                files.getStone().set("piedras" + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño 10x10x10.");
                break;

            case IRON_ORE:
                if(!p.getInventory().getItemInMainHand().equals(stones.stone2)) return;
                l1 = 10; l2 = 10; l3 = 10;
                fl1 = -10; fl2 = -10; fl3 = -10;
                b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                cz = new CuboidZone(b1,b2);
                if(files.getStone().getInt("piedras") != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 0; s <= i; s++){
                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(st ->{
                                if(bl.getLocation() == st.getLocation()) return;
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                e.setCancelled(true);
                            });
                        }
                    });
                }
                i++;
                files.getStone().set("piedras" + i + ".b1", b1);
                files.getStone().set("piedras" + i + ".b2", b2);
                files.getStone().set("piedras" + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño 20x20x20.");
                break;

            case GOLD_ORE:
                if(!p.getInventory().getItemInMainHand().equals(stones.stone3)) return;
                l1 = 15; l2 = 15; l3 = 15;
                fl1 = -15; fl2 = -15; fl3 = -15;
                b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                cz = new CuboidZone(b1,b2);
                if(files.getStone().getInt("piedras") != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 0; s <= i; s++){
                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(st ->{
                                if(bl.getLocation() == st.getLocation()) return;
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                e.setCancelled(true);
                                return;
                            });
                        }
                    });
                }
                i++;
                files.getStone().set("piedras" + i + ".b1", b1);
                files.getStone().set("piedras" + i + ".b2", b2);
                files.getStone().set("piedras" + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño 30x30x30.");
                break;

            case LAPIS_ORE:
                if(!p.getInventory().getItemInMainHand().equals(stones.stone4)) return;
                l1 = 20; l2 = 20; l3 = 20;
                fl1 = -20; fl2 = -20; fl3 = -20;
                b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                cz = new CuboidZone(b1,b2);
                if(files.getStone().getInt("piedras") != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 0; s <= i; s++){
                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(st ->{
                                if(bl.getLocation() == st.getLocation()) return;
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                e.setCancelled(true);
                            });
                        }
                    });
                }
                i++;
                files.getStone().set("piedras" + i + ".b1", b1);
                files.getStone().set("piedras" + i + ".b2", b2);
                files.getStone().set("piedras" + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño 40x40x40.");
                break;

            case EMERALD_ORE:
                if(!p.getInventory().getItemInMainHand().equals(stones.stone5)) return;
                l1 = 25; l2 = 25; l3 = 25;
                fl1 = -25; fl2 = -25; fl3 = -25;
                b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                cz = new CuboidZone(b1,b2);
                if(files.getStone().getInt("piedras") != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 0; s <= i; s++){
                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(st ->{
                                if(bl.getLocation() == st.getLocation()) return;
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                e.setCancelled(true);
                            });
                        }
                    });
                }
                i++;
                files.getStone().set("piedras" + i + ".b1", b1);
                files.getStone().set("piedras" + i + ".b2", b2);
                files.getStone().set("piedras" + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño 50x50x50.");
                break;
            case DIAMOND_ORE:
                if(!p.getInventory().getItemInMainHand().equals(stones.stone6)) return;
                l1 = 35; l2 = 35; l3 = 35;
                fl1 = -35; fl2 = -35; fl3 = -35;
                b1 = l.getWorld().getBlockAt(l.add(l1, l2, l3));
                b2 = l.getWorld().getBlockAt(l.add(fl1, fl2, fl3));
                cz = new CuboidZone(b1,b2);
                if(files.getStone().getInt("piedras") != 0){
                    cz.toArray().forEach(bl->{
                        for(int s = 0; s <= i; s++){
                            Block fb1,fb2;
                            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
                            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
                            CuboidZone fcz = new CuboidZone(fb1, fb2);
                            fcz.toArray().forEach(st ->{
                                if(bl.getLocation() == st.getLocation()) return;
                                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No puedes colocar este bloque de protección aquí ya que hay otra región cerca.");
                                e.setCancelled(true);
                            });
                        }
                    });
                }
                i++;
                files.getStone().set("piedras" + i + ".b1", b1);
                files.getStone().set("piedras" + i + ".b2", b2);
                files.getStone().set("piedras" + i + ".owner", p.getName());
                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GREEN + "Zona protegida, tamaño 70x70x70.");
                break;

        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        for(int s = 0; s <= i; s++){
            Block fb1,fb2;
            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
            CuboidZone fcz = new CuboidZone(fb1, fb2);
            Boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(p.getName());
            files.getStone().getList("piedras." + s + ".perm").forEach(j ->{
                fcz.toArray().forEach(st ->{
                    if(p.getLocation() == st.getLocation() && !owner || !j.toString().equals(p.getName())){
                        p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No tienes permisos para construir aquí.");
                        e.setCancelled(true);
                    }
                });
            });
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Location l = p.getLocation();
        for(int s = 0; s <= i; s++){
            Block fb1,fb2;
            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
            CuboidZone fcz = new CuboidZone(fb1, fb2);
            Boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(p.getName());
            files.getStone().getList("piedras." + s + ".perm").forEach(j ->{
                fcz.toArray().forEach(st ->{
                    if(p.getLocation() == st.getLocation() && !owner || !j.toString().equals(p.getName())){
                        p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No tienes permisos para construir aquí.");
                        e.setCancelled(true);
                    }
                });
            });
        }

    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        Location l = p.getLocation();
        for(int s = 0; s <= i; s++){
            Block fb1,fb2;
            fb1 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b1"))));
            fb2 = l.getWorld().getBlockAt(l.add(Utils.stringToLocation(files.getStone().getString("piedras." + s + ".b2"))));
            CuboidZone fcz = new CuboidZone(fb1, fb2);
            Boolean owner = files.getStone().getString("piedras." + s + ".owner").equals(p.getName());
            files.getStone().getList("piedras." + s + ".perm").forEach(j ->{
                fcz.toArray().forEach(st ->{
                    if(p.getLocation() == st.getLocation() && !owner || !j.toString().equals(p.getName())){
                        p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + "No tienes permisos para atacar a entidades aquí.");
                        e.setCancelled(true);
                    }
                });
            });
        }

    }

}
