package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.api.TOAUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mage extends Race {

    public Mage(int id, int health) {
        super(id,"Mago", health);
    }

    public void setItems(Player p) {
        p.getInventory().setItem(0, new ItemMaker(Material.WRITTEN_BOOK).setDisplayName("&dLibro del conocimiento").build());
    }

    public void addEffects(TOAUser u) {}
}
