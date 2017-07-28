package es.projectalpha.pa.toa.races;

import es.projectalpha.pa.core.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class Archer extends Race {

    public Archer(RaceType id, int health) {
        super(id, "Arquero", health);
    }

    public void setItems(Player p) {
        p.getInventory().addItem(new ItemMaker(Material.BOW).addUnsafeEnchant(Enchantment.ARROW_INFINITE, 1).setUnbreakable().build());
    }

    public boolean enabled() {
        return false;
    }
}
