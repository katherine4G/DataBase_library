package com.example;
//oracle
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.example.connection.OracleConnection;
import com.example.mapping.OracleData;
import com.example.model.TRABAJADOR;

//MongoDB
// import org.bson.Document;
// import com.example.connection.MongoDBConnection;
// import com.example.mapping.MongoData;
// import com.example.model.CAR;
// import com.mongodb.client.MongoDatabase;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, SQLException {
        // Establecer la conexión
        Connection conexion = OracleConnection.conectarOracleXE();      
        OracleData oracleData = new OracleData(conexion); 
        conexion.setAutoCommit(false);

        //  CAR car = new CAR(1,"Toyota","corolla",2000);
        // // Mapear la clase a la tabla
        // oracleData.mapearClaseATabla(car);
        // // Eliminar la tabla CAR si existe
        // oracleData.eliminarTabla("trabajador");

        // objetos de la clase TRABAJADOR
        TRABAJADOR worker0 = new TRABAJADOR(0,"Joan","CS","181273","JC@gmail.com","developer"); 
        TRABAJADOR worker1 = new TRABAJADOR(1,"kathy","G","12345","k@gmail.com","manager"); 

        oracleData.mapearClaseATabla(worker0); // Mapear la clase a la tabla e insertar trabajadores
        oracleData.mapearClaseATabla(worker1); 

        // // Actualizar datos de un trabajador
        // trabajador0.setName("Johan");
        // oracleData.actualizarDatos("TRABAJADOR", trabajador0, "trabajador_id", trabajador0.getID());

        // trabajador1.setLastName("Guatemala");
        // oracleData.actualizarDatos("TRABAJADOR", trabajador1, "trabajador_id", trabajador1.getID());
        
        // // Eliminar datos de un trabajador
        // oracleData.eliminarDatos("TRABAJADOR", "trabajador_id", trabajador0.getID());
        // oracleData.eliminarDatos("TRABAJADOR", "trabajador_id", trabajador1.getID());

        // to string
        List<TRABAJADOR> trabajadores = oracleData.recuperarDeTabla(TRABAJADOR.class);
        for (TRABAJADOR trabajador : trabajadores) {
            System.out.println(trabajador.getID() + " " + trabajador.getName() + " " + trabajador.getLastName() +
                    " " + trabajador.getWorker_id() + " " + trabajador.getEmail() + " " + trabajador.getJob_position());
        }
        
        try { conexion.close();} 
        catch (SQLException e) { System.err.println("Error al cerrar la conexión: " + e.getMessage());}

    }
}



/////////////////////MONGO DB/////////////////////////////////////////
//     public static void main(String[] args) {
//         try {
//             MongoDatabase database = MongoDBConnection.getDatabase();
//             MongoData carDAO = new MongoData(database);

//             // Insertar carros
//             carDAO.insertarCarros();

//             // Obtener y mostrar todos los carros
//             List<Document> cars = carDAO.obtenerTodosLosCarros();
//             cars.forEach(System.out::println);

//             // Obtener y mostrar un carro por ID
//             Document car = carDAO.obtenerCarPorId(1);
//             System.out.println(car);

//             // Actualizar un carro
//             CAR updatedCar = new CAR(1, "Toyota", "Camry", 2021);
//             carDAO.actualizarCarro(1, updatedCar);

            // // Eliminar un carro
            // carDAO.eliminarCarro(2);
//         } catch (Exception e) {
//             System.err.println("Error al interactuar con MongoDB: " + e.getMessage());
//         } finally {
//             MongoDBConnection.close();
//         }
//     }
// }