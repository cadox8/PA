package es.projectalpha.pa.sn.recipes;

import es.projectalpha.pa.core.utils.ItemMaker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Created by cadox on 22/12/2016.
 */
public class PokeEgg {

    public ItemStack getPokeEgg(){
        return new ItemMaker(Material.EGG).setAmount(4).setDisplayName(ChatColor.YELLOW + "Poke" + ChatColor.WHITE + "Egg").setLores(ChatColor.WHITE + "Lanzalo contra un mob", ChatColor.WHITE + "para atraparlo").addEnchant(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build();
    }

    public void registerRecipe(){
        ItemStack i = getPokeEgg();

        ShapedRecipe pokeEgg = new ShapedRecipe(i);

        pokeEgg.shape("BAB", "DCD", "BAB");

        pokeEgg.setIngredient('B', Material.WOOL);
        pokeEgg.setIngredient('A', Material.STRING);
        pokeEgg.setIngredient('D', Material.GOLD_NUGGET);
        pokeEgg.setIngredient('C', Material.EGG);

        Bukkit.addRecipe(pokeEgg);
    }
}
