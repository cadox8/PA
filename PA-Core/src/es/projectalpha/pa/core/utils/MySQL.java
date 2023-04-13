package es.projectalpha.pa.core.utils;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import es.projectalpha.pa.core.PACore;
import es.projectalpha.pa.core.api.PAUser;
import es.projectalpha.pa.core.cmd.PACmd;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;

/**
 * Objeto para conexiones de MySQL
 *
 * @author Huskehhh base original, Cadiducho y Cadox8 actualización y metodos
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
        //Datos
        PACore.getInstance().getServer().getScheduler().runTaskAsynchronously(PACore.getInstance(), () -> {
            try {
                PreparedStatement statement = openConnection().prepareStatement("SELECT `id` FROM `pa_datos` WHERE `name` = ?");
                statement.setString(1, p.getName());

                ResultSet rs = statement.executeQuery();
                if (!rs.next()) { //No hay filas encontradas, insertar nuevos datos
                    PreparedStatement inserDatos = openConnection().prepareStatement("INSERT INTO `pa_datos` (`name`, `grupo`) VALUES (?, ?)");
                    inserDatos.setString(1, p.getName());
                    inserDatos.setInt(2, 0);
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
                PreparedStatement statementDatos = openConnection().prepareStatement("UPDATE `pa_datos` SET `grupo`=?,`god`=?,`coins`=?," +
                        "`lastConnect`=?,`ip`=?,`nick`=?,`maxPiso`=?,`exp`=?,`lvl`=?,`zeny`=?,`kills`=?,`deaths`=?,`kit`=?, `karma`=? WHERE `name`=?");
                statementDatos.setInt(1, data.getGrupo() != null ? data.getGrupo().getRank() : 0);
                statementDatos.setBoolean(2, data.getGod() == null ? false : data.getGod());
                statementDatos.setInt(3, data.getCoins() == null ? 0 : data.getCoins());
                statementDatos.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
                statementDatos.setString(5, data.getIp() == null ? "" : data.getIp().getAddress().getHostAddress());
                statementDatos.setString(6, data.getNickname() == null ? "" : data.getNickname());
                statementDatos.setInt(7, data.getMaxPiso() == null ? 0 : data.getMaxPiso());
                statementDatos.setInt(8, data.getExp() == null ? 0 : data.getExp());
                statementDatos.setInt(9, data.getLvl() == null ? 0 : data.getLvl());
                statementDatos.setInt(10, data.getZeny() == null ? 0 : data.getZeny());
                statementDatos.setInt(11, data.getKills() == null ? 0 : data.getKills());
                statementDatos.setInt(12, data.getDeaths() == null ? 0 : data.getDeaths());
                statementDatos.setInt(13, data.getKit() == null ? -1 : data.getKit());
                statementDatos.setInt(14, data.getKarma() == null ? 100 : data.getKarma());

                statementDatos.setString(15, u.getName());
                statementDatos.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Ha ocurrido un error guardando los datos de " + u.getName());
                ex.printStackTrace();
            }
        });
    }

    public PAUser.UserData loadUserData(String id) {
        PAUser.UserData data = new PAUser.UserData();
        try {
            PreparedStatement statementDatos = openConnection().prepareStatement("SELECT `timeJoin`,`grupo`,`god`,`coins`,`lastConnect`," +
                    "`maxPiso`,`exp`,`lvl`,`zeny`,`kills`,`deaths`,`kit`,`karma`,`nick` FROM `pa_datos` WHERE `name` = ?");
            statementDatos.setString(1, id);
            ResultSet rsDatos = statementDatos.executeQuery();

            if (rsDatos.next()) {
                int rank = rsDatos.getInt("grupo");
                data.setGrupo(PACmd.Grupo.values()[rank] == null ? PACmd.Grupo.Usuario : PACmd.Grupo.values()[rank]);
                data.setTimeJoin(rsDatos.getLong("timeJoin"));
                data.setGod(rsDatos.getBoolean("god"));
                data.setCoins(rsDatos.getInt("coins"));
                data.setLastConnect(rsDatos.getLong("lastConnect"));

                data.setMaxPiso(rsDatos.getInt("maxPiso"));
                data.setExp(rsDatos.getInt("exp"));
                data.setLvl(rsDatos.getInt("lvl"));
                data.setZeny(rsDatos.getInt("zeny"));
                data.setKills(rsDatos.getInt("kills"));
                data.setDeaths(rsDatos.getInt("deaths"));
                data.setKit(rsDatos.getInt("kit"));
                data.setKarma(rsDatos.getInt("karma"));
                data.setNickname(rsDatos.getString("nick"));
            } else {
                PACore.getInstance().debugLog("No hay datos guardados de " + id);
            }

            //Logros
            PreparedStatement stamentLogros = openConnection().prepareStatement("SELECT `logro` FROM `pa_logros` WHERE `user` = ?");
            stamentLogros.setString(1, id);
            ResultSet rsLogros = stamentLogros.executeQuery();

            ArrayList<Integer> logros = new ArrayList<>();
            while (rsLogros.next()) logros.add(rsLogros.getInt("logro"));
            data.setLogros(logros);

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
                PreparedStatement statement = openConnection().prepareStatement("SELECT `id` FROM `pa_antium` WHERE `name` = ?");
                statement.setString(1, p.getUniqueId().toString());
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) { //No hay filas encontradas, insertar nuevos datos
                    PreparedStatement inserDatos = openConnection().prepareStatement("INSERT INTO `pa_antium` (`name`, `pass`, `email`) VALUES (?, ?, ?)");
                    inserDatos.setString(1, p.getName());
                    inserDatos.setString(2, pass);
                    inserDatos.setString(3, email);
                    inserDatos.executeUpdate();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean login(PAUser u, String inPass) {
        try {
            PreparedStatement statementDatos = openConnection().prepareStatement("SELECT `pass` FROM `pa_antium` WHERE `name` = ?");
            statementDatos.setString(1, u.getName());
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

    public boolean isRegistered(String name) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM `pa_antium` WHERE `name` =?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return false;
    }

    public boolean deleteUserAntium(String name) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("DELETE * FROM `pa_antium` WHERE `name` =?");
            statement.setString(1, name);
            statement.executeQuery();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return false;
    }

    public boolean changePassword(String name, String newPassword) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("UPDATE `pa_antium` SET `pass`=? WHERE `name` =?");
            statement.setString(1, ProtectPass.encodePass(newPassword));
            statement.setString(2, name);
            statement.executeQuery();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return false;
    }

    public boolean setEmail(String name, String email) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("UPDATE `pa_antium` SET `email`=? WHERE `name` =?");
            statement.setString(1, email);
            statement.setString(2, name);
            statement.executeQuery();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return false;
    }

    //Logros
    public void addLogro(PAUser u, int logro) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM `pa_logros` WHERE `name` =? AND `logro`=?");
            statement.setString(1, u.getName());
            statement.setInt(2, logro);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) { //No hay filas encontradas, insertar nuevos datos
                PreparedStatement logros = openConnection().prepareStatement("INSERT INTO `pa_logros` (`name`, `logro`) VALUES (?, ?)");
                logros.setString(1, u.getName());
                logros.setInt(2, logro);
                logros.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
    }

    public boolean chechLogro(PAUser u, int logro) {
        try {
            PreparedStatement statement = openConnection().prepareStatement("SELECT * FROM `pa_logros` WHERE `name` =? AND `logro`=?");
            statement.setString(1, u.getName());
            statement.setInt(2, logro);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) return false;
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.Level.SEVERE, e.toString());
        }
        return true;
    }
}
