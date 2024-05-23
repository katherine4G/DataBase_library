package com.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {
    // Parámetros de conexión para Oracle XE
    private static final String USER_ORACLE = "new_user_developer";
    private static final String PASSWORD = "12345";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";


    public static Connection conectarOracleXE() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER_ORACLE, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
            return conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public static String getUSUARIO_ORACLE() {
        return USER_ORACLE;
    }

    
}