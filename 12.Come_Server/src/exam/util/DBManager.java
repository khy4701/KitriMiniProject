package exam.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	private static final String CON_URL = "jdbc:oracle:thin:@192.168.0.231:1521:xe";

	static {
		// 1. JDBC �뱶�씪�씠踰� 濡쒕뱶
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			SystemLog.printLog("JDBC �뱶�씪�씠踰� 濡쒕뱶 �꽦怨�!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �뿰寃�
	public static Connection getConnection() {
		// 2. DB �꽌踰� �뿰寃�
		Connection con = null;
		try {
			con = DriverManager.getConnection(CON_URL, "hr", "hr");
			SystemLog.printLog("DB �뿰寃� �꽦怨�!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	// �뿰寃� 醫낅즺
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection con, PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
