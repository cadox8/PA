package es.projectalpha.pa.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

@AllArgsConstructor
public enum ColorParser {

    BLACK(DyeColor.BLACK, ChatColor.DARK_GRAY),
    BLUE(DyeColor.BLUE, ChatColor.DARK_BLUE),
    GOLD(DyeColor.BROWN, ChatColor.GOLD),
    AQUA(DyeColor.CYAN, ChatColor.AQUA),
    GRAY(DyeColor.GRAY, ChatColor.GRAY),
    GREEN(DyeColor.GREEN, ChatColor.DARK_GREEN),
    LIGHT_BLUE(DyeColor.LIGHT_BLUE, ChatColor.BLUE),
    LIME(DyeColor.LIME, ChatColor.GREEN),
    MAGENTA(DyeColor.MAGENTA, ChatColor.LIGHT_PURPLE),
    ORANGE(DyeColor.ORANGE, ChatColor.GOLD),
    PINK(DyeColor.PINK, ChatColor.LIGHT_PURPLE),
    PURPLE(DyeColor.PURPLE, ChatColor.DARK_PURPLE),
    RED(DyeColor.RED, ChatColor.DARK_RED),
    SILVER(DyeColor.SILVER, ChatColor.GRAY),
    WHITE(DyeColor.WHITE, ChatColor.WHITE),
    YELLOW(DyeColor.YELLOW, ChatColor.YELLOW);

    @Getter private DyeColor dyeColor;
    @Getter private ChatColor chatColor;
}
