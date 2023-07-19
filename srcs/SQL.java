import java.sql.*;
import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SQL {
	private static String db_dir = "./database/";
	private static String db_log_dir = "./database/log/";
	private static String db_name = "database.db";

	public static void getLog() {
		LocalDateTime currentLocalTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String time_log = currentLocalTime.format(formatter);

		Path file_from = Paths.get(SQL.db_dir + SQL.db_name);
		Path file_to = Paths.get(SQL.db_log_dir + time_log);

		try {
			Files.copy(file_from, file_to);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void insert(String statement, String... s) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + SQL.db_dir + SQL.db_name);
			PreparedStatement ps = conn.prepareStatement(statement);
			int index = 0;
			for (String str : s)
				ps.setString(++index, str);
			ps.executeUpdate();
			ps.close();
			System.out.println("Insert done");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<ArrayList<String>>	select(String statement, int elements, String... s) {
		Connection conn = null;
		ArrayList<ArrayList<String>> result = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + SQL.db_dir + SQL.db_name);

			PreparedStatement ps = conn.prepareStatement(statement);
			int index = 0;
			for (String str : s)
				ps.setString(++index, str);
			ResultSet rs = ps.executeQuery();
			result = new ArrayList<ArrayList<String>>();
			while( rs.next() ) {
				ArrayList<String> value = new ArrayList<String>();
				for (int i = 1; i <= elements; i++)
					value.add(rs.getString(i));
				result.add(value);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (result);
	}

	public static ArrayList<ArrayList<String>>	select_like(String statement, int elements, String... s) {
		Connection conn = null;
		ArrayList<ArrayList<String>> result = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + SQL.db_dir + SQL.db_name);

			PreparedStatement ps = conn.prepareStatement(statement);
			int index = 0;
			for (String str : s)
				ps.setString(++index, "%" + str + "%");
			ResultSet rs = ps.executeQuery();
			result = new ArrayList<ArrayList<String>>();
			while( rs.next() ) {
				ArrayList<String> value = new ArrayList<String>();
				for (int i = 1; i <= elements; i++)
					value.add(rs.getString(i));
				result.add(value);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (result);
	}

	public static void update(String statement, String... s) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + SQL.db_dir + SQL.db_name);
			PreparedStatement ps = conn.prepareStatement(statement);
			int index = 0;
			for (String str : s)
				ps.setString(++index, str);
			ps.executeUpdate();
			ps.close();
			System.out.println("Update done");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void delete(String statement, String... s) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + SQL.db_dir + SQL.db_name);
			PreparedStatement ps = conn.prepareStatement(statement);
			int index = 0;
			for (String str : s)
				ps.setString(++index, str);
			ps.executeUpdate();
			ps.close();
			System.out.println("Delete done");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
