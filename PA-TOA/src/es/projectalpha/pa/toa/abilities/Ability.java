package es.projectalpha.pa.toa.abilities;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.abilities.list.Dagas;
import es.projectalpha.pa.toa.abilities.list.Fantasma;
import es.projectalpha.pa.toa.abilities.list.Frenesi;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Random;

public class Ability {

    protected TOA plugin = TOA.getInstance();

    protected Random r = new Random();

    @Getter private static ArrayList<TOAUser> fireArrow = new ArrayList<>();

    @Getter private String name;
    @Getter private Race.RaceType race;

    public Ability(String name, Race.RaceType race) {
        this.name = name;
        this.race = race;
    }

    protected void play(TOAUser u) {}

    protected boolean canUse(TOAUser u) {
        return u.getUserData().getKit() == race.getId();
    }

    public static void useAbility(TOAUser u, Material m) {
        switch (m) {
            case APPLE:
                new Fantasma().play(u);
                break;
            case SUGAR:
                new Frenesi().play(u);
                break;
            case FEATHER:
                new Dagas().play(u);
                break;

            default:
                break;
        }
    }
}
