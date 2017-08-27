package es.projectalpha.pa.sur.utils;


import es.projectalpha.pa.core.utils.ItemMaker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class Stones /*like the rolling*/ {

    public ItemStack stone1 = new ItemMaker(Material.COAL_ORE).setDisplayName(ChatColor.AQUA + "Piedra de protección básica").setLores("Esta piedra te proporciona un area de 10x10x10.").addEnchant(Enchantment.DURABILITY, 1).build();
    public ItemStack stone2 = new ItemMaker(Material.IRON_ORE).setDisplayName(ChatColor.DARK_AQUA + "Piedra de protección").setLores("Esta piedra te proporciona un area de 20x20x20.").addEnchant(Enchantment.DURABILITY, 1).build();
    public ItemStack stone3 = new ItemMaker(Material.GOLD_ORE).setDisplayName(ChatColor.LIGHT_PURPLE + "Piedra de protección").setLores("Esta piedra te proporciona un area de 30x30x30.").addEnchant(Enchantment.DURABILITY, 1).build();
    public ItemStack stone4 = new ItemMaker(Material.LAPIS_ORE).setDisplayName(ChatColor.DARK_PURPLE + "Piedra de protección").setLores("Esta piedra te proporciona un area de 40x40x40.").addEnchant(Enchantment.DURABILITY, 1).build();
    public ItemStack stone5 = new ItemMaker(Material.EMERALD_ORE).setDisplayName(ChatColor.RED + "Piedra de protección").setLores("Esta piedra te proporciona un area de 50x50x50.").addEnchant(Enchantment.DURABILITY, 1).build();
    public ItemStack stone6 = new ItemMaker(Material.DIAMOND_ORE).setDisplayName(ChatColor.GOLD + "Piedra de protección").setLores("Esta piedra te proporciona un area de 70x70x70.").addEnchant(Enchantment.DURABILITY, 1).build();
    public ItemStack staffst = new ItemMaker(Material.BEDROCK).setDisplayName(ChatColor.GRAY + "Piedra de protección del Staff").setLores("Esta piedra te proporciona un area de 150x150x150.").addEnchant(Enchantment.DURABILITY, 1).build();


    @AllArgsConstructor
    public enum StoneType {
        COAL(new Stones().stone1, 5),
        IRON(new Stones().stone2, 10),
        GOLD(new Stones().stone3, 15),
        LAPIS(new Stones().stone4, 20),
        EMERALD(new Stones().stone5, 25),
        DIAMOND(new Stones().stone6, 35),
        STAFF(new Stones().staffst, 75);

        @Getter private ItemStack itemStack;
        @Getter private int area;
    }
}
