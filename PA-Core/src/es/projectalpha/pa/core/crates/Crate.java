package es.projectalpha.pa.core.crates;

import es.projectalpha.pa.core.api.PAUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Crate {

    @Getter private String name;
    @Getter private CrateType crateType;
    @Getter private CrateAction crateAction;
    @Getter private Location location;

    public Crate(String name, CrateAction crateAction, Location location){
        this(name, CrateType.NORMAL, crateAction, location);
    }

    public Crate(String name, CrateType crateType, CrateAction crateAction, Location location){
        this.name = name;
        this.crateType = crateType;
        this.crateAction = crateAction;
        this.location = location;
    }



    public void use(PAUser user){}

    public ArrayList<ItemStack> contains(){
        return new ArrayList<>();
    }


    @AllArgsConstructor
    public enum CrateType{
        NORMAL("Normal"),
        EPICA("Épica"),
        LEGENDARIA("Legendaria");

        @Getter private final String name;
    }

    public enum CrateAction{
        RANDOM, DROP;
    }
}
