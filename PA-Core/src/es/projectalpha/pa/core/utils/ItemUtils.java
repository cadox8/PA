package es.projectalpha.pa.core.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static ItemStack coloredBlock(DyeColor color, String name, ColoredBlock block) {
        return coloredBlock(color, name, 1, block);
    }

    public static ItemStack coloredBlock(DyeColor color, int amount, ColoredBlock block) {
        return coloredBlock(color, "%0000%1", amount, block);
    }

    public static ItemStack coloredBlock(DyeColor color, String name, int amount, ColoredBlock block) {
        ItemStack i;
        ItemMeta im;

        switch (block) {
            case GLASS:
                i = new ItemStack(Material.STAINED_GLASS, amount, color.getDyeData());
                break;
            case FLAT_GLASS:
                i = new ItemStack(Material.STAINED_GLASS_PANE, amount, color.getDyeData());
                break;
            case CLAY:
                i = new ItemStack(Material.CLAY, amount, color.getDyeData());
                break;
            default:
                i = new ItemStack(Material.AIR);
                break;
        }

        im = i.getItemMeta();
        if (!name.equalsIgnoreCase("%0000%1")) im.setDisplayName(Utils.colorize(name));

        i.setItemMeta(im);

        return i;
    }

    public enum ColoredBlock {
        GLASS, FLAT_GLASS, CLAY;
    }
}
