package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.PACore;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {

    private PACore plugin = PACore.getInstance();

    public Inventory createInventory(int size, String name){
        return createInventory(InventoryType.CHEST, size, name);
    }

    public Inventory createInventory(InventoryType inventoryType, String name){
        return createInventory(inventoryType, 0, name);
    }

    public Inventory createInventory(InventoryType inventoryType, int size, String name) throws IndexOutOfBoundsException {
        if (size % 9 != 0 || size > 56) throw new IndexOutOfBoundsException("El tamaño es mayor que 56 o no es un múltiplo de 9");
        name = Utils.colorize(name);

        switch (inventoryType){
            case CHEST:
                return plugin.getServer().createInventory(null, size, name);
            case HOPPER:
            case DISPENSER:
            case DROPPER:
                return plugin.getServer().createInventory(null, inventoryType, name);
            default:
                return plugin.getServer().createInventory(null, size, name);
        }
    }

    public Inventory addItem(Inventory inv, Material... materials){
        List<ItemStack> items = new ArrayList<>();

        for (Material m : materials){
            items.add(new ItemStack(m));
        }
        return addItem(inv, items.toArray(new ItemStack[materials.length]));
    }

    public Inventory addItem(Inventory inv, ItemStack... items){
        inv.addItem(items);
        return inv;
    }


    public Inventory setItem(Inventory inv, int slot, Material material){
        return setItem(inv, slot, new ItemStack(material));
    }

    public Inventory setItem(Inventory inv, int slot, ItemStack item){
        inv.setItem(slot, item);
        return inv;
    }
}
