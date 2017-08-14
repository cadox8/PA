package es.projectalpha.pa.toa.manager;

import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.api.TOAUser;
import es.projectalpha.pa.toa.spells.Spell;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private TOA plugin;

    @Getter private ArrayList<TOAUser> inTower = new ArrayList<>();
    @Getter private HashMap<TOAUser, Spell> selectedSpell = new HashMap<>();

    public GameManager(TOA instance) {
        this.plugin = instance;
    }

    public void joinTower(TOAUser u) {
        if (inTower.contains(u)) return;
        inTower.add(u);
    }

    public void leaveTower(TOAUser u) {
        if (!inTower.contains(u)) return;
        inTower.remove(u);
    }

    public void selectSpell(TOAUser u, Spell s) {
        if (selectedSpell.get(u).getName().equalsIgnoreCase(s.getName())) return;
        selectedSpell.put(u, null);
        selectedSpell.put(u, s);
    }
}
