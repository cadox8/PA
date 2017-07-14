package es.projectalpha.pa.lobby.events;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvents implements Listener {

    private PALobby plugin;

    public InventoryEvents(PALobby instance){
        this.plugin = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        PAUser u = PAServer.getUser((Player) e.getWhoClicked());

        switch (e.getClickedInventory().getName()){
            case "Servidores":
                e.setCancelled(true);
                switch (e.getSlot()){
                    case 0:
                        u.sendToServer("survival");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
