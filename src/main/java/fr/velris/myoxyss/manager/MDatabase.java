package fr.velris.myoxyss.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.velris.myoxyss.Myoxyss;
import org.bukkit.entity.Player;

import java.sql.*;

public class MDatabase {

    private final Myoxyss plugin = Myoxyss.getInstance();
    private HikariDataSource dataSource;

    public void initDatabase() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://54.36.249.182:3306/Myo");
        config.setUsername("myo");
        config.setPassword("myo");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS myo (id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "username TEXT NOT NULL, uuid TEXT NOT NULL UNIQUE, money DOUBLE NOT NULL DEFAULT 0);");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public boolean isPlayerExist(Player player) {
        try (Connection connection = getConnection()) {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM myo WHERE uuid = ?;");
            pstmt.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void insertNewPlayer(Player player) {

        if (!isPlayerExist(player)) {
            try (Connection connection = getConnection()) {

                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO myo (username, uuid) VALUES (?, ?);");
                pstmt.setString(1, player.getName());
                pstmt.setString(2, player.getUniqueId().toString());

                pstmt.executeUpdate();

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void addMoney(String playerName) {
        try (Connection connection = getConnection()) {

            PreparedStatement pstmt = connection.prepareStatement("UPDATE myo SET money = money + 10 WHERE username = ?;");
            pstmt.setString(1, playerName);

            pstmt.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Double getMoney(String playerName) {
        try (Connection connection = getConnection()) {

            PreparedStatement pstmt = connection.prepareStatement("SELECT money FROM myo WHERE username = ?;");
            pstmt.setString(1, playerName);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("money");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0.0;
    }

}
