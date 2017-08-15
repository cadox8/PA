package es.projectalpha.pa.toa.abilities;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.abilities.archer.Fenix;
import es.projectalpha.pa.toa.abilities.archer.Guerrillero;
import es.projectalpha.pa.toa.abilities.mage.BIT;
import es.projectalpha.pa.toa.abilities.mage.CrucioAbi;
import es.projectalpha.pa.toa.abilities.mage.Regeneration;
import es.projectalpha.pa.toa.abilities.picaro.Dagas;
import es.projectalpha.pa.toa.abilities.picaro.Fantasma;
import es.projectalpha.pa.toa.abilities.picaro.Frenesi;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.races.Race;
import es.projectalpha.pa.toa.spells.Spell;
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
    @Getter private int cooldown;

    public Ability(String name, int cooldown, Race.RaceType race) {
        this.name = name;
        this.cooldown = cooldown;
        this.race = race;
    }

    protected void play(TOAUser u) {}

    protected boolean canUse(TOAUser u) {
        return u.getUserData().getKit() == race.getId();
    }

    protected boolean isInCooldown(TOAUser u, String abi) {
        if (new Cooldown(0).isCoolingDown(abi)) {
            u.sendMessage(PAData.TOA + "&cEsta habilidad está en cooldown &7(&6" + new Cooldown(0).getTimeLeft(abi) + " segundos&7)");
            return true;
        }
        return false;
    }

    public static void useAbility(TOAUser u, Material m) {
        if (!TOA.getInstance().getGm().getInTower().contains(u)) return;
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
            case BLAZE_POWDER:
                new Fenix().play(u);
                break;
            case POTION:
                new Guerrillero().play(u);
                break;
            case NETHER_STAR:
                new BIT().play(u);
                break;
            case BARRIER:
                new CrucioAbi().play(u);
                break;
            case GOLDEN_APPLE:
                new Regeneration().play(u);
                break;
            case ENCHANTED_BOOK:
                Spell s = TOA.getInstance().getGm().getSelectedSpell().get(u);
                if (s == null) return;
                if (new Cooldown(0).isCoolingDown(s.getName())) {
                    u.sendMessage(PAData.TOA + "&cEsta habilidad está en cooldown &7(&6" + new Cooldown(0).getTimeLeft(s.getName()) + " segundos&7)");
                    return;
                }
                s.spell(u);
                new Cooldown(s.getCooldown()).setOnCooldown(s.getName());
                break;

            default:
                break;
        }
    }
}