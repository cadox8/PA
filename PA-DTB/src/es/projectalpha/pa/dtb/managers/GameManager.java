package es.projectalpha.pa.dtb.managers;

import es.projectalpha.pa.dtb.DTB;
import es.projectalpha.pa.dtb.DTBPlayer;
import lombok.Getter;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private DTB plugin;

    @Getter private ArrayList<DTBPlayer> playing;
    @Getter private ArrayList<DTBPlayer> spect;
    @Getter private DTBPlayer jumper = null;

    @Getter private HashMap<DTBPlayer, Integer> score;

    public GameManager(DTB instance){
        this.plugin = instance;
        playing = new ArrayList<>();
        spect = new ArrayList<>();
        score = new HashMap<>();
    }

    public void initGame(){
        playing.forEach(u -> {
            score.put(u, 0);
            u.sendMessagePrefix("&6El juego ha empezado");
            u.sendSound(Sound.ENTITY_PLAYER_LEVELUP);
        });
    }

    public void addToGame(DTBPlayer u){
        if (playing.contains(u)) return;
        playing.add(u);
        u.backtoLobby();
    }

    public void removeFromGame(DTBPlayer u){
        if (!playing.contains(u)) return;
        playing.remove(u);
        u.backtoLobby();
        spect.add(u);
    }

    public void timeToJump(DTBPlayer u){
        jumper = u;
        u.jump();
    }

    public void addPoints(DTBPlayer u, int points){
        score.put(u, score.get(u) + points);
    }

    public void removePoints(DTBPlayer u, int points){
        if (score.get(u) - points <= 0) points = score.get(u);
        score.put(u, score.get(u) - points);
    }
}
