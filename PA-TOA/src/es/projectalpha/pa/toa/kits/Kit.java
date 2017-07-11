package es.projectalpha.pa.toa.kits;

import lombok.Getter;
import org.bukkit.entity.Player;

public class Kit {

    @Getter public String name;

    public Kit(String name){
        this.name = name;
    }

    public void setItems(Player p){}

    public boolean enabled(){
        return true;
    }
}
