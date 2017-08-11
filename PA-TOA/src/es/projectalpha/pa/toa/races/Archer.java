package es.projectalpha.pa.toa.races;

import es.projectalpha.pa.core.utils.ItemMaker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Archer extends Race {

    public Archer(RaceType id, double health) {
        super(id, "Arquero", health);
    }

    public void setItems(Player p) {
        p.getInventory().setItem(0, new ItemMaker(Material.BOW).addEnchant(Enchantment.ARROW_INFINITE, 1).setUnbreakable().build());

        p.getInventory().setItem(2, new ItemMaker(Material.POTION).setDisplayName("Guerrillero").build());

        p.getInventory().setItem(8, new ItemMaker(Material.BLAZE_POWDER).setDisplayName("Fenix").build());

        p.getInventory().setItem(9, new ItemMaker(Material.ARROW).build());

        p.getInventory().setHelmet(new ItemMaker(Material.CHAINMAIL_HELMET).build());
        p.getInventory().setChestplate(new ItemMaker(Material.CHAINMAIL_CHESTPLATE).build());
        p.getInventory().setLeggings(new ItemMaker(Material.CHAINMAIL_LEGGINGS).build());
        p.getInventory().setBoots(new ItemMaker(Material.CHAINMAIL_BOOTS).build());
    }

    public Location spawn(){
        return plugin.getAm().getArcher();
    }
}
