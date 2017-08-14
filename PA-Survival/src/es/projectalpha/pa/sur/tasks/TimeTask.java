package es.projectalpha.pa.sur.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeTask extends BukkitRunnable {

    Calendar cal = Calendar.getInstance();
    int hora,horaf;
    SimpleDateFormat sdf = new SimpleDateFormat("HH");

    public void run() {
        cal = new GregorianCalendar();
        hora = cal.get(Calendar.HOUR_OF_DAY);
        if(hora != 6) return;

    }


}