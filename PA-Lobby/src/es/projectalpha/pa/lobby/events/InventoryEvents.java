package es.projectalpha.pa.lobby.events;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryEvents implements Listener {

    private PALobby plugin;

    public InventoryEvents(PALobby instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        PAUser u = PAServer.getUser((Player) e.getWhoClicked());

        switch (e.getClickedInventory().getName()) {
            case "Servidores":
                switch (e.getSlot()) {
                    case 0:
                        //u.sendToServer("survival");
                        break;
                    default:
                        break;
                }
                e.setCancelled(true);
                break;
            default:
                break;
        }

        if (u.getPlayer().getGameMode() == GameMode.SURVIVAL || u.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            if (e.getInventory().getType() == InventoryType.PLAYER || e.getInventory().getType() == InventoryType.CRAFTING) e.setCancelled(true);
        }
    }
}
