package es.projectalpha.pa.sur.tasks;

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

    private int rd = new Random().nextInt(9999);

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
            balance.saveBalance(p);
            //if (!plugin.getManager().isInPvP(p.getPlayer())) p.sendMessage(PAData.SURVIVAL.getPrefix() + ChatColor.DARK_GREEN + "Ya no estás en pvp, puedes desconectarte.");
        });

        switch(hora){
            case 6:
                if(min == 0 && seg == 0){
                    System.out.println(hora + ":" + min + ":" + seg);
                    files.getUser().getStringList("Users.").forEach(p ->{
                        balance.saveBalance(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p)));
                        files.getUser().set("recaudado", files.getUser().getInt("recaudado") + (files.getUser().getInt("Users." + p + ".money")*0.01));
                        files.getUser().set("recaudado", files.getUser().getInt("recaudado") - (files.getUser().getInt("recaudado")*0.1));
                        files.getUser().set("loteria", files.getUser().getInt("loteria") + (files.getUser().getInt("recaudado")*0.1));
                        balance.removeBalance(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p)), eco.getBalance(plugin.getServer().getOfflinePlayer(p))*0.01);
                        balance.saveBalance(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p)));
                    });
                    Bukkit.getOnlinePlayers().forEach(u ->
                            u.sendMessage(ChatColor.GREEN + "Hora de los impuestos, se te ha quitado 1% de tu dinero, o lo que es lo mismo " + eco.getBalance(u.getPlayer()) * 0.01));
                }
                break;
            case 18:
                if(min == 0 && seg == 0){
                    Utils.broadcastMsg("&aHora de la lotería, los números ganadores son: &6" + rd + ".");

                    files.getUser().getStringList("Users.").forEach(p ->{
                        files.getUser().getStringList("Users." + p + ".bol").forEach(b ->{
                            if(Integer.parseInt(b) == rd){
                                Utils.broadcastMsg("&aEl ganador de la lotería es " + p + ". Ha ganado " + files.getUser().getInt("loteria") + "$");
                                balance.addBalace(PASurvival.getPlayer(plugin.getServer().getOfflinePlayer(p)), files.getUser().getInt("loteria"));
                                files.getUser().set("loteria", 0);
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