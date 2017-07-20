package es.projectalpha.pa.antium.manager;

import es.projectalpha.pa.antium.PAAntium;
import es.projectalpha.pa.core.api.PAData;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.utils.MySQL;
import es.projectalpha.pa.core.utils.ProtectPass;
import lombok.Getter;

import java.util.ArrayList;

public class PassManager {

    @Getter
    private ArrayList<PAUser> logged;

    private PAAntium plugin;
    private MySQL mysql;

    public PassManager(PAAntium instance) {
        this.plugin = instance;
        logged = new ArrayList<>();
        mysql = plugin.getMysql();
    }

    public void register(PAUser u, String pass, String pass2) {
        if (isLogged(u)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&cYa estas loggeado");
            return;
        }

        if (!pass.equalsIgnoreCase(pass2)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&cLas contraseñas no coinciden");
            return;
        }
        String encryptedPass = ProtectPass.encodePass(pass);
        mysql.register(u, encryptedPass, "");
        u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Registrado correctamente. &Contraseña: &c" + pass + " &2Contraseña encriptada: &c" + encryptedPass);
    }

    public void login(PAUser u, String pass) {
        if (isLogged(u)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&cYa estas loggeado");
            return;
        }
        if (mysql.login(u, pass)) {
            u.sendMessage(PAData.ANTIUM.getPrefix() + "&3Has entrado correctamente");
            logged.add(u);
            return;
        }
        u.sendMessage(PAData.ANTIUM.getPrefix() + "&cContraseña erronea");
    }

    public void removeLogged(PAUser u) {
        if (isLogged(u)) logged.remove(u);
    }

    public boolean isLogged(PAUser u) {
        return logged.contains(u);
    }

    @Deprecated
    public void updateEmail(PAUser u, String email) {
        //DISABLED
    }
}
