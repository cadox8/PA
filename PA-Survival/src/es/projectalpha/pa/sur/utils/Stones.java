package es.projectalpha.pa.sur.utils;


import es.projectalpha.pa.core.utils.ItemMaker;
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


}
