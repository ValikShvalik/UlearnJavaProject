import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteConfig;

public class SQLHandler {
    private final Connection connection;

    public SQLHandler() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        connection = DriverManager.getConnection("jdbc:sqlite:DataBase.db", config.toProperties());

        if (connection.createStatement().executeQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = 'table'")
                .getInt(1) != 0) {
            connection.createStatement().execute("DELETE FROM `teams`");
            connection.createStatement().execute("DELETE FROM `playersIndicators`");
            connection.createStatement().execute("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'teams'");
        }

        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS playersIndicators ("
                + "playerId integer not null PRIMARY KEY AUTOINCREMENT,"
                + "name text not null,"
                + "position text not null,"
                + "height integer not null,"
                + "weight integer not null,"
                + "age real not null,"
                + "teamId integer,"
                + "FOREIGN KEY (teamId) REFERENCES teams(id));");

        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS teams" +
                        "(id integer primary key AUTOINCREMENT, name text not null UNIQUE);");
    }

    public List<String> getAllData()  throws SQLException {
        List<String> allData = new ArrayList<>();

        String sql = """
                SELECT
                	playersIndicators.name, playersIndicators.position, playersIndicators.height,
                	playersIndicators.weight, playersIndicators.age, teams.name AS teamName
                FROM `playersIndicators`
                	LEFT JOIN `teams`
                		ON teams.id = playersIndicators.teamId
                """;

        ResultSet data = connection.createStatement().executeQuery(sql);

        while (data.next()) {
            String dbLine = data.getString("name") + ", " +
                            data.getString("position") + ", " +
                            data.getInt("height") + ", " +
                            data.getInt("weight") + ", " +
                            data.getDouble("age") + ", " +
                            data.getString("teamName");

            allData.add(dbLine);
        }

        return allData;
    }

    public void insertTeam(Team team) throws SQLException {
        PlayerIndicators[] players = team.getPlayers();
        if (!getTeamNumber(team.getName()).next()) {
            String sqlInsertTeam = "INSERT INTO teams(name) VALUES(?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertTeam);
            preparedStatement.setString(1, team.getName());
            preparedStatement.executeUpdate();
        }

        for (PlayerIndicators player : players) {
            insertPlayer(player, getTeamNumber(team.getName()).getInt(1));
        }
    }

    public void insertPlayer(PlayerIndicators player, int teamId) throws SQLException {
        String sql = "INSERT INTO playersIndicators(`name`, `position`, `height`, `weight`, `age`, `teamId`) " +
                "VALUES (?,?,?,?,?,?);";

        PreparedStatement preparedQuery = connection.prepareStatement(sql);
        preparedQuery.setString(1, player.getName());
        preparedQuery.setString(2, player.getPosition());
        preparedQuery.setInt(3, player.getHeight());
        preparedQuery.setInt(4, player.getWeight());
        preparedQuery.setDouble(5, player.getAge());
        preparedQuery.setInt(6, teamId);
        preparedQuery.executeUpdate();
    }

    private ResultSet getTeamNumber(String name) throws SQLException {
        return connection.createStatement().executeQuery("SELECT * FROM `teams` WHERE `name` = '" + name + "'");
    }

    public List<String> getTeamsNames() throws SQLException {
        List<String> teams = new ArrayList<>();
        ResultSet data = connection.createStatement().executeQuery( "SELECT `name` FROM `teams`");
        while (data.next()) {
            teams.add(data.getString(1));
        }
        return teams;
    }

    public double getAverageAge(String teamName) throws SQLException {
        int teamId = getTeamNumber(teamName).getInt(1);
        String sql = "SELECT AVG(age) FROM `playersIndicators` WHERE `teamId` = '" + teamId + "'";
        ResultSet averageHeight = connection.createStatement().executeQuery(sql);
        return averageHeight.getDouble(1);
    }

    public String[] getMaxHeightPlayers(String teamName) throws SQLException {
        int teamId = getTeamNumber(teamName).getInt(1);
        String sql = "SELECT `name` FROM `playersIndicators` WHERE `teamId` = " + teamId + " ORDER BY `height` DESC";
        ResultSet data = connection.createStatement().executeQuery(sql);

        String[] values = new String[5];
        int i = 0;
        while (data.next() && i < 5) {
            values[i] =  data.getString(1);
            i++;
        }

        return values;
    }

    public double getAverageHeight(String teamName) throws SQLException {
        int teamId = getTeamNumber(teamName).getInt(1);
        String sql = "SELECT AVG(height) FROM playersIndicators WHERE `teamId` = '" + teamId + "'";
        ResultSet averageHeight = connection.createStatement().executeQuery(sql);
        return averageHeight.getDouble(1);
    }

    public double getAverageWeight(String teamName) throws SQLException {
        int teamId = getTeamNumber(teamName).getInt(1);
        String sql = "SELECT AVG(weight) FROM playersIndicators WHERE `teamId` = '" + teamId + "'";
        ResultSet averageHeight = connection.createStatement().executeQuery(sql);
        return averageHeight.getDouble(1);
    }
}
