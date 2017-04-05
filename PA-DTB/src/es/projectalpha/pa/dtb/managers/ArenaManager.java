package es.projectalpha.pa.dtb.managers;

import es.projectalpha.pa.core.utils.CuboidZone;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.dtb.DTB;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class ArenaManager {

    private DTB plugin;

    public ArenaManager(DTB instance){
        this.plugin = instance;
    }

    public Location getJump(){
        return Utils.stringToLocation(plugin.getConfig().getString("jumpLoc"));
    }

    public Location getLobby(){
        return Utils.stringToLocation(plugin.getConfig().getString("lobbyLoc"));
    }

    public CuboidZone getFallArea(){
        @NonNull Block b = Utils.cuboidToLocation(plugin.getConfig().getString("fallArena"), 0).getBlock();
        @NonNull Block b1 = Utils.cuboidToLocation(plugin.getConfig().getString("fallArena"), 1).getBlock();
        return new CuboidZone(b, b1);
    }
}
