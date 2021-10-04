import utils.ConfigParams;

import java.sql.Connection;
import java.sql.SQLException;

public class MainApp {
    static Long runTestWithoutPool(int N) {
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Connection connection = null;
            try {
                connection = nopool.MySqlConnection.getConnection();
            } catch (Exception e) {

            } finally {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    static Long runTestWithPool(int N) {
        Long startTime = System.currentTimeMillis();
        withpool.MySqlConnection mySqlConnection = new withpool.MySqlConnection();
        for (int i = 0; i < N; i++) {
            Connection connection = null;
            try {
                connection = mySqlConnection.getConnection();
            } catch (Exception e) {

            } finally {
                try {
                    if (connection != null && !connection.isClosed()) {
                        mySqlConnection.closeConnection(connection);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        int poolSize = Integer.parseInt(ConfigParams.getConfig("POOL_SIZE"));
        System.out.println("==============BENCHMARK=============");
        System.out.println("Pool size     :" + poolSize);
        System.out.println("====================================");
        int N = 1;
        System.out.println("N           : " + N);
        System.out.println("With Pool   : " + runTestWithPool(N) + " ms");
        System.out.println("Without Pool: " + runTestWithoutPool(N) + " ms");
        System.out.println("====================================");

        N = 100;
        System.out.println("N           : " + N);
        System.out.println("With Pool   : " + runTestWithPool(N) + " ms");
        System.out.println("Without Pool: " + runTestWithoutPool(N) + " ms");
        System.out.println("====================================");

        N = 1000;
        System.out.println("N           : " + N);
        System.out.println("With Pool   : " + runTestWithPool(N) + " ms");
        System.out.println("Without Pool: " + runTestWithoutPool(N) + " ms");
        System.out.println("====================================");

        N = 10000;
        System.out.println("N           : " + N);
        System.out.println("With Pool   : " + runTestWithPool(N) + " ms");
        System.out.println("Without Pool: " + runTestWithoutPool(N) + " ms");
        System.out.println("====================================");

        N = 1000000;
        System.out.println("N           : " + N);
        System.out.println("With Pool   : " + runTestWithPool(N) + " ms");
        System.out.println("Without Pool: " + runTestWithoutPool(N) + " ms");
        System.out.println("====================================");
    }
}
