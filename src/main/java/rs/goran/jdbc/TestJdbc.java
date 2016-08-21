package rs.goran.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

    public void test() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/pomodoro_rosso?useSSL=false";
        String user = "hbstudent";
        String password = "hbstudent";

        try {
            System.out.println("Connecting to database " + jdbcUrl);

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("Connection successful.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
