package es.projectalpha.pa.toa.api;

import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.ScoreboardUtil;
import es.projectalpha.pa.core.utils.Title;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.races.Race;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

public class TOAUser extends PAUser {

    private TOA plugin = TOA.getInstance();

    public TOAUser(String name) {
        super(name);
    }

    public void reset() {
        save();
        TOA.users.remove(this);
        TOA.getPlayer(plugin.getServer().getOfflinePlayer(getName()));
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
                String kit = Race.parseRace(getUserData().getKit()) == null ? "Ninguna" : Race.parseRace(getUserData().getKit()).getName();

                board.setName(PAData.RG.getOldPrefix());
                board.text(4, "Zenys: §b" + getUserData().getZeny());
                board.text(3, "§e ");
                board.text(2, "Raza: §e" + kit);
                board.text(1, "§e ");
                board.text(0, PACore.getIP());
                if (getPlayer() != null) board.build(getPlayer());
            }
        }.runTaskTimer(plugin, 1, 20);
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

        plugin.getServer().getScheduler().runTaskLater(plugin, this::respawn, 100);
    }

    private void respawn() {
        int zenys = (int) (getUserData().getZeny() * 0.02);

        getPlayer().setGameMode(GameMode.ADVENTURE);
        sendToCity();
        plugin.getHealth().ajustHealth(this);
        getUserData().setZeny(getUserData().getZeny() - zenys);
        sendMessage(PAData.TOA.getPrefix() + "&2Has perdido &6" + zenys + "&2 zenys");
    }

    private int count = 0;
    public void setRace(Race race) {
        if (!race.enabled()) {
            sendMessage(PAData.TOA.getPrefix() + "&cEsta clase no está disponible por el momento");
            return;
        }

        getPlayer().getInventory().clear();
        race.setItems(getPlayer());
        teleport(race.spawn());
        plugin.getHealth().setHealth(this, race.getHealth());
        Title.sendTitle(getPlayer(), 0, 3, 0, "", "&cTu aventura comienza ahora");
        getUserData().setKit(race.getRaceType().getId());
        reset();

        if (getUserData().getKit() == -1) {
            getUserData().setKit(race.getRaceType().getId());
            reset();
        }
    }
}
