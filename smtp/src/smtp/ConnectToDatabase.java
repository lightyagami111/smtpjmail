/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smtp;

/**
 *
 * @author Administrator
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {
	public static String userid = "root", password = "123456";
	public static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/smtp?useUnicode=yes&characterEncoding=utf-8";
	private ConnectToDatabase() {}//prevent create objects
	public static Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		Class.forName(JDBC_Driver);
		con = DriverManager.getConnection(url, userid, password);
		con.setAutoCommit(false);
		return con;
	}
}
//sun.jdbc.odbc.JdbcOdbcDriver