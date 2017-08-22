package es.projectalpha.pa.lobby.cosmetics;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.Cooldown;
import es.projectalpha.pa.core.utils.ItemMaker;
import es.projectalpha.pa.core.utils.Messages;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.PALobby;
import es.projectalpha.pa.lobby.cosmetics.list.AntiGravity;
import es.projectalpha.pa.lobby.cosmetics.list.ExplosiveSheep;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Cosmetic {

    //
    public static final Cosmetic ANTI_GRAVITY = new AntiGravity();
    public static final Cosmetic EXPLOSIVE_SHEEP = new ExplosiveSheep();
    //

    protected final transient PALobby plugin = PALobby.getInstance();
    protected final transient Random r = new Random();

    @Getter private String name;
    @Getter private Material mat;
    @Getter private Cooldown cooldown;

    public Cosmetic(String name, Material mat, int cooldown) {
        this.name = Utils.colorize(name);
        this.mat = mat;
        this.cooldown = new Cooldown(cooldown);
    }

    public void play(PAUser u) {}

    public ItemStack getItemStack() {
        return new ItemMaker(getMat()).setDisplayName(getName()).build();
    }

    protected boolean isInCooldown(PAUser u, String cosmetic) {
        if (cooldown.isCoolingDown(cosmetic)) {
            u.sendMessage(PAData.TOA + "&cEste cosmetico está en cooldown &7(&6" + new Cooldown(0).getTimeLeft(cosmetic) + " segundos&7)");
            return true;
        }
        return false;
    }

    public static boolean useCosmetic(PAUser u, Material m) {
        String name = "ERROR";

        switch (m) {
            case DIAMOND_BARDING:
                ANTI_GRAVITY.play(u);
                name = ANTI_GRAVITY.getName();
                break;
            case WOOL:
                EXPLOSIVE_SHEEP.play(u);
                name = EXPLOSIVE_SHEEP.getName();
                break;
        }
        if (name.equalsIgnoreCase("ERROR")) {
            return false;
        }
        u.sendMessage(Messages.getMessage(Messages.COSMETIC_USE, PAData.LOBBY, "%cosmetic%", name));
        return true;
    }

    protected Entity spawnEntity(Location l, EntityType et) {
        return l.getWorld().spawnEntity(l, et);
    }
}
