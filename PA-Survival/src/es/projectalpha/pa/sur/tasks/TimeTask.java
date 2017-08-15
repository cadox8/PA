package es.projectalpha.pa.sur.tasks;

import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class TimeTask extends BukkitRunnable {

    private Calendar cal = Calendar.getInstance();
    private int hora,min,seg;
    private Files files = new Files();
    private Economy eco;
    private PASurvival plugin;
    private Balance balance;

    public void run() {
        cal = new GregorianCalendar();
        hora = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        seg = cal.get(Calendar.SECOND);
        Bukkit.getOnlinePlayers().forEach(p -> balance.saveBalance(PASurvival.getPlayer(p)));
        if(hora != 6 && min !=0 && seg != 0) return;
        System.out.println(hora + ":" + min + ":" + seg);
        files.getUser().getList("Users.").forEach(p ->{
                balance.saveBalance(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p.toString())));
                balance.removeBalance(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p.toString())), eco.getBalance(plugin.getServer().getOfflinePlayer(p.toString()))*0.01);
                balance.saveBalance(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p.toString())));
        });
        Bukkit.getOnlinePlayers().forEach(u -> u.sendMessage(ChatColor.GREEN + "Hora de los impuestos, se te ha quitado 1% de tu dinero, o lo que es lo mismo " + eco.getBalance(plugin.getServer().getPlayer(u.toString())) * 0.01));
    }


}