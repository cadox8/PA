package es.projectalpha.pa.sur.events;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import es.projectalpha.pa.sur.manager.PvPManager;
import net.milkbowl.vault.economy.Economy;
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
import org.bukkit.permissions.PermissionAttachment;


public class PlayerEvents implements Listener{

    private PASurvival plugin;
    private Files files = new Files();
    private PvPManager manager = new PvPManager();
    private Balance balance = new Balance();
    private Economy eco = PASurvival.getInstance().getVault();

    @EventHandler
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

        if(Files.user.getBoolean("Users." + p.getName() + ".pimp") == false){
            u.sendMessage("&aSe te ha cobrado los impuestos, lo que equivale a &6" + eco.getBalance(u.getPlayer()) * 0.01 + "&a$");
            Files.user.set("Users." + p.getName() + ".apos", eco.getBalance(u.getPlayer()) * 0.01);
            balance.cobrarImpuestos(u);
            Files.user.set("Users." + p.getName() + ".pimp", true);
            Files.saveFiles();
        }

        PermissionAttachment attachment = p.addAttachment(plugin);
        plugin.getPerms().put(p, attachment);
        PermissionAttachment pperms = plugin.getPerms().get(p);
        pperms.setPermission("essentials.balance", true);
        pperms.setPermission("essentials.pay", true);
        pperms.setPermission("essentials.balancetop", true);
        pperms.setPermission("jobs.use", true);
        pperms.setPermission("essentials.home", true);
        pperms.setPermission("essentials.sethome", true);
        pperms.setPermission("essentials.tpahere", true);
        pperms.setPermission("essentials.tpaccept", true);
        pperms.setPermission("essentials.tpdeny", true);
        pperms.setPermission("essentials.afk", true);
        pperms.setPermission("essentials.ignore", true);
        pperms.setPermission("essentials.msg", true);
        pperms.setPermission("essentials.recipe", true);
        pperms.setPermission("essentials.spawn", true);
        if(u.isOnRank(PACmd.Grupo.VIP)){
            pperms.setPermission("essentials.tpa", true);
            pperms.setPermission("essentials.sethome.multiple.vip", true);
            pperms.setPermission("essentials.chat.color", true);
            pperms.setPermission("essentials.chat.format", true);
            pperms.setPermission("essentials.chat.magic", true);
            pperms.setPermission("essentials.nick", true);
            pperms.setPermission("essentials.msg.color", true);
            pperms.setPermission("essentials.msg.format", true);
            pperms.setPermission("essentials.msg.magic", true);
            pperms.setPermission("essentials.back", true);
            pperms.setPermission("essentials.back.ondeath", true);
        }
        if(u.isOnRank(PACmd.Grupo.ORIGIN)){
            pperms.setPermission("essentials.sethome.multiple.origin", true);
            pperms.setPermission("essentials.signs.color", true);
            pperms.setPermission("essentials.signs.format", true);
            pperms.setPermission("essentials.signs.magic", true);
            pperms.setPermission("essentials.me", true);
            pperms.setPermission("essentials.nick.color", true);
            pperms.setPermission("essentials.nick.format", true);
            pperms.setPermission("essentials.nick.magic", true);
            pperms.setPermission("essentials.nick.format", true);
            pperms.setPermission("essentials.keepxp", true);
        }
        if(u.isOnRank(PACmd.Grupo.Builder)){
            pperms.setPermission("essentials.msg.url", true);
        }
        if(u.isOnRank(PACmd.Grupo.Mod)){
            pperms.setPermission("essentials.*", true);
        }
        if(u.isOnRank(PACmd.Grupo.Admin)){
            pperms.setPermission("*", true);
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

                p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + " Ya no estás en pvp, puedes desconectarte."));
                pl.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + " Ya no estás en pvp, puedes desconectarte."));
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
        if(Files.user.contains("Users." + p.getName())){
            Files.saveFiles();
            if(manager.isInPvP(p)){
                p.setHealth(0D);
                Bukkit.broadcastMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.GRAY + " ¡" + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " se ha desconectado en combate!"));
            }
        }
    }

}
