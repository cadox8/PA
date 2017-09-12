package es.projectalpha.pa.sur.cmd;

import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import es.projectalpha.pa.core.utils.Utils;
import es.projectalpha.pa.sur.events.PlayerEvents;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

public class GGCMD extends PACmd {

    public GGCMD() {
        super("gg", Grupo.Usuario, "ez", "noob");
    }

    public void run(PAUser u, String label, String... args) {
        if (u.getName().equalsIgnoreCase("SrJohn")) {
            Utils.broadcastMsg(Utils.colorize(PerfectMSGs.BV.getMsg()));
        } else {
            PerfectMSGs msg = PerfectMSGs.values()[new Random().nextInt(PerfectMSGs.values().length)];
            Utils.broadcastMsg(Utils.colorize(msg.getMsg()));
            if (PlayerEvents.death) {
                u.getUserData().setKarma(u.getUserData().getKarma() + msg.getKarma());
                u.save();
                u.sendMessage(PAData.CORE.getPrefix() + "&6Has recibido &c" + msg.getKarma() + " &6de Karma &7(&c" + u.getUserData().getKarma() + "&7)");
            }
        }
    }

    @AllArgsConstructor
    public enum PerfectMSGs { //ni de coña xdd
        BV(1, "&dI'm bronze V"),
        BV2(2, "&cgg ez win fkn noob"),
        BV3(1, "&b&lWOTOFOK GENTE"),
        BV4(1, "&aDONT BAN YASUO!");

        @Getter
        private int karma;
        @Getter private String msg;
    }
}
