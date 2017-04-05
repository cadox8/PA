package es.projectalpha.pa.core.crates;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ItemMaker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Crate {

    @Getter private String name;
    @Getter private CrateType crateType;

    protected PACore plugin = PACore.getInstance();

    public Crate(String name){
        this(name, CrateType.NORMAL);
    }

    public Crate(String name, CrateType crateType){
        this.name = name;
        this.crateType = crateType;
    }

    public void open(PAUser user){
    }

    public ArrayList<ItemStack> contains(){
        ArrayList<ItemStack> i = new ArrayList<>();
        i.add(new ItemMaker(Material.APPLE).setDisplayName(":D").build());
        return i;
    }


    @AllArgsConstructor
    public enum CrateType {
        NORMAL("Normal"),
        EPICA("Épica"),
        LEGENDARIA("Legendaria");

        @Getter private final String name;
    }
}
