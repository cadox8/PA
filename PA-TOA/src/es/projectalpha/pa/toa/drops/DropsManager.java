package es.projectalpha.pa.toa.drops;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.mobs.MobType;
import org.bukkit.Material;

import java.util.ArrayList;

public class DropsManager {

    //
    public final Drop ARROW = new Drop(MobType.SKELETON, new ItemMaker(Material.ARROW).setDisplayName("&6Flecha").build());


    //
    public ArrayList<Drop> drops;

    public DropsManager() {
        drops = new ArrayList<>();

        registerDrop(ARROW);
    }

    private void registerDrop(Drop... drop) {
        for (Drop d : drop) if (!drops.contains(d)) drops.add(d);
    }
}
