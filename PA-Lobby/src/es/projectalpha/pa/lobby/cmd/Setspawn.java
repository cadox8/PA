package es.projectalpha.pa.lobby.cmd;

import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.lobby.files.Files;

public class Setspawn extends PACmd {

    public Setspawn(){
        super("setspawn", Grupo.Admin, "ss", "spawnset");
    }

    private Files files;

    public void run(PAUser user, String label, String... args) {
        if(args.length >=0){
            files.getConfig().set("spawn.point", Utils.locationToString(user.getLoc()));
            user.sendMessage("Illo, punto ponio.");
        }
    }

}
