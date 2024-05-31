package com.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
     // Parámetros conexión a MySQL
     
     private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/mysql_conn";
     private static final String USER_MYSQL = "ADMIN";
     private static final String PASSWORD = "0000";

    public static Connection conectarMySQL() throws SQLException {
    try {
            Connection connection = DriverManager.getConnection(URL_MYSQL, USER_MYSQL, PASSWORD);
            System.out.println("Conexión a la base de datos MySQL establecida correctamente.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
 
}
