package es.projectalpha.pa.sn.utils;

import org.bukkit.ChatColor;

import java.util.Random;

/**
 * Created by cadox on 12/12/2016.
 */
public class ColorUtils {

    private ChatColor getRandomChatColor(){
        ChatColor c = ChatColor.values()[new Random().nextInt(ChatColor.values().length)];
        return c;
    }

    public ChatColor getRandomColor(){
        ChatColor c = getRandomChatColor();

        if(c == ChatColor.MAGIC || c == ChatColor.STRIKETHROUGH || c == ChatColor.BOLD || c == ChatColor.ITALIC) getRandomColor();
        return c;
    }
}
