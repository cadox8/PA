package es.projectalpha.pa.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum PAData {

    CORE(0, "&cPA"),
    BUNGEE(1, "&cBungee"),
    LOBBY(2, "&dLobby"),
    ANTIUM(3, "&eAntium"),

    RG(4, "&cRageGames"),
    TOA(5, "&aTOA"),
    TB(6, "&eTowerBattle");

    @Getter private int id;
    private String prefix;

    public String getPrefix() {
        return ChatColor.GRAY + " || " + prefix + ChatColor.GRAY + " || ";
    }

    public String getOldPrefix() {
        return "§7 || " + prefix.replace('&', '§') + "§7 || ";
    }
}
