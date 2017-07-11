package es.projectalpha.pa.toa.api;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.toa.TOA;
import es.projectalpha.pa.toa.kits.Kit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;

import java.net.InetSocketAddress;
import java.util.UUID;

public class TOAUser extends PAUser {

    private TOA plugin = TOA.getInstance();

    @Getter @Setter private UserData userData;

    public TOAUser(UUID uuid) {
        super(uuid);
    }


    public void sendToMap(){

    }

    public void sendToCity(){

    }

    public void save() {

    }

    public void setKit(Kit kit){
        getPlayer().getInventory().clear();
        kit.setItems(getPlayer());
    }


    @Data
    public static class UserData {
        Integer piso = 0;
        Integer maxPiso = 0;

        Integer exp = 0;
        Integer lvl = 1;

        Integer zeny = 0;

        Integer kills = 0;
        Integer deaths = 0;

        public UserData() {}
    }
}
