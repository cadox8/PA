package es.projectalpha.pa.sn.events;

import es.projectalpha.pa.sn.SNMob;
import es.projectalpha.pa.sn.SafariNet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by cadox on 13/12/2016.
 */
public class SpawnMob implements Listener{

    private SafariNet plugin;

    public SpawnMob(SafariNet Main){
        this.plugin = Main;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSpawn(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getHand() != EquipmentSlot.HAND) return;
            if (e.getItem() == null || !e.getItem().hasItemMeta() || e.getItem().getItemMeta() == null || !e.getItem().getItemMeta().hasDisplayName() || e.getItem().getType() != Material.SNOWBALL) return;
            if (!ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).contains("Spawn")) return;
            int id = Integer.parseInt(e.getItem().getItemMeta().getLore().get(0));
            String s = e.getItem().getItemMeta().getLore().get(1);

            SNMob mob = new SNMob(p);
            if (!mob.isOwner(id)) {
               p.sendMessage(SafariNet.getInstance().getPrefix() + ChatColor.RED + "No eres el dueño de este huevo");
                return;
            }
            mob.spawnMob(id, s);
            p.getInventory().getItemInMainHand().setAmount(-1);
        }
    }
}
