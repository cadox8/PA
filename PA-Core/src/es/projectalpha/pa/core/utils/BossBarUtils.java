package es.projectalpha.pa.core.utils;

import es.projectalpha.pa.core.PACore;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BossBarUtils {

    public static BossBar bossBar(String title){
        return bossBar(title, BarColor.PINK, BarStyle.SOLID);
    }

    public static BossBar bossBar(String title, BarColor color){
        return bossBar(title, color, BarStyle.SOLID);
    }

    public static BossBar bossBar(String title, BarColor color, BarStyle style, BarFlag... flags){
        return PACore.getInstance().getServer().createBossBar(title, color, style, flags);
    }

    public static BossBar editBossBar(BossBar bossBar, String title) {
        bossBar.setTitle(title);
        return bossBar;
    }
}
