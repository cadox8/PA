package es.projectalpha.pa.core.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

public class BossBarUtils {

    public static void create(Player p, String title, BossBarAPI.Color color, BossBarAPI.Style style) {
        BossBarAPI.removeAllBars(p);
        BossBarAPI.addBar(p, new TextComponent(Utils.colorize(title)), color, style, 1f);
    }
}
