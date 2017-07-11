package es.projectalpha.pa.toa.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Warrior extends Kit {

    public Warrior(){
        super("Caballero");
    }

    public void setItems(Player p) {
        p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));

        p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    }
}
