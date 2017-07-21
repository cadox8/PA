package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class Picaro extends Race {

    public Picaro(int id, int health) {
        super(id, "Picaro", health);
    }

    public void setItems(Player p) {
        p.getInventory().addItem(new ItemMaker(Material.SHEARS).setDisplayName("&bChas Chas Chas").setLores("&dDaño:", "&734").addItemFlag(ItemFlag.values()).setUnbreakable().build());

        p.getInventory().addItem(new ItemMaker(Material.ARMOR_STAND).setDisplayName("&eLadrón").build());
        p.getInventory().addItem(new ItemMaker(Material.SUGAR).setDisplayName("&eFrenesí").build());
        p.getInventory().addItem(new ItemMaker(Material.FEATHER).setDisplayName("&eDagas").build());

        p.getInventory().addItem(new ItemMaker(Material.APPLE).setDisplayName("&cFantasma").build());

        addEffects(TOA.getPlayer(p));
    }

    public void addEffects(TOAUser u) {
        u.getPlayer().setWalkSpeed(0.2f);
    }
}
