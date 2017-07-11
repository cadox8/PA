package es.projectalpha.pa.toa.manager;

import es.projectalpha.pa.toa.api.TOAUser;

public class Experience {

    private static final int BASE_XP = 500;

    private TOAUser user;
    private TOAUser.UserData ud;

    public Experience(TOAUser user) {
        this.user = user;
        ud = user.getUserData();
    }

    public void addExp(int amount) {
        ud.setExp(ud.getExp() + amount);
        ajustLvL();
    }

    public void remExp(int amount) {
        ud.setExp(ud.getExp() - amount);
    }

    public void addLvL(int lvl) {
        ud.setLvl(ud.getLvl() + lvl);
    }

    private void ajustLvL() {
        if (ud.getExp() >= (BASE_XP * ud.getLvl())) {
            addLvL(1);
            remExp(BASE_XP * ud.getLvl());
            user.save();
            ajustLvL();
        }
    }
}
