package es.projectalpha.pa.toa.spells;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.spells.list.BackInTime;
import es.projectalpha.pa.toa.spells.list.Crucio;
import es.projectalpha.pa.toa.spells.list.Regen;
import lombok.Getter;

public class Spell {

    // Spells
    public static final Spell BIT = new BackInTime(0);
    public static final Spell CRUCIO = new Crucio(0);
    public static final Spell REGEN = new Regen(0);
    //

    protected TOA plugin = TOA.getInstance();

    @Getter private String name;
    @Getter private int cooldown; //Segundos
    @Getter private int difficulty; //1-5

    public Spell(String name, int cooldown, int difficulty) {
        this.name = name;
        this.cooldown = cooldown;
        this.difficulty = difficulty;
    }

    public void spell(TOAUser u) {}
}
