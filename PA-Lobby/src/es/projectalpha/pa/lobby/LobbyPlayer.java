package es.projectalpha.pa.lobby;

import es.projectalpha.pa.core.api.PAUser;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class LobbyPlayer extends PAUser {

    public LobbyPlayer(UUID uuid){
        super(uuid);
    }

    public LobbyPlayer(OfflinePlayer p){
        this(p.getUniqueId());
    }


}
