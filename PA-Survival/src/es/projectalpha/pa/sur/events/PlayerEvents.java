package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.casino.Casino;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import es.projectalpha.pa.sur.manager.PvPManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Random;


public class PlayerEvents implements Listener{

    private PASurvival plugin;
    private Files files = new Files();
    private PvPManager manager = new PvPManager();
    private Balance balance = new Balance();
    private Economy eco = PASurvival.getInstance().getVault();

    public PlayerEvents(PASurvival instance) {
        plugin = instance;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        SurvivalUser u = PASurvival.getPlayer(p);

        if (!Files.user.getStringList("Users").contains(p.getName())) {
            Files.user.set("Users." + p.getName() + ".pvp", false);
            Files.user.set("Users." + p.getName() + ".money", "0");
            Files.user.createSection("Users." + p.getName() + ".homes");
            Files.user.set("Users." + p.getName() + ".imprec","0");
            Files.user.set("Users." + p.getName() + ".pimp",false);
            Files.user.set("Users." + p.getName() + ".apos",0);
            Files.saveFiles();
        }

        if(!PASurvival.getImp().contains(p.getName())){
            DecimalFormat df = new DecimalFormat("#.00");
            double imp = Double.valueOf(df.format(eco.getBalance(u.getPlayer()) * 0.01));
            u.sendMessage("&aSe te ha cobrado los impuestos, lo que equivale a &6" + imp + "&a$");
            Files.user.set("Users." + p.getName() + ".imprec", imp);
            balance.cobrarImpuestos(u, imp);
            PASurvival.getImp().add(p.getName());
            Files.saveFiles();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Entity en1 = e.getEntity();
        Entity en = e.getEntity().getKiller();

        if(en instanceof Player && en1 instanceof Player){
            Player pl = (Player) en1;
            Player p = (Player) en;

            if (manager.isInPvP(p)) {
                manager.removeCooldown(p);
                manager.removeCooldown(pl);

                p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + " Ya no estás en pvp, puedes desconectarte."));
                pl.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + " Ya no estás en pvp, puedes desconectarte."));
            }
            pl.sendMessage(ChatColor.GREEN + "");
        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (e.getItemInHand().getType() == Material.DROPPER && e.getItemInHand().getEnchantments() != null && e.getItemInHand().getEnchantments().containsKey(Enchantment.ARROW_DAMAGE)) {
            Casino c = new Casino(e.getBlockPlaced().getLocation());
            c.effect();
        }

        for(Entity en : p.getNearbyEntities (4D, 4D, 4D)){
            if (en instanceof Player){
                if(en == p) continue;
                if(b.getType() == Material.FIRE){

                    if(Files.user.getBoolean("Users." + en.getName() + ".pvp") == false){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " No puedes poner ese bloque cerca de un jugador con el pvp desactivado."));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerBucketEmptyEvent e){
        Player p = e.getPlayer();
        for(Entity en : p.getNearbyEntities (4D, 4D, 4D)){
            if (en instanceof Player){
                if(en == p) continue;
                if(e.getBucket() == Material.LAVA_BUCKET){

                    if(Files.user.getBoolean("Users." + en.getName() + ".pvp") == false){
                        p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " No puedes poner ese bloque cerca de un jugador con el pvp desactivado."));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(manager.isInPvP(p)){
            e.setCancelled(true);
            p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " ¡No puedes ejecutar comandos en pvp!"));
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        SurvivalUser u = PASurvival.getPlayer(p);

        if(Files.user.contains("Users." + p.getName())){
            Files.saveFiles();
            if(manager.isInPvP(p)){
                p.setHealth(0D);
                Bukkit.broadcastMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.GRAY + " ¡" + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " se ha desconectado en combate!"));
            }
        }
        u.save();
        PAServer.users.remove(u);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        SurvivalUser u = PASurvival.getPlayer(e.getPlayer());

        if (plugin.getMineTask().getBlocks().containsKey(u)) {
            plugin.getMineTask().getBlocks().put(u, plugin.getMineTask().getBlocks().get(u) + 1);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        SurvivalUser u = PASurvival.getPlayer(e.getPlayer());

        if (e.getFrom().getName().toLowerCase().equalsIgnoreCase("eventos")) {
            if (plugin.getMineTask().getBlocks().containsKey(u)) {
                plugin.getMineTask().getBlocks().remove(u, plugin.getMineTask().getBlocks().get(u));
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConsume(PlayerInteractEvent e) {
        SurvivalUser u = PASurvival.getPlayer(e.getPlayer());

        if (e.getItem() == null) return;

        if (e.getItem().getType() == Material.BREAD && e.getItem().hasItemMeta()) {
            if (e.getItem().getEnchantments().keySet().contains(Enchantment.ARROW_DAMAGE)) {
                String name = ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName());
                ItemStack i = u.getPlayer().getInventory().getItemInMainHand();

                if (u.getPlayer().getFoodLevel() >= 20) return;

                if (name.equalsIgnoreCase("Bocadillo de Jamón")) {
                    if (u.getPlayer().getFoodLevel() + 5 > 20) {
                        u.getPlayer().setFoodLevel(20);
                    } else {
                        u.getPlayer().setFoodLevel(u.getPlayer().getFoodLevel() + 5);
                    }
                    u.getPlayer().setSaturation(3F);
                    i.setAmount(i.getAmount() - 1);
                    u.getPlayer().getInventory().setItemInMainHand(i);
                }

                if (name.equalsIgnoreCase("Hamburguesa Artesana")) {
                    if (u.getPlayer().getFoodLevel() + 6 > 20) {
                        u.getPlayer().setFoodLevel(20);
                    } else {
                        u.getPlayer().setFoodLevel(u.getPlayer().getFoodLevel() + 6);
                    }
                    u.getPlayer().setSaturation(4F);
                    i.setAmount(i.getAmount() - 1);
                    u.getPlayer().getInventory().setItemInMainHand(i);
                }
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (e.getEntity().getWorld().getName().equalsIgnoreCase("world_nether") || e.getEntity().getWorld().getName().equalsIgnoreCase("Nether")) {
                if (e.getEntity().getType() == EntityType.SKELETON) {
                    e.setCancelled(true);
                    if (new Random().nextInt(11) > 2) e.getEntity().getWorld().spawnEntity(e.getLocation(), EntityType.WITHER_SKELETON);
                }
            }
        }
    }
}
