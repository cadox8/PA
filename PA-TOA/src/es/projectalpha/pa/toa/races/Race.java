package es.projectalpha.pa.toa.races;

import es.projectalpha.pa.toa.api.TOAUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Race {

    //ToDo: Punto de spawn por cada clase

    public static final Race WARRIOR = new Warrior(RaceType.WARRIOR, 1130);
    public static final Race PICARO = new Picaro(RaceType.PICARO, 1034);
    public static final Race ARCHER = new Archer(RaceType.ARCHER, 1014);
    public static final Race MAGE = new Mage(RaceType.MAGE, 1000);
    private static ArrayList<Race> races = new ArrayList<>();

    @Getter private String name;
    @Getter private RaceType raceType;
    @Getter private int health;

    public Race(RaceType raceType, String name, int health) {
        this.name = name;
        this.raceType = raceType;
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
        for (Race k : races) if (k.getRaceType().getId() == id) return k;
        return null;
    }

    @AllArgsConstructor
    public enum RaceType {
        WARRIOR(0), PICARO(1), ARCHER(2), MAGE(3);

        @Getter private int id;
    }
}
