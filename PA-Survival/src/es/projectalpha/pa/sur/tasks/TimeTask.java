package es.projectalpha.pa.sur.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.files.Files;
import es.projectalpha.pa.sur.manager.Balance;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
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
                    plugin.getImp().clear();
                    Files.user.getStringList("Users").forEach(e->{
                        Files.user.set("Users." + e + ".pimp", false);
                        Files.saveFiles();
                    });
                    DecimalFormat df = new DecimalFormat("#.00");
                    Files.user.set("recaudado", Files.user.getInt("recaudado") - Double.valueOf(df.format((Files.user.getInt("recaudado") * 0.1))));
                    Files.user.set("loteria", Files.user.getInt("loteria") + Double.valueOf(df.format((Files.user.getInt("recaudado") * 0.1))));
                }
                break;
            case 18:
                if(min == 30 && seg == 0){
                    System.out.println(hora + ":" + min + ":" + seg);
                    int rd = new Random().nextInt(9999);
                    Utils.broadcastMsg("&aHora de la lotería, el número ganador es: &6" + rd + ".");

                    Files.user.getStringList("Users").forEach(p ->{
                        Files.user.getStringList("Users." + p + ".bol").forEach(b ->{
                            if(Integer.parseInt(b) == rd){
                                Utils.broadcastMsg("&aEl ganador de la lotería es " + p + ". Ha ganado " + Files.user.getInt("loteria") + "$");
                                balance.addBalace(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p)), Files.user.getInt("loteria"));
                                Files.user.set("loteria", 0);
                                Files.saveFiles();
                                return;
                            }else{
                                Utils.broadcastMsg("&cNo ha habido ningun ganador hoy, mañana habra otra oportunidad.");
                                Files.user.set("Users." + p + ".", "bol");
                                Files.user.set("Users." + p + ".apos", 0);
                            }
                        });
                    });
                }
                break;
        }
    }
}