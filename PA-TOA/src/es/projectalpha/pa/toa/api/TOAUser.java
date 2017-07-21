package es.projectalpha.pa.toa.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.core.utils.Title;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.kits.Race;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class TOAUser extends PAUser {

    private TOA plugin = TOA.getInstance();

    @Getter
    @Setter
    private TOAUserData toaUserData;

    public TOAUser(UUID uuid) {
        super(uuid);
    }


    public void save() {

    }

    private void setCity() {
        ScoreboardUtil board = new ScoreboardUtil(PAData.RG.getOldPrefix(), "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (plugin.getGm().getInTower().contains(TOA.getPlayer(getPlayer()))) {
                    board.reset();
                    cancel();
                    return;
                }
                String kit = Race.parseRace(getToaUserData().getKit()) == null ? "Ninguna" : Race.parseRace(getToaUserData().getKit()).getName();

                board.setName(PAData.RG.getOldPrefix());
                board.text(4, "Zenys: §b" + getToaUserData().getZeny());
                board.text(3, "§e ");
                board.text(2, "Race: §e" + kit);
                board.text(1, "§e ");
                board.text(0, PACore.getIP());
                if (getPlayer() != null) board.build(getPlayer());
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void sendToTower() {
        teleport(plugin.getAm().getTower());
        plugin.getGm().joinTower(this);
    }

    public void sendToCity() {
        plugin.getGm().leaveTower(this);
        teleport(plugin.getAm().getCity());
        setCity();
    }


    public void death() {
        getPlayer().setGameMode(GameMode.SPECTATOR);
        getPlayer().eject();
        Title.sendTitle(getPlayer(), 0, 3, 0, "&cHas muerto", "&2Reapareceras en 5 segundos");
        plugin.getGm().leaveTower(this);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> respawn(), 100);
    }

    private void respawn() {
        int zenys = (int) (getToaUserData().getZeny() * 0.02);

        getPlayer().setGameMode(GameMode.ADVENTURE);
        sendToCity();
        plugin.getHealth().ajustHealth(this);
        getToaUserData().setZeny(getToaUserData().getZeny() - zenys);
        sendMessage(PAData.TOA.getPrefix() + "&2Has perdido &6" + zenys + "&2 zenys");
    }

    public void setRace(Race race) {
        getPlayer().getInventory().clear();
        race.setItems(getPlayer());
        sendToCity();
        plugin.getHealth().setHealth(this, race.getHealth());
        Title.sendTitle(getPlayer(), 0, 3, 0, "", "&cTu aventura comienza ahora");
    }


    @Data
    public static class TOAUserData {
        Integer piso = 0;
        Integer maxPiso = 0;

        Integer exp = 0;
        Integer lvl = 1;

        Integer zeny = 0;

        Integer kills = 0;
        Integer deaths = 0;

        Integer kit = -1;

        public TOAUserData() {
        }
    }
}
