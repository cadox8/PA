package es.projectalpha.pa.sur.utils;


import es.projectalpha.pa.core.utils.ItemMaker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class Stones /*like the rolling*/ {

    ItemStack stone1 = new ItemMaker(Material.COAL_ORE).setDisplayName(ChatColor.GOLD + "Piedra de protección básica").setLores("Esta piedra te proporciona un area de 10x10x10.").addEnchant(Enchantment.DURABILITY, 1).build();


}
