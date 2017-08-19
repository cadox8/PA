package es.projectalpha.pa.sur.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class TimeTask extends BukkitRunnable {

    private PASurvival plugin;

    private Calendar cal = Calendar.getInstance();
    private int hora,min,seg;

    private Files files;
    private Economy eco;
    private Balance balance = new Balance();

    public TimeTask(PASurvival instance){
        this.plugin = instance;
        files = plugin.getFiles();
        eco = plugin.getVault();
    }

    public void run() {
        cal = new GregorianCalendar();
        hora = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        seg = cal.get(Calendar.SECOND);

        PASurvival.players.forEach(p -> {
            if(p == null) return;
            if (plugin.getManager().getPvpc().getTimeLeft(p.getName()) == 1){
                p.sendMessage(Utils.colorize(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + "Ya no estás en pvp, puedes desconectarte."));
                plugin.getManager().removePvP(p.getPlayer());
            }
        });

        switch(hora){
            case 6:
                if(min == 0 && seg == 0) {
                    System.out.println(hora + ":" + min + ":" + seg);
                    Files.user.getStringList("Users").forEach(e->{
                        Files.user.set("Users." + e + ".pimp", false);
                        Files.saveFiles();
                    });
                }
                break;
            case 18:
                if(min == 0 && seg == 0){
                    int rd = new Random().nextInt(9999);
                    Utils.broadcastMsg("&aHora de la lotería, los números ganadores son: &6" + rd + ".");

                    Files.user.getStringList("Users.").forEach(p ->{
                        Files.user.getStringList("Users." + p + ".bol").forEach(b ->{
                            if(Integer.parseInt(b) == rd){
                                Utils.broadcastMsg("&aEl ganador de la lotería es " + p + ". Ha ganado " + Files.user.getInt("loteria") + "$");
                                balance.addBalace(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p)), Files.user.getInt("loteria"));
                                Files.user.set("loteria", 0);
                                Files.saveFiles();
                                return;
                            }
                        });
                    });
                    Utils.broadcastMsg("&cNo ha habido ningún ganador hoy, mañana habrá otra oportunidad.");
                }
                break;
        }
    }


}