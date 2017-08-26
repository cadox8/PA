package es.projectalpha.pa.sn.events;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import es.projectalpha.pa.sn.SNMob;
import es.projectalpha.pa.sn.SafariNet;
import es.projectalpha.pa.sn.files.Files;
import es.projectalpha.pa.sn.recipes.PokeEgg;
import es.projectalpha.pa.sn.utils.MobUtils;
import es.projectalpha.pa.sn.utils.VaultUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

/**
 * Created by cadox on 12/12/2016.
 */
public class CatchMob implements Listener{

    private Files files = new Files();
    private MobUtils mu = new MobUtils();
    private PokeEgg pe = new PokeEgg();
    private VaultUtils vu = new VaultUtils();
    private SafariNet plugin;

    private boolean canWork = false;

    public CatchMob(SafariNet Main){
        this.plugin = Main;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e){
        Player p;
        if (e.getEntity().getShooter() instanceof Player) {
            p = (Player) e.getEntity().getShooter();

            if (p.getInventory().getItemInMainHand() == null) return;
            if (!p.getInventory().getItemInMainHand().hasItemMeta() || !p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
            if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(pe.getPokeEgg().getItemMeta().getDisplayName())) {
                canWork = true;
            }
        }
    }

    @EventHandler
    public void onEgg(PlayerEggThrowEvent e){
        Player p = e.getPlayer();

        if (p.getInventory().getItemInMainHand() == null) return;
        if (!p.getInventory().getItemInMainHand().hasItemMeta() || !p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(pe.getPokeEgg().getItemMeta().getDisplayName())) {
            e.setHatching(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMobCatch(ProjectileHitEvent e){
        Player p;
        if (e.getEntity().getShooter() instanceof Player) {
            p = (Player) e.getEntity().getShooter();
            if (canWork) {
                if (e.getEntity() instanceof Egg) {
                    if (e.getHitEntity() instanceof Monster || e.getHitEntity() instanceof Animals || e.getHitEntity() instanceof Villager) {
                        if (mu.bannedMobs().contains(e.getEntity().getType())) {
                            p.sendMessage(this.plugin.getPrefix() + ChatColor.RED + "Este mob esta prohibido");
                            p.getInventory().addItem(pe.getPokeEgg());
                            return;
                        }
                        if (!vu.hasEnoughMoney(p, e.getHitEntity().getType().toString())) {
                            p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.RED + "No tienes dinero suficiente para hacer esto");
                            p.getInventory().addItem(pe.getPokeEgg());
                            return;
                        }
                        if (e.getEntity() instanceof Monster || e.getEntity() instanceof Animals){
                            LivingEntity le = (LivingEntity)e.getEntity();
                            if (le.getHealth() <= 0 || le.isDead()){
                                p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.RED + "No puedes capturar a un mob muerto");
                                p.getInventory().addItem(pe.getPokeEgg());
                                return;
                            }
                        }

                        boolean canCatch = true;
                        ApplicableRegionSet region = plugin.getWg().getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
                        for (ProtectedRegion r : region.getRegions()) if (!r.getOwners().contains(p.getName())) canCatch = false;

                        if (!canCatch) {
                            p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.RED + "No puedes capturar a un mob en parcelas ajenas");
                            return;
                        }

                        //To Fix
                        if (e.getEntity() instanceof Mule || e.getEntity() instanceof Donkey || e.getEntity() instanceof Llama || e.getEntity() instanceof Villager){
                            p.sendMessage(ChatColor.RED + "Estos mobs están desactivados debido a un error de Minecraft.");
                            return;
                        }

                        SNMob mob = new SNMob(e.getHitEntity(), p);
                        mob.writeConfig();
                        mob.givePlayerEgg();

                        e.getHitEntity().remove();
                        p.sendMessage(this.plugin.getPrefix() + ChatColor.GREEN + "Has atrapado un mob");
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

                        vu.removeMoney(p, e.getHitEntity().getType().toString());

                        canWork = false;

                        if (e.getHitEntity().getType() == EntityType.VILLAGER) {
                            p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.RED + "Los aldeanos tienen un pequeño error ahora mismo con los tradeos. Lo sentimos");
                        }
                    }
                }
            }
        }
    }
}
