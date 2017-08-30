package es.projectalpha.pa.sur.recipes;

import es.projectalpha.pa.core.utils.ItemMaker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Bocatas {

    public ItemStack getBocataJamon(){
        return new ItemMaker(Material.BREAD).setAmount(1).setDisplayName(ChatColor.YELLOW + "Bocadillo de Jamón").addEnchant(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build();
    }

    public void bocataJamon(){
        ItemStack i = getBocataJamon();

        ShapedRecipe pokeEgg = new ShapedRecipe(i);

        pokeEgg.shape("BAB", "BCB", "BAB");

        pokeEgg.setIngredient('B', Material.AIR);
        pokeEgg.setIngredient('A', Material.BREAD);
        pokeEgg.setIngredient('C', Material.GRILLED_PORK);

        Bukkit.addRecipe(pokeEgg);
    }

    public ItemStack getHambur(){
        return new ItemMaker(Material.BREAD).setAmount(1).setDisplayName(ChatColor.YELLOW + "Hamburguesa Artesana").addEnchant(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build();
    }

    public void hamburguesa(){
        ItemStack i = getBocataJamon();

        ShapedRecipe pokeEgg = new ShapedRecipe(i);

        pokeEgg.shape("BAB", "BCB", "BAB");

        pokeEgg.setIngredient('B', Material.AIR);
        pokeEgg.setIngredient('A', Material.BREAD);
        pokeEgg.setIngredient('C', Material.COOKED_BEEF);

        Bukkit.addRecipe(pokeEgg);
    }
}
