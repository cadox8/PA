package es.projectalpha.pa.lobby.events;

import es.projectalpha.pa.core.api.PAServer;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.PALobby;
import es.projectalpha.pa.lobby.cosmetics.Cosmetic;
import es.projectalpha.pa.lobby.utils.LobbyMenu;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvents implements Listener {

    private PALobby plugin;

    public InventoryEvents(PALobby instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getClickedInventory().getTitle() == null) return;
        Player p = (Player) e.getWhoClicked();
        PAUser u = PAServer.getUser(p);

        switch (e.getClickedInventory().getTitle()) {
            case "Servidores":
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 0:
                        u.getPlayer().closeInventory();
                        LobbyMenu.openMenu(u, LobbyMenu.MenuType.RG);
                        break;
                    case 2:
                        u.sendToServer("creativo");
                        break;
                    case 4:
                        u.sendToServer("toa");
                        break;
                    case 6:
                        u.sendToServer("survival");
                        break;
                    case 8:
                        u.teleport(Utils.stringToLocation("mlb%0.5%47%35.5%0.3%-6"));
                        break;
                }
                u.sendSound(Sound.CLICK);
                break;
            case "RageGames":
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 2:
                        u.sendToServer("rg2");
                        break;
                    case 3:
                        u.sendToServer("rg3");
                        break;
                    case 4:
                        u.sendToServer("rg4");
                        break;
                    case 8:
                        u.teleport(Utils.stringToLocation("mlb%0.5%47%-34.5%179.4%-6.5"));
                        break;
                }
                u.sendSound(Sound.CLICK);
                break;
            case "Cosmeticos":
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 0:
                        u.removeItemInSlot(5);
                        u.getPlayer().getInventory().setItem(5, Cosmetic.ANTI_GRAVITY.getItemStack());
                        break;
                    case 1:
                        u.removeItemInSlot(5);
                        u.getPlayer().getInventory().setItem(5, Cosmetic.EXPLOSIVE_SHEEP.getItemStack());
                        break;
                    case 8:
                        u.removeItemInSlot(5);
                        break;

                    default:
                        break;
                }
                u.sendSound(Sound.CLICK);
                break;
            default:
                break;
        }
        if (!u.isOnRank(PACmd.Grupo.Admin)) e.setCancelled(true); //Prevenir que muevan / oculten / tiren objetos de la interfaz del Lobby
    }
}
