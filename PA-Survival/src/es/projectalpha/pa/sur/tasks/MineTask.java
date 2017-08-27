package es.projectalpha.pa.sur.tasks;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.PASurvival;
import es.projectalpha.pa.sur.api.SurvivalUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MineTask extends BukkitRunnable { //Cause it's mine.

    private PASurvival plugin;

    @Getter private HashMap<SurvivalUser, Integer> blocks;
    @Getter @Setter private boolean working;

    private int count;

    public MineTask(PASurvival instance){
        this.plugin = instance;
        blocks = new HashMap<>();
        blocks.clear();
        setWorking(false);
    }

    public void run() {
        if (count < 0) blocks.keySet().forEach(u -> u.sendActionBar(PAData.SURVIVAL.getPrefix() + "&2Tiempo restante: &c" + count + " &2segundos"));

        switch (count) {
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                blocks.keySet().forEach(u -> u.sendMessage(PAData.SURVIVAL.getPrefix() + "&2Quedan &c" + count + " &2segundos"));
            case 0:
                checkWinner();
                cancel();
                break;
        }
        count--;
    }

    private void checkWinner() {
        SurvivalUser[] users = reorder().keySet().toArray(new SurvivalUser[reorder().size()]);
        if (users.length == 0) return;

        Utils.broadcastMsg("------------------------");
        Utils.broadcastMsg("");
        if (blocks.get(users[0]) != null) Utils.broadcastMsg("1º &c" + users[0].getName() + "&7: &6" + blocks.get(users[0]) + " bloques.");
        if (blocks.get(users[1]) != null) Utils.broadcastMsg("2º &c" + users[1].getName() + "&7: &6" + blocks.get(users[1]) + " bloques.");
        if (blocks.get(users[2]) != null) Utils.broadcastMsg("3º &c" + users[2].getName() + "&7: &6" + blocks.get(users[2]) + " bloques.");
        Utils.broadcastMsg("");
        Utils.broadcastMsg("------------------------");

        blocks.keySet().forEach(p -> blocks.remove(p));
        blocks.clear(); //Security Reasons
        setWorking(false);
    }

    private <K, V extends Comparable<? super V>> Map<SurvivalUser, Integer> reorder() {
        return blocks.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void setTime(int minutes) {
        count = 20 * 60 * minutes;
    }
}