package es.projectalpha.pa.toa.drops;

import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.toa.mobs.MobType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Drop {

    //
    public static final Drop ARROW = new Drop(MobType.SKELETON, new ItemMaker(Material.ARROW).setDisplayName("&6Flecha").build());
    public static final Drop ROTTEN = new Drop(MobType.ZOMBIE, new ItemMaker(Material.ROTTEN_FLESH).setDisplayName("&6Carne podrida").build());
    public static final Drop ROD = new Drop(MobType.BLAZE, new ItemMaker(Material.BLAZE_ROD).setDisplayName("&6Vara de fuego").build());
    //
    private static ArrayList<Drop> drops = new ArrayList<>();

    @Getter private MobType mt;
    @Getter private int kit;
    @Getter private int dropPercent;
    @Getter private ItemStack item;

    public Drop(MobType mt, ItemStack item) {
        this(mt, 10, 100, item);
    }

    public Drop(MobType mt, int kit, int dropPercent, ItemStack item) {
        this.mt = mt;
        this.kit = kit;
        this.dropPercent = dropPercent;
        this.item = item;
    }

    public static void registerDrop(Drop... drop) {
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
        drops.stream().filter(d -> mt.equals(d.getMt())).filter(d -> kit == d.getKit()).filter(d -> d.getDropPercent() >= minPercent).filter(d -> d.getDropPercent() <= maxPercent).forEach(selectedDrops::add);

        if (selectedDrops.isEmpty()) {
            i = new ItemStack(Material.AIR);
        } else {
            i = selectedDrops.get(r.nextInt(selectedDrops.size())).getItem();
        }

        switch (mt) {
            case SKELETON:
                defI = ARROW.getItem();
                break;
            default:
                defI = new ItemStack(Material.AIR);
                break;
        }
        return Arrays.asList(i, defI);
    }
}
