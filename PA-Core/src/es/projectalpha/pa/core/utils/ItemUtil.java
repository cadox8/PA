package es.projectalpha.pa.core.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtil {


    public static String[] pages = new String[]{"&6Tower &rOf &cAthone\n\n &rTower Of Athone (TOA) es un modo de juego propio de &bProjectAlpha.\n &rAntes de empejar a jugar recomiendo leer este libro.",
            ""};

    public static ItemStack createHeadPlayer(String displayname, String username, List<String> lore) {
        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) playerHead.getItemMeta();
        sm.setOwner(username);
        ArrayList<String> colorLore = new ArrayList<>();
        if (lore != null) {
            lore.forEach(str -> colorLore.add(Utils.colorize(str)));
            sm.setLore(colorLore);
        }

        sm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
        sm.setDisplayName(Utils.colorize(displayname));
        playerHead.setItemMeta(sm);
        return playerHead;
    }

    public static ItemStack createBanner(String name, String lore, DyeColor color) {
        return createBanner(name, Arrays.asList(lore), color);
    }

    public static ItemStack createBanner(String name, List<String> lore, DyeColor color) {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta itemMeta = (BannerMeta) banner.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
        ArrayList<String> colorLore = new ArrayList<>();
        if (lore != null) {
            lore.forEach(str -> colorLore.add(Utils.colorize(str)));
            itemMeta.setLore(colorLore);
        }
        itemMeta.setBaseColor(color);
        itemMeta.setDisplayName(name);
        banner.setItemMeta(itemMeta);
        return banner;
    }

    public static ItemStack createBook(String name, String lore, String... pages) {
        return createBook(name, Arrays.asList(lore), pages);
    }

    public static ItemStack createBook(String name, List<String> lore, String... pages) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        ItemMeta bookMeta = book.getItemMeta();

        bookMeta.setDisplayName(Utils.colorize(name));
        ArrayList<String> colorLore = new ArrayList<>();
        if (lore != null) lore.forEach(str -> colorLore.add(Utils.colorize(str)));
        bookMeta.setLore(colorLore);

        meta.addPage(pages);
        meta.setAuthor(Utils.colorize("&6ProjectAlpha"));

        book.setItemMeta(bookMeta);

        return book;
    }
}
