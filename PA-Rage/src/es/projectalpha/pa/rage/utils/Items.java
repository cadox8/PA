package es.projectalpha.pa.rage.utils;

import es.projectalpha.pa.core.utils.ItemMaker;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public final class Items {

    @Getter
    private static final ItemStack arrow = new ItemMaker(Material.ARROW).setDisplayName("&6RageArrow").build();
    @Getter
    private static final ItemStack knife = new ItemMaker(Material.IRON_SWORD).setDisplayName("&6RageKnife").setUnbreakable().addItemFlag(ItemFlag.values()).build();
    @Getter
    private static final ItemStack axe = new ItemMaker(Material.IRON_AXE).setDisplayName("&6RageAxe").setUnbreakable().addItemFlag(ItemFlag.values()).build();
    @Getter
    private static final ItemStack bow = new ItemMaker(Material.BOW).setDisplayName("&6RageBow").setUnbreakable().addItemFlag(ItemFlag.values()).build();
}
