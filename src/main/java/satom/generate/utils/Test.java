package satom.generate.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Test {

    public static void main(String[] args) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties props = new Properties();
            props.setProperty("user", "phpatient");
            props.setProperty("password", "phpatient");
            props.setProperty("remarks", "true");
            props.setProperty("useInformationSchema", "true");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@172.16.10.28:1521:orcl", props);

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String user = databaseMetaData.getUserName();
            ResultSet rs = databaseMetaData.getTables(null, user, null, new String[] { "TABLE" });
            while (rs.next()) {
                String table = rs.getString("TABLE_NAME");
                ResultSet plRs = databaseMetaData.getPrimaryKeys(null, user, table);
                while (plRs.next()) {
                    String pkColumnName = plRs.getString("COLUMN_NAME");
                    System.out.println("alter table "+table+" modify ("+pkColumnName+" varchar2(40));");
                }
               
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
