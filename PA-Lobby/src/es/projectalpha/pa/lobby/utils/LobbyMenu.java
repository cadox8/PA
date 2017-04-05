package es.projectalpha.pa.lobby.utils;

import es.projectalpha.pa.lobby.LobbyPlayer;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbyMenu {

    private PALobby plugin;

    public enum MenuType {
        SERVERS
    }

    private static Inventory servers;

    public LobbyMenu(PALobby instance){
        this.plugin = instance;
        servers = plugin.getServer().createInventory(null, 54, "Servidores");

        servers.addItem(new ItemStack(Material.APPLE));
    }

    public static void openMenu(LobbyPlayer u, MenuType menuType){
        Inventory clon = null;

        switch (menuType){
            case SERVERS:
                clon = servers;
                break;
        }
        if (clon != null) {
            u.getPlayer().closeInventory();
            u.getPlayer().openInventory(clon);
            u.sendSound(Sound.UI_BUTTON_CLICK);
        }
    }
}
