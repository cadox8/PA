package es.projectalpha.pa.lobby.cosmetics;

import es.projectalpha.pa.core.api.PAUser;
import lombok.Getter;
import org.bukkit.Material;

public class Cosmetic {

    @Getter private String name;
    @Getter private Material mat;
    @Getter private int cooldown;

    public Cosmetic(String name, Material mat, int cooldown) {
        this.name = name;
        this.mat = mat;
        this.cooldown = cooldown;
    }

    public void play(PAUser u) {}
}
