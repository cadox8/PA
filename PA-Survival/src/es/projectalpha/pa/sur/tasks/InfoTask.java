package es.projectalpha.pa.sur.tasks;

import es.projectalpha.pa.sur.PASurvival;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class InfoTask extends BukkitRunnable {

    private ArrayList<String> msgs;
    private String prefix = "[&6ProjectAlpha&r]";

    public InfoTask() {
        msgs = new ArrayList<>();

        msgs.add("&aRecuerda pasarte por nuestro foro para estar informado. &7http://projectalpha.es/foro");
        msgs.add("&aTenemos un Teamspeak propio para el servidor. &7ip: ts.projectalpha.es");
        msgs.add("&aRecuerda seguirnos en Twitter para enterarte de las novedades más rápido. &7Twitter:@ProjectAlphaSv");
        msgs.add("&aPuedes votarnos en 40servidoresmc, recibirás una recompensa a cambio. Para votar haz &6/votar40 &ay dale click al link que aparece.");
        msgs.add("&aEste servidor dispone de dynmap. Para acceder a el haz click en el siguiente link: ://projectalpha.es:8123");
        msgs.add("&aSi te gusta el servidor puedes donar en nuestra tienda para mantener el servidor más tiempo. http://projectalpha.buycraft.net/");
        msgs.add("&aEstás jugando en ProjectAlpha, si te gusta el servidor compartelo con tus amigos.");
    }

    public void run() {
        PASurvival.players.forEach(u -> u.sendMessage(prefix + msgs.get(new Random().nextInt(msgs.size()))));
    }
}
