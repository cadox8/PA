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
}
