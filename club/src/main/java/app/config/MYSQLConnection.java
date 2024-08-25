package app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import app.controller.Utils;

public class MYSQLConnection {

	private static final String URL = "jdbc:mysql://localhost:3306/club_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			Utils.log("Error al conectar a la base de datos");
			Utils.log(e.getMessage());
		}
		return connection;
	}

}
