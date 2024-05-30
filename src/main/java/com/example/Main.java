package com.example;
//oracle
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.connection.OracleConnection;
import com.example.mapping.OracleData;

//mongo
import com.example.model.TRABAJADOR;
import com.mongodb.client.MongoDatabase;
import com.example.mapping.MongoCRUD;
import com.example.connection.MongoDBConnection;


public class Main {
    public static void main(String[] args) throws SQLException {
       
       MongoDatabase database = MongoDBConnection.conectarMongoDB();// Conectar a la base de datos  
       MongoCRUD mongoData = new MongoCRUD(database);// Crear una instancia de MongoData

       // Crear objetos Trabajador
       TRABAJADOR trabajador1 = new TRABAJADOR(1,"Maria","Perez","1234254","mari@gmail.com","limpieza");
       TRABAJADOR trabajador2 = new TRABAJADOR(2,"José","Ureña","438942","jose@gmail.com","limpieza");

       // Insertar objetos Trabajador en la colección
       mongoData.insert(trabajador1);
       mongoData.insert(trabajador2);

       // Seleccionar todos los trabajadores (to string)
       List<TRABAJADOR> trabajadores = mongoData.selectAll(TRABAJADOR.class);
       for (TRABAJADOR trabajador : trabajadores) {
           System.out.println(trabajador.getID()+"-"+trabajador.getName() + " " + trabajador.getLastName()+ "." +trabajador.getWorker_id()+". " + trabajador.getEmail() + ". " + trabajador.getJob_position() );
       }
    
       TRABAJADOR workerRecovered = MongoCRUD.selectById(TRABAJADOR.class,1);// Seleccionar un trabajador por ID

       // Modificar al trabajador seleccionado
       workerRecovered.setJob_position("Secretaria");
       mongoData.modify(workerRecovered);
       System.out.println("Trabajador modificado: " + workerRecovered.getName()+" = "+ workerRecovered.getJob_position());

       
       mongoData.delete(TRABAJADOR.class, 1);// Eliminar un trabajador
       mongoData.delete(TRABAJADOR.class, 2);

       MongoDBConnection.close();
   
    }
}



// public class Main {
//     public static void main(String[] args) throws IllegalAccessException, SQLException {
//         // Establecer la conexión
//         Connection conexion = OracleConnection.conectarOracleXE();      
//         OracleData oracleData = new OracleData(conexion); 
//         conexion.setAutoCommit(false);

//         //  CAR car = new CAR(1,"Toyota","corolla",2000);
//         // // Mapear la clase a la tabla
//         // oracleData.mapearClaseATabla(car);
//         // // Eliminar la tabla CAR si existe
//          //oracleData.eliminarTabla("trabajador");

//         //objetos de la clase TRABAJADOR
//         TRABAJADOR worker0 = new TRABAJADOR(0,"Joan","CS","181273","JC@gmail.com","developer"); 
//         TRABAJADOR worker1 = new TRABAJADOR(1,"kathy","G","12345","k@gmail.com","manager"); 

//         oracleData.mapearClaseATabla(worker0); // Mapear la clase a la tabla e insertar trabajadores
//         oracleData.mapearClaseATabla(worker1); 

//         // // Actualizar datos de un trabajador
//         // worker0.setName("Johan");
//         // oracleData.actualizarDatos("TRABAJADOR", worker0, "trabajador_id", worker0.getID());
//         // worker1.setLastName("Guatemala");
//         // oracleData.actualizarDatos("TRABAJADOR", worker1, "trabajador_id", worker1.getID());
        
//         // // Eliminar datos de un trabajador
//         // oracleData.eliminarDatos("TRABAJADOR", "trabajador_id", worker0.getID());
//         // oracleData.eliminarDatos("TRABAJADOR", "trabajador_id", worker1.getID());

//         // to string
//         List<TRABAJADOR> trabajadores = oracleData.recuperarDeTabla(TRABAJADOR.class);
//         for (TRABAJADOR trabajador : trabajadores) {
//             System.out.println(trabajador.getID() + " " + trabajador.getName() + " " + trabajador.getLastName() +
//                     " " + trabajador.getWorker_id() + " " + trabajador.getEmail() + " " + trabajador.getJob_position());
//         }
        
//         try { conexion.close();} 
//         catch (SQLException e) { System.err.println("Error al cerrar la conexión: " + e.getMessage());}

//     }
// }


