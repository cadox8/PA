package es.projectalpha.pa.toa.kits;

import lombok.Getter;
import org.bukkit.entity.Player;

public class Kit {

    public static final Warrior WARRIOR = new Warrior();
    public static final Archer ARCHER = new Archer();


    @Getter public String name;

    public Kit(String name){
        this.name = name;
    }

    public void setItems(Player p){}

    public boolean enabled(){
        return true;
    }
}
