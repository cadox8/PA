package es.projectalpha.pa.core.utils;

import com.mysql.jdbc.CommunicationsException;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

/**
 * Objeto para conexiones de MySQL
 *
 * @author Huskehhh base original, Cadox8 actualización y metodos
 */
public class MySQL {

    private final String user, database, password, port, hostname;
    protected Connection connection;

    public MySQL(String hostname, String database, String username, String password) {
        this.hostname = hostname;
        this.port = "3306";
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean closeConnection() throws SQLException {
        if (connection == null) {
            return false;
        }
        connection.close();
        return true;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
        return connection;
    }

    // -----------------
    public void setupTable(Player p) {
        PACore.getInstance().getServer().getScheduler().runTaskAsynchronously(PACore.getInstance(), () -> {
            try {
                PreparedStatement statement = openConnection().prepareStatement("SELECT `id` FROM `pa_datos` WHERE `uuid` = ?");
                statement.setString(1, p.getUniqueId().toString());
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) { //No hay filas encontradas, insertar nuevos datos
                    PreparedStatement inserDatos = openConnection().prepareStatement("INSERT INTO `pa_datos` (`uuid`, `name`, `grupo`) VALUES (?, ?, ?)");
                    inserDatos.setString(1, p.getUniqueId().toString());
                    inserDatos.setString(2, p.getName());
                    inserDatos.setInt(3, 0);
                    inserDatos.executeUpdate();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void saveUser(PAUser u) {
        PACore.getInstance().getServer().getScheduler().runTaskAsynchronously(PACore.getInstance(), () -> {
            PAUser.UserData data = u.getUserData();
            try {
                PreparedStatement statementDatos = openConnection().prepareStatement("UPDATE `pa_datos` SET `grupo`=?,`god`=?,`coins`=?,`lastConnect`=?,`ip`=?,`nick`=? WHERE `uuid`=?");
                statementDatos.setInt(1, data.getGrupo() != null ? data.getGrupo().getRank() : 0);
                statementDatos.setBoolean(2, data.getGod() == null ? false : data.getGod());
                statementDatos.setInt(3, data.getCoins() == null ? 0 : data.getCoins());
                statementDatos.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
                statementDatos.setString(5, data.getIp() == null ? "" : data.getIp().getAddress().getHostAddress());
                statementDatos.setString(6, data.getNickname() == null ? "" : data.getNickname());
                statementDatos.setString(7, u.getUuid().toString());
                statementDatos.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Ha ocurrido un error guardando los datos de " + u.getName());
                ex.printStackTrace();
            }
        });
    }

    public PAUser.UserData loadUserData(UUID id) {
        PAUser.UserData data = new PAUser.UserData();
        try {
            PreparedStatement statementDatos = openConnection().prepareStatement("SELECT `timeJoin`,`grupo`,`god`,`coins`,`lastConnect` FROM `pa_datos` WHERE `uuid` = ?");
            statementDatos.setString(1, id.toString());
            ResultSet rsDatos = statementDatos.executeQuery();

            if (rsDatos.next()) {
                int rank = rsDatos.getInt("grupo");
                data.setGrupo(PACmd.Grupo.values()[rank] == null ? PACmd.Grupo.Usuario : PACmd.Grupo.values()[rank]);
                data.setTimeJoin(rsDatos.getLong("timeJoin"));
                data.setGod(rsDatos.getBoolean("god"));
                data.setCoins(rsDatos.getInt("coins"));
                data.setLastConnect(rsDatos.getLong("lastConnect"));
            }
        } catch (CommunicationsException ex) {
            PACore.getInstance().debugLog(ex.toString());
            try {
                closeConnection();
                openConnection();
                return loadUserData(id);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error cargando los datos de " + id);
            ex.printStackTrace();
        }
        return data;
    }


    //Sólo para Antium
    public void register(PAUser u, String pass, String email) {
        PACore.getInstance().getServer().getScheduler().runTaskAsynchronously(PACore.getInstance(), () -> {
            Player p = u.getPlayer();
            try {
                PreparedStatement statement = openConnection().prepareStatement("SELECT `id` FROM `pa_antium` WHERE `uuid` = ?");
                statement.setString(1, p.getUniqueId().toString());
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) { //No hay filas encontradas, insertar nuevos datos
                    PreparedStatement inserDatos = openConnection().prepareStatement("INSERT INTO `pa_antium` (`uuid`, `name`, `pass`, `email`) VALUES (?, ?, ?, ?)");
                    inserDatos.setString(1, p.getUniqueId().toString());
                    inserDatos.setString(2, p.getName());
                    inserDatos.setString(3, pass);
                    inserDatos.setString(4, email);
                    inserDatos.executeUpdate();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean login(PAUser u, String inPass) {
        PAUser.UserData data = new PAUser.UserData();
        try {
            PreparedStatement statementDatos = openConnection().prepareStatement("SELECT `pass` FROM `pa_antium` WHERE `uuid` = ?");
            statementDatos.setString(1, u.getUuid().toString());
            ResultSet rsDatos = statementDatos.executeQuery();

            if (rsDatos.next()) {
                String pass = ProtectPass.decodePass(rsDatos.getString("pass"));
                return pass.equalsIgnoreCase(inPass);
            }
        } catch (CommunicationsException ex) {
            PACore.getInstance().debugLog(ex.toString());
            try {
                closeConnection();
                openConnection();
                return login(u, inPass);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("Ha ocurrido un error cargando los datos de " + u.toString());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isRegistered(PAUser u) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM `pa_antium` WHERE `uuid` =?");
            statement.setString(1, u.getUuid().toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) return false;
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return false;
    }

    public boolean deleteUserAntium(PAUser u) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("DELETE * FROM `pa_antium` WHERE `uuid` =?");
            statement.setString(1, u.getUuid().toString());
            statement.executeQuery();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return false;
    }
}
