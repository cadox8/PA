package es.projectalpha.pa.toa.races;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.abilities.mage.CrucioAbi;
import es.projectalpha.pa.toa.api.TOAUser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Mage extends Race {

    public Mage(RaceType id, int health) {
        super(id,"Mago", health);
    }

    public void setItems(Player p) {
        p.getInventory().setItem(0, new ItemMaker(Material.ENCHANTED_BOOK).setDisplayName("&dLibro del conocimiento").build());

        p.getInventory().setItem(3, new ItemMaker(Material.NETHER_STAR).setDisplayName("&dBackInTime").build());
        p.getInventory().setItem(4, new ItemMaker(Material.BARRIER).setDisplayName("&cCrucio").build());
        p.getInventory().setItem(5, new ItemMaker(Material.GOLDEN_APPLE).setDisplayName("&dRegeneración").build());

        addEffects(TOA.getPlayer(p));
    }

    public void addEffects(TOAUser u) {
        new CrucioAbi().play(u);
    }

    public Location spawn(){
        return plugin.getAm().getMage();
    }
}
