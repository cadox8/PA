package es.projectalpha.pa.toa.utils;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.TOA;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

public class TOAMenu {

    private static Inventory variado;
    private static Inventory armas;

    private TOA plugin;

    public TOAMenu(TOA instance) {
        this.plugin = instance;

        //Servidores
        variado = plugin.getServer().createInventory(null, 9, "Vender Variado");
        variado.setItem(0, new ItemMaker(plugin.getDrops().ARROW.getItem()).setLores("&3Precio venta x1: &c6 zenys").build());
        variado.setItem(1, new ItemMaker(plugin.getDrops().ROTTEN.getItem()).setLores("&3Precio venta x1: &c6 zenys").build());
        variado.setItem(2, new ItemMaker(plugin.getDrops().ROD.getItem()).setLores("&3Precio venta x1: &c8 zenys").build());

        //Cosmeticos
        armas = plugin.getServer().createInventory(null, 9, "Vender Armas");
    }

    public static void openMenu(PAUser u, MenuType menuType) {
        Inventory clon = null;

        switch (menuType) {
            case VARIADO:
                clon = variado;
                break;
            case ARMAS:
                clon = armas;
                break;
        }
        if (clon != null) {
            u.getPlayer().closeInventory();
            u.getPlayer().openInventory(clon);
            u.sendSound(Sound.CLICK);
        }
    }

    public enum MenuType {
        VARIADO, ARMAS
    }
}
