package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnector {

    private static final String JDBC_URL = "jdbc:sqlserver://10.176.111.34\\EASV-DB4:1433;database=RateCalcGambling;trustServerCertificate=true";

    private static final String USER = "CSe2023b_e_6";
    private static final String PASSWORD = "CSe2023bE6#23";

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

}
