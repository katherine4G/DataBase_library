package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.connection.OracleConnection;


public class Main {
    public static void main(String[] args) throws SQLException {
    // Establecer la conexión a la base de datos
    Connection conexion = null;
    try {
        conexion = OracleConnection.conectarOracleXE();

        // // Crear la tabla de personas
         //crearTablaPersonas(conexion);

        // Añadir personas a la tabla
        insertarPersonas(conexion);

        System.out.println("Datos insertados exitosamente.");
    } catch (SQLException e) {
        System.err.println("Error al interactuar con la base de datos: " + e.getMessage());
    } finally {
        // Cerrar la conexión
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
        
}
       //  
    //     Connection conexionOracle = OracleConnection.conectarOracleXE();
    //     OracleData mapeadorOracle = new OracleData(conexionOracle);
    //   //  uso del mapeo:1
    //     TRABAJADOR trabajador0 = new TRABAJADOR();
    //     trabajador0.setID(0);
    //     trabajador0.setName("Juan");
    //     trabajador0.setLastName("Pérez");
    //     trabajador0.setWorker_id("12345678");
    //     trabajador0.setEmail("juan.perez@example.com");
    //     trabajador0.setJob_position("Desarrollador");
         
    //     //:2
    //     TRABAJADOR trabajador1 = new TRABAJADOR(1,"kath","Guatemala","801230969","kath@gmail","est");

    //     //  Oracle XE
    //      mapeadorOracle.mapearClaseATabla(trabajador0);
    //      mapeadorOracle.mapearClaseATabla(trabajador1);

    //     List<TRABAJADOR> workers = mapeadorOracle.recuperarDeTabla(TRABAJADOR.class);
    //     for (TRABAJADOR worker : workers) {
    //         System.out.println(worker.getID()+" "+worker.getName()+" "+worker.getLastName()
    //         +" "+worker.getWorker_id()+" "+worker.getEmail()+" "+worker.getJob_position());
    //     }
    

    private static void crearTablaPersonas (Connection conexion) throws SQLException {
        //main
        // Connection conexion = null;

        // try {
        //     conexion = OracleConnection.conectarOracleXE();

        //     // Crear la tabla
        //     crearTabla(conexion);

        //     System.out.println("Tabla creada exitosamente.");
        // } catch (SQLException e) {
        //     System.err.println("Error al crear la tabla: " + e.getMessage());
        // } finally {
        //     // Cerrar la conexión
        //     if (conexion != null) {
        //         try {
        //             conexion.close();
        //         } catch (SQLException e) {
        //             System.err.println("Error al cerrar la conexión: " + e.getMessage());
        //         }
        //     }
        // Definir la consulta SQL para crear la tabla

        String query = "CREATE TABLE personas (" +
                "id INT PRIMARY KEY," +
                "nombre VARCHAR(255)," +
                "edad INT," +
                "cedula VARCHAR(255)" +
                ")";

        // Ejecutar la consulta SQL
        try (Statement statement = conexion.createStatement()) {
            statement.executeUpdate(query);
        }
    }
    private static void insertarPersonas(Connection conexion) throws SQLException {
        // Definir los datos de las personas a insertar
        String[][] personas = {
            {"1", "Juan", "30", "123456789"},
            {"2", "María", "25", "987654321"},
            {"3", "Pedro", "35", "456789123"}
        };

        // Definir la consulta SQL de inserción
        String query = "INSERT INTO personas (id, nombre, edad, cedula) VALUES ";

        // Construir la consulta SQL con los datos de las personas
        for (String[] persona : personas) {
            query += "(" + persona[0] + ", '" + persona[1] + "', " + persona[2] + ", '" + persona[3] + "'),";
        }
        // Eliminar la coma final
        query = query.substring(0, query.length() - 1);

        // Ejecutar la consulta SQL
        try (Statement statement = conexion.createStatement()) {
            statement.executeUpdate(query);
        }
    }

}