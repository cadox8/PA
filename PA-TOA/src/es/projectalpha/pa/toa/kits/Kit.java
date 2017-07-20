package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.toa.api.TOAUser;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Kit {

    //ToDo: Punto de spawn por cada clase

    public static final Warrior WARRIOR = new Warrior(0, 1130);
    public static final Picaro PICARO = new Picaro(1, 1034);
    public static final Archer ARCHER = new Archer(2, 1014);
    private static ArrayList<Kit> kits = new ArrayList<>();

    @Getter private String name;
    @Getter private int id;
    @Getter private int health;

    public Kit(int id, String name, int health) {
        this.name = name;
        this.id = id;
        this.health = health;
    }

    public static void registerKits() {
        kits.add(WARRIOR);
        kits.add(PICARO);
        kits.add(ARCHER);
    }

    public static Kit parseKit(int id) {
        for (Kit k : kits) if (k.getId() == id) return k;
        return null;
    }

    public void setItems(Player p) {
    }

    public void addEffects(TOAUser u) {
    }

    public boolean enabled() {
        return true;
    }
}
