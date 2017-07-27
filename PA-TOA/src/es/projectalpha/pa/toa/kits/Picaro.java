package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Picaro extends Race {

    public Picaro(int id, int health) {
        super(id, "Picaro", health);
    }

    public void setItems(Player p) {
        p.getInventory().setItem(1, new ItemMaker(Material.SHEARS).setDisplayName("&bChas Chas Chas").setLores("&dDaño:", "&734").addItemFlag(ItemFlag.values()).setUnbreakable().build());

        p.getInventory().setItem(2, new ItemMaker(Material.ARMOR_STAND).setDisplayName("&eLadrón").build());
        p.getInventory().setItem(3, new ItemMaker(Material.SUGAR).setDisplayName("&eFrenesí").build());
        p.getInventory().setItem(4, new ItemMaker(Material.FEATHER).setDisplayName("&eDagas").build());

        p.getInventory().setItem(8, new ItemMaker(Material.APPLE).setDisplayName("&cFantasma").build());

        p.getInventory().setHelmet(new ItemMaker(Material.CHAINMAIL_HELMET).setUnbreakable().build());
        p.getInventory().setChestplate(new ItemMaker(Material.CHAINMAIL_CHESTPLATE).setUnbreakable().build());
        p.getInventory().setLeggings(new ItemMaker(Material.CHAINMAIL_LEGGINGS).setUnbreakable().build());
        p.getInventory().setBoots(new ItemMaker(Material.CHAINMAIL_BOOTS).setUnbreakable().build());

        addEffects(TOA.getPlayer(p));
    }

    public void addEffects(TOAUser u) {
        u.getPlayer().setWalkSpeed(0.3f);
    }
}
