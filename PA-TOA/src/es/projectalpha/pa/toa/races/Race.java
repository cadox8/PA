package es.projectalpha.pa.toa.races;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Race {

    protected TOA plugin = TOA.getInstance();

    //ToDo: Punto de spawn por cada clase

    public static final Race WARRIOR = new Warrior(RaceType.WARRIOR, 113);
    public static final Race PICARO = new Picaro(RaceType.PICARO, 103.4);
    public static final Race ARCHER = new Archer(RaceType.ARCHER, 101.4);
    public static final Race MAGE = new Mage(RaceType.MAGE, 100);
    private static ArrayList<Race> races = new ArrayList<>();

    @Getter private String name;
    @Getter private RaceType raceType;
    @Getter private double health;

    public Race(RaceType raceType, String name, double health) {
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

    public Location spawn(){
        return null;
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
