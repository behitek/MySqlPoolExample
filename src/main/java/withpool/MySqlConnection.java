package withpool;

import utils.ConfigParams;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.System.exit;

public class MySqlConnection {
    private String dbUrl = "jdbc:mysql://" + ConfigParams.getConfig("MYSQL_HOST") + ":" +
            ConfigParams.getConfig("MYSQL_PORT") + "/" +
            ConfigParams.getConfig("MYSQL_DB") + "?autoReconnect=true&useSSL=false";
    private String username = ConfigParams.getConfig("MYSQL_USER");
    private String password = ConfigParams.getConfig("MYSQL_PASS");
    private final int MAX_CONN = Integer.parseInt(ConfigParams.getConfig("POOL_SIZE"));
    private final int MAX_TRY = 3;
    private Queue<Connection> connections = null;

    private boolean addConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, username, password);
            connections.add(conn);
            return true;
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return false;
    }

    public MySqlConnection(){
        connections = new ArrayDeque<>();
        int failedCount = 0;
        while (connections.size() < MAX_CONN){
            if (!addConnection()){
                failedCount++;
            }
            if (failedCount > MAX_TRY){
                System.out.println("GIVE UP");
                exit(-1);
            }
        }
    }

    public Connection getConnection() {
        return connections.remove();
    }


    public void closeConnection(Connection connection){
        connections.add(connection);
    }
}
