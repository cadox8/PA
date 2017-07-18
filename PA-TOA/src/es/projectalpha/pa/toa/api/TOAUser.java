package es.projectalpha.pa.toa.api;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Title;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.kits.Kit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.UUID;

public class TOAUser extends PAUser {

    private TOA plugin = TOA.getInstance();

    @Getter @Setter private TOAUserData toaUserData;

    public TOAUser(UUID uuid) {
        super(uuid);
    }


    public void save() {

    }


    public void sendToTower(){
        teleport(plugin.getAm().getTower());
        plugin.getGm().joinTower(this);
    }

    public void sendToCity(){
        teleport(plugin.getAm().getCity());
    }


    public void death() {
        getPlayer().setGameMode(GameMode.SPECTATOR);
        getPlayer().eject();
        Title.sendTitle(getPlayer(), 0, 3, 0, "&cHas muerto", "&2Reapareceras en 5 segundos");
        plugin.getGm().leaveTower(this);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> respawn(), 100);
    }

    private void respawn() {
        getPlayer().setGameMode(GameMode.ADVENTURE);
        sendToCity();
        int zenys = (int)(getToaUserData().getZeny() * 0.02);

        getToaUserData().setZeny(getToaUserData().getZeny() - zenys);
        sendMessage(PAData.TOA.getPrefix() + "&2Has perdido &6" + zenys + "&2 zenys");
    }

    public void setKit(Kit kit){
        getPlayer().getInventory().clear();
        kit.setItems(getPlayer());
        sendToCity();
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

        public TOAUserData() {}
    }
}
