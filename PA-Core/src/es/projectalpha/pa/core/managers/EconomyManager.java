package es.projectalpha.pa.core.managers;

import es.projectalpha.pa.core.api.PAUser;

public class EconomyManager {

    private PAUser user;
    private DataManager dataManager;

    public EconomyManager(PAUser user){
        this.user = user;
        this.dataManager = new DataManager(user);
    }

    public boolean addMoney(double amount){
        return dataManager.setObject("money", dataManager.getMoney() + amount);
    }

    public boolean removeMoney(double amount){
        if (dataManager.getMoney() - amount <= 0){
            return dataManager.setObject("money", 0);
        }
        return dataManager.setObject("money", dataManager.getMoney() - amount);
    }
}
