package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.toa.api.TOAUser;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Kit {

    private static ArrayList<Kit> kits = new ArrayList<>();

    public static final Warrior WARRIOR = new Warrior(0);
    public static final Picaro PICARO = new Picaro(1);
    public static final Archer ARCHER = new Archer(2);


    @Getter public String name;
    @Getter public int id;

    public Kit(int id, String name){
        this.name = name;
        this.id = id;
    }

    public void setItems(Player p){}
    public void addEffects(TOAUser u) {}


    public boolean enabled(){
        return true;
    }




    public static void registerKits() {
        kits.add(WARRIOR);
        kits.add(PICARO);
        kits.add(ARCHER);
    }

    public static Kit parseKit(int id) {
        for (Kit k : kits) {
            if (k.getId() == id) return k;
        }
        return null;
    }
}
