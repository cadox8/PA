package es.projectalpha.pa.lobby.utils;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.lobby.PALobby;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

public class LobbyMenu {

    private static Inventory servers;
    private static Inventory cometicos;

    private PALobby plugin = PALobby.getInstance();

    public LobbyMenu(PALobby instance) {
        this.plugin = instance;

        //Servidores
        servers = plugin.getServer().createInventory(null, 9, "Servidores");
        servers.setItem(0, new ItemMaker(Material.BOW).setDisplayName(PAData.RG.getName()).build());
        servers.setItem(4, new ItemMaker(Material.IRON_AXE).setDisplayName("&6Tower&rOf&cAncients").build());
        servers.setItem(8, new ItemMaker(Material.TNT).setDisplayName(PAData.NS.getName()).build());

        //Cosmeticos
        cometicos = plugin.getServer().createInventory(null, 27, "Cosmeticos");

    }

    public static void openMenu(PAUser u, MenuType menuType) {
        Inventory clon = null;

        switch (menuType) {
            case SERVERS:
                clon = servers;
                break;
            case COSMETICOS:
                clon = cometicos;
                break;
        }
        if (clon != null) {
            u.getPlayer().closeInventory();
            u.getPlayer().openInventory(clon);
            u.sendSound(Sound.CLICK);
        }
    }

    public enum MenuType {
        SERVERS, COSMETICOS
    }
}
