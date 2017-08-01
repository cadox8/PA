package es.projectalpha.pa.toa.drops;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.mobs.MobType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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


    public static List<ItemStack> drop(MobType mt, int kit) {
        Random r = new Random();

        List<Drop> selectedDrops = new ArrayList<>();
        ItemStack i;
        ItemStack defI;

        int minPercent = r.nextInt(100) + 1;
        int maxPercent = minPercent == 100 ? 100 : r.nextInt(minPercent) + 1;

        //Magic :D
        TOA.getInstance().getDrops().drops.stream().filter(d -> mt.equals(d.getMt())).filter(d -> kit == d.getKit()).filter(d -> d.getDropPercent() >= minPercent).filter(d -> d.getDropPercent() <= maxPercent).forEach(selectedDrops::add);

        if (selectedDrops.isEmpty()) {
            i = new ItemStack(Material.AIR);
        } else {
            i = selectedDrops.get(r.nextInt(selectedDrops.size())).getItem();
        }

        switch (mt) {
            case SKELETON:
                defI = TOA.getInstance().getDrops().ARROW.getItem();
                break;
            default:
                defI = new ItemStack(Material.AIR);
                break;
        }
        return Arrays.asList(i, defI);
    }
}
