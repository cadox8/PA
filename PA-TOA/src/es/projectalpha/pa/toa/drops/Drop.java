package es.projectalpha.pa.toa.drops;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.mobs.MobType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Drop {

    private TOA plugin = TOA.getInstance();

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

    public List<ItemStack> drop(MobType mt, int kit) {
        Random r = new Random();

        List<Drop> selectedDrops = new ArrayList<>();
        ItemStack i;
        ItemStack defI;

        int minPercent = r.nextInt(100) + 1;
        int maxPercent = minPercent == 100 ? 100 : r.nextInt(minPercent) + 1;

        //Magic :D
        plugin.getDrops().drops.stream().filter(d -> mt.equals(d.getMt())).filter(d -> kit == d.getKit()).filter(d -> d.getDropPercent() >= minPercent).filter(d -> dropPercent <= maxPercent).forEach(selectedDrops::add);

        i = selectedDrops.get(r.nextInt(selectedDrops.size())).getItem();

        if (selectedDrops.isEmpty()) i = new ItemStack(Material.AIR);

        switch (mt) {
            case SKELETON:
                defI = plugin.getDrops().ARROW.getItem();
                break;
            default:
                defI = new ItemStack(Material.AIR);
                break;
        }
        return Arrays.asList(i, defI);
    }
}
