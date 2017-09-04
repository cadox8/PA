package es.projectalpha.pa.sur.casino;

import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import es.projectalpha.pa.sur.tasks.AnimationTask;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Casino {

    private PASurvival plugin = PASurvival.getInstance();

    @Getter private Location l;

    public Casino(Location l) {
        this.l = l;
    }

    public void play(SurvivalUser u, int in) {
        new AnimationTask(this, u, in).runTaskTimer(plugin, 0, 20);
    }

    public void effect() {

    }

    public void reward(SurvivalUser u, double reward, ItemStack reward2) {

    }
}
