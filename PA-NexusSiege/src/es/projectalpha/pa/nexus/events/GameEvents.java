package es.projectalpha.pa.nexus.events;

import es.projectalpha.pa.nexus.NexusSiege;
import es.projectalpha.pa.nexus.api.NexusPlayer;
import es.projectalpha.pa.nexus.clases.BarbaroClass;
import es.projectalpha.pa.nexus.clases.MagoClass;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameEvents {

    private NexusSiege plugin;

    private BarbaroClass bc;
    private MagoClass mg;



    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        NexusPlayer p = NexusSiege.getPlayer(e.getPlayer());

        if(p.getPlayer().getInventory().getItemInHand().equals(bc.getItem3())){

        }

    }

}
