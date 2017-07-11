package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.core.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Archer extends Kit {

    public Archer() {
        super("Arquero");
    }

    public void setItems(Player p) {
        p.getInventory().addItem(new ItemMaker(Material.BOW).addUnsafeEnchant(Enchantment.ARROW_INFINITE, 1).setUnbreakable().build());
    }
}
