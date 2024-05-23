package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.connection.OracleConnection;
import com.example.mapping.OracleData;
import com.example.model.TRABAJADOR;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, SQLException {
        Connection conexion = null;

        //  conexión a la base de datos
        conexion = OracleConnection.conectarOracleXE();
       
        OracleData oracleData = new OracleData(conexion); //instancia de OracleData

        // objeto de la clase TRABAJADOR
        TRABAJADOR trabajador0 = new TRABAJADOR();
        trabajador0.setID(3);
        trabajador0.setName("Johan");
        trabajador0.setLastName("Mora");
        trabajador0.setWorker_id("12345678");
        trabajador0.setEmail("juan@gmail.com");
        trabajador0.setJob_position("Developer");
        
        oracleData.mapearClaseATabla(trabajador0); // Mapear la clase a la tabla e insertar trabajador0

        // otro objeto de la clase TRABAJADOR
        TRABAJADOR trabajador1 = new TRABAJADOR();
        trabajador1.setID(2);
        trabajador1.setName("Kathy");
        trabajador1.setLastName("G");
        trabajador1.setWorker_id("87654321");
        trabajador1.setEmail("kath@gmail.com");
        trabajador1.setJob_position("Manager");
   
        oracleData.mapearClaseATabla(trabajador1);  // Insertar el segundo objeto

        // to string
        List<TRABAJADOR> trabajadores = oracleData.recuperarDeTabla(TRABAJADOR.class);
        for (TRABAJADOR trabajador : trabajadores) {
            System.out.println(trabajador.getID() + " " + trabajador.getName() + " " + trabajador.getLastName() +
                    " " + trabajador.getWorker_id() + " " + trabajador.getEmail() + " " + trabajador.getJob_position());
        }
        
        // Actualizar datos de un trabajador
        trabajador1.setName("Katherine");
        oracleData.actualizarDatos("TRABAJADOR", trabajador1, "id", trabajador1.getID());

        trabajador1.setLastName("Guatemala");
        oracleData.actualizarDatos("TRABAJADOR", trabajador1, "id", trabajador1.getID());
        
        // // Eliminar datos de un trabajador
        // oracleData.eliminarDatos("TRABAJADOR", "id", trabajador0.getID());
        // oracleData.eliminarDatos("TRABAJADOR", "id", trabajador1.getID());

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
