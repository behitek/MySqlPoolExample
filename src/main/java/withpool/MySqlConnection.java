package withpool;

import utils.ConfigParams;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.System.exit;

public class MySqlConnection {
    private static String dbUrl = "jdbc:mysql://" + ConfigParams.getConfig("MYSQL_HOST") + ":" +
            ConfigParams.getConfig("MYSQL_PORT") + "/" +
            ConfigParams.getConfig("MYSQL_DB") + "?autoReconnect=true&useSSL=false";
    private static String username = ConfigParams.getConfig("MYSQL_USER");
    private static String password = ConfigParams.getConfig("MYSQL_PASS");
    private static final int MAX_CONN = 10;
    private static final int MAX_TRY = 3;
    private static Queue<Connection> connections = new ArrayDeque<>();

    private static final MySqlConnection instance = new MySqlConnection();

    public static MySqlConnection getInstance(){
        return instance;
    }

    static {
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
        System.out.println("Inited " + connections.size() + " connections!");
    }

    private static boolean addConnection(){
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

    public static Connection getConnection() {
        return connections.remove();
    }


    public static void closeConnection(Connection connection){
        connections.add(connection);
    }
}
