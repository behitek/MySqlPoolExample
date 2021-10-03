package nopool;

import utils.ConfigParams;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static String dbUrl = "jdbc:mysql://" + ConfigParams.getConfig("MYSQL_HOST") + ":" +
            ConfigParams.getConfig("MYSQL_PORT") + "/" +
            ConfigParams.getConfig("MYSQL_DB") + "?autoReconnect=true&useSSL=false";
    private static String username = ConfigParams.getConfig("MYSQL_USER");
    private static String password = ConfigParams.getConfig("MYSQL_PASS");


    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, username, password);
//            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
