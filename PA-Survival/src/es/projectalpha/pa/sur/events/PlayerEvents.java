package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.PvPManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static es.projectalpha.pa.sur.files.Files.saveFiles;

public class PlayerEvents implements Listener{

    private Files files = new Files();
    private PvPManager manager = new PvPManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        SurvivalUser u = PASurvival.getPlayer(p);

        if (!files.getUser().contains(p.getName())) {
            files.getUser().set("Users." + p.getName() + ".pvp", false);
            files.getUser().set("Users." + p.getName() + ".money", "0");
            files.getUser().createSection("Users." + p.getName() + "homes");
            files.getUser().set("Users." + p.getName() + ".imprec","0");
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Entity en1 = e.getEntity();
        Entity en = e.getEntity().getKiller();

        if (en instanceof Player) {
            Player pl = (Player) en1;
            Player p = (Player) en;

            if (manager.isInPvP(p)) {
                manager.removeCooldown(p);
                manager.removeCooldown(pl);

                p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + " Ya no estás en pvp, puedes desconectarte.");
                pl.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + " Ya no estás en pvp, puedes desconectarte.");
            }
        }
    }

    @EventHandler
    public void BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        for(Entity en : p.getNearbyEntities (4D, 4D, 4D)){
            if (en instanceof Player){
                if(en == p) continue;
                if(b.getType() == Material.FIRE){

                    if(files.getUser().getBoolean("Users." + en.getName() + ".pvp") == false){
                        p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " No puedes poner ese bloque cerca de un jugador con el pvp desactivado.");
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

                    if(files.getUser().getBoolean("Users." + en.getName() + ".pvp") == false){
                        p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " No puedes poner ese bloque cerca de un jugador con el pvp desactivado.");
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
            p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_RED + " ¡No puedes ejecutar comandos en pvp!");
        }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        if(files.getUser().contains("Users." + p.getName())){
            saveFiles();
            if(manager.isInPvP(p)){
                p.setHealth(0D);
                Bukkit.broadcastMessage(PAData.SURVIVAL.getPrefix() + ChatColor.GRAY + " ¡" + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " se ha desconectado en combate!");
            }
        }
    }

}
