package es.projectalpha.pa.toa.kits;

import es.projectalpha.pa.toa.api.TOAUser;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Race {

    //ToDo: Punto de spawn por cada clase

    public static final Race WARRIOR = new Warrior(0, 1130);
    public static final Race PICARO = new Picaro(1, 1034);
    public static final Race ARCHER = new Archer(2, 1014);
    public static final Race MAGE = new Mage(3, 1000);
    private static ArrayList<Race> races = new ArrayList<>();

    @Getter private String name;
    @Getter private int id;
    @Getter private int health;

    public Race(int id, String name, int health) {
        this.name = name;
        this.id = id;
        this.health = health;
    }

    public static void registerRaces() {
        races.add(WARRIOR);
        races.add(PICARO);
        races.add(ARCHER);
        races.add(MAGE);
    }

    public void setItems(Player p) {
    }

    public void addEffects(TOAUser u) {
    }

    public boolean enabled() {
        return true;
    }



    public static Race parseRace(int id) {
        for (Race k : races) if (k.getId() == id) return k;
        return null;
    }
}
