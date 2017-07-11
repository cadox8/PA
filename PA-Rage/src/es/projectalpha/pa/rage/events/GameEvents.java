package es.projectalpha.pa.rage.events;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.manager.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameEvents implements Listener {

    private RageGames plugin;
    private ArenaManager am;

    public GameEvents(RageGames instance) {
        this.plugin = instance;
    }

    public ItemStack knife(){
        ItemStack knife = new ItemStack(Material.IRON_SWORD);
        ItemMeta mknife = knife.getItemMeta();
        mknife.setDisplayName(ChatColor.GOLD + "RageKnife");
        mknife.setUnbreakable(true);
        return knife;
    }
    ItemStack bow = new ItemMaker(Material.BOW).setDisplayName("&6RageBow").setUnbreakable().build();
    ItemStack arrow = new ItemMaker(Material.ARROW).setDisplayName("&RageArrow").build();
    ItemStack axe = new ItemMaker(Material.IRON_AXE).setDisplayName("&RageAxe").setUnbreakable().build();



    @EventHandler
    public void OnHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){

            Player d = (Player) e.getDamager();
            Player a = (Player) e.getEntity();

            if(d.getInventory().getItemInMainHand().equals(knife())){
               e.setCancelled(true);
               Bukkit.broadcastMessage(ChatColor.GOLD + d.getName() + ChatColor.GREEN + " ha matado a " + ChatColor.GOLD + a.getName() + ChatColor.GREEN + " usando " + knife().getItemMeta().getDisplayName());
               a.setHealth(20d);
               remove(a);
               a.teleport(am.getRandomSpawn());
            }

        }
    }

    public void OnProjectile(ProjectileHitEvent e){
        if(e.getEntity().equals(arrow)){
            Player s = (Player) e.getEntity().getShooter();
            Player h = (Player) e.getHitEntity();

        }
    }

    private void remove(Player p){
        p.getInventory().clear();
        p.getInventory().setItem(0, knife());
        p.getInventory().setItem(1, axe);
        p.getInventory().setItem(2, bow);
        p.getInventory().setItem(9, arrow);
    }
}
