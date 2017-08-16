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
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Random;

public class Ability {

    //
    private static final Ability FANTASMA = new Fantasma();
    private static final Ability FRENESI = new Frenesi();
    private static final Ability DAGAS = new Dagas();
    private static final Ability FENIX = new Fenix();
    private static final Ability GUERRILLERO = new Guerrillero();
    private static final Ability BIT = new BIT();
    private static final Ability CRUCIO = new CrucioAbi();
    private static final Ability REGEN = new Regeneration();
    //

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
        String name = "ERROR";

        switch (m) {
            case APPLE:
                FANTASMA.play(u);
                name = FANTASMA.getName();
                break;
            case SUGAR:
                FRENESI.play(u);
                name = FRENESI.getName();
                break;
            case FEATHER:
                DAGAS.play(u);
                name = DAGAS.getName();
                break;
            case BLAZE_POWDER:
                FENIX.play(u);
                name = FENIX.getName();
                break;
            case POTION:
                GUERRILLERO.play(u);
                name = GUERRILLERO.getName();
                break;
            case NETHER_STAR:
                BIT.play(u);
                name = BIT.getName();
                break;
            case BARRIER:
                CRUCIO.play(u);
                name = CRUCIO.getName();
                break;
            case GOLDEN_APPLE:
                REGEN.play(u);
                name = REGEN.getName();
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
        u.sendMessage(PAData.TOA.getPrefix() + "&6Has usado la habilidad &c" + WordUtils.capitalizeFully(name.toLowerCase()));
    }
}