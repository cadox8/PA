package es.projectalpha.pa.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum PAData {

    RAGE(0, "&cRage&6Games"),
    TOWER(1, "Tower&cBattle");

    @Getter private int id;
    @Getter private String prefix;

    public String fullPrefix() {
        return ChatColor.GRAY + " || " + prefix + ChatColor.GRAY + " || ";
    }
}
