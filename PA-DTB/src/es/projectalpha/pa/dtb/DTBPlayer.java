package es.projectalpha.pa.dtb;

import es.projectalpha.pa.core.api.PAUser;

import java.util.UUID;

public class DTBPlayer extends PAUser {

    private DTB plugin = DTB.getInstance();

    public DTBPlayer(UUID uuid){
        super(uuid);
    }

    public void jump(){
        teleport(plugin.getArenaManager().getJump());

        sendMessagePrefix("&6Te toca saltar");
    }

    public void backtoLobby(){
        teleport(plugin.getArenaManager().getLobby());
    }
}
