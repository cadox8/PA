package es.projectalpha.pa.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PAData {

    CORE(0, "&cPA"),
    BUNGEE(1, "&cBungee"),
    LOBBY(2, "&dLobby"),
    ANTIUM(3, "&eAntium"),

    RG(4, "&cRageGames"),
    TOA(5, "&aTOA"),
    NS(6, "&eNexus&bSiege"),
    SN(7, "&bSafari&8Net");

    @Getter
    private int id;
    private String prefix;

    public String getName() {
        return prefix;
    }
    public String getPrefix() {
        return "&7 || " + prefix + "&7 || &r";
    }
    public String getOldPrefix() {
        return "§7 || " + prefix.replace('&', '§') + "§7 || §r";
    }
}
