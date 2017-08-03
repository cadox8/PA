package es.projectalpha.pa.rage.manager;

import es.projectalpha.pa.core.utils.GameState;
import es.projectalpha.pa.rage.RageGames;
import es.projectalpha.pa.rage.api.RagePlayer;
import es.projectalpha.pa.rage.tasks.LobbyTask;
import es.projectalpha.pa.rage.utils.ScoreComparator;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class GameManager {

    private RageGames plugin;

    @Getter private ArrayList<RagePlayer> playing = new ArrayList<>();
    @Getter private HashMap<RagePlayer, Integer> score = new HashMap<>();
    @Getter private ArrayList<RagePlayer> top = new ArrayList<>();

    @Getter @Setter private boolean checkStart = true;

    public GameManager(RageGames instance) {
        this.plugin = instance;
    }

    public void checkStart() {
        if (checkStart && playing.size() >= plugin.getAm().getMinPlayers()) {
            checkStart = false;
            new LobbyTask(plugin).runTaskTimer(plugin, 0, 20);
        }
    }

    public void addPlayerToGame(RagePlayer player) {
        if (playing.contains(player)) {
            playing.remove(player);
            playing.add(player);
        } else {
            playing.add(player);
        }
    }

    public void removePlayerFromGame(RagePlayer p) {
        playing.remove(p);
    }

    public void resetPoint(RagePlayer u) {
        score.put(u, 0);
    }

    public void addPoint(RagePlayer u, int v) {
        score.put(u, score.get(u) + v);
    }

    public void removePoint(RagePlayer u, int v) {
        if (score.get(u) != 0) {
            int pf = score.get(u) - v;
            score.put(u, pf);
        }
    }


    public boolean acceptPlayers() {
        return GameState.getState() == GameState.LOBBY;
    }

    public boolean isInLobby() {
        return GameState.getState() == GameState.LOBBY;
    }

    public void reorder() {
        if (!top.isEmpty()) top.clear();
        List<Integer> list = new ArrayList<>(score.values());
        Collections.sort(list, Collections.reverseOrder());

        score.keySet().forEach(k -> list.subList(0, playing.size() < 7 ? playing.size() : 7).forEach(v -> {
            if (score.get(k).equals(v) && !top.contains(k)) top.add(k);
        }));
    }

    public <K, V extends Comparable<? super V>> Map<RagePlayer, Integer> reorder2() {
        return score.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public SortedSet reorder3() {
        SortedSet sorted = new TreeSet<>(new ScoreComparator());
        sorted.addAll(score.values());
        return sorted;
    }
}
