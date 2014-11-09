package io.anw.Signs.Utils.SQL;

import io.anw.AuroraUtils.DatabaseConnection;
import io.anw.Signs.Main;
import io.anw.Signs.Utils.LoggingUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {

    private static DatabaseManager instance = new DatabaseManager();
    public static DatabaseManager getInstance() {
        return instance;
    }

    private static DatabaseConnection connection = new DatabaseConnection(
            Main.getInstance().getConfig().getString("MySQL.Address"),
            Main.getInstance().getConfig().getString("MySQL.Database"),
            Main.getInstance().getConfig().getString("MySQL.Username"),
            Main.getInstance().getConfig().getString("MySQL.Password"),
            Main.getInstance().getDescription().getName()
    );


    public void checkDatabase() {
        try {
            Connection con = connection.getConnection();
            con.createStatement().execute("CREATE TABLE IF NOT EXISTS signData(serverName VARCHAR(255), players INTEGER, maxPlayers INTEGER, state VARCHAR(255))");
            LoggingUtils.log("Connected to database successfully!");
        } catch (SQLException | ClassNotFoundException e) {
            LoggingUtils.log(Level.SEVERE, "Database connection failed! Shutting down plugin...");
            Main.getInstance().getPluginLoader().disablePlugin(Main.getInstance());
            e.printStackTrace();
        }
    }

    /**
     * Check if a sign's data exists in the database
     *
     * @param name Sign name to check
     * @return If a sign exists
     */
    public boolean doesSignExist(String name) {
        try {
            ResultSet rs = connection.getStatement().executeQuery("SELECT COUNT(*) FROM signData WHERE serverName='" + name + "';");
            rs.next();

            return rs.getInt(1) == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the players from the selected sign
     *
     * @param sign ArSign name to grab from
     * @return ArSign's players
     */
    public int getPlayers(String sign) {
        try {
            ResultSet rs = connection.getStatement().executeQuery("SELECT players FROM signData WHERE serverName='" + sign + "';");

            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get the maximum players from the selected sign
     *
     * @param sign ArSign name to grab from
     * @return ArSign's max players
     */
    public int getMaxPlayers(String sign) {
        try {
            ResultSet rs = connection.getStatement().executeQuery("SELECT maxPlayers FROM signData WHERE serverName='" + sign + "';");

            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get the state from the selected sign
     *
     * @param sign ArSign name to grab from
     * @return ArSign's state
     */
    public String getState(String sign) {
        try {
            ResultSet rs = connection.getStatement().executeQuery("SELECT state FROM signData WHERE serverName='" + sign + "';");

            if(rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
