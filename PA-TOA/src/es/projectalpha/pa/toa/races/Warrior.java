package es.projectalpha.pa.toa.races;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Warrior extends Race {

    public Warrior(RaceType id, double health) {
        super(id, "Caballero", health);
    }

    public void setItems(Player p) {
        p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));

        p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    }


    public Location spawn(){
        return plugin.getAm().getWarrior();
    }
}
