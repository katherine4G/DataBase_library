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
        Connection conexion = null;

        //  conexión a la base de datos
        conexion = OracleConnection.conectarOracleXE();
       
        OracleData oracleData = new OracleData(conexion); //instancia de OracleData

        // objeto de la clase TRABAJADOR
        TRABAJADOR trabajador0 = new TRABAJADOR();
        trabajador0.setID(0);
        trabajador0.setName("Johan");
        trabajador0.setLastName("Mora");
        trabajador0.setWorker_id("12345678");
        trabajador0.setEmail("jMora@gmail.com");
        trabajador0.setJob_position("Developer");
        
        oracleData.mapearClaseATabla(trabajador0); // Mapear la clase a la tabla e insertar trabajador0

        // otro objeto de la clase TRABAJADOR
        TRABAJADOR trabajador1 = new TRABAJADOR();
        trabajador1.setID(1);
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
        
        // // // Actualizar datos de un trabajador
        // trabajador1.setName("Katherine");
        // oracleData.actualizarDatos("TRABAJADOR", trabajador1, "id", trabajador1.getID());

        // trabajador1.setLastName("Guatemala");
        // oracleData.actualizarDatos("TRABAJADOR", trabajador1, "id", trabajador1.getID());
        
        // // // Eliminar datos de un trabajador
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



//////////////CREAR TABLA PERSONA ///////////////////

/* public class Main {
    public static void main(String[] args) throws SQLException {
        // Establecer la conexión a la base de datos
        Connection conexion = null;
        try {
            conexion = OracleConnection.conectarOracleXE();

            // Crear la tabla de personas
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

    private static void crearTablaPersonas(Connection conexion) throws SQLException {
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
        String query = "INSERT INTO personas (id, nombre, edad, cedula) VALUES (?, ?, ?, ?)";

        // Ejecutar la consulta SQL para cada persona
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            for (String[] persona : personas) {
                preparedStatement.setInt(1, Integer.parseInt(persona[0]));
                preparedStatement.setString(2, persona[1]);
                preparedStatement.setInt(3, Integer.parseInt(persona[2]));
                preparedStatement.setString(4, persona[3]);
                preparedStatement.executeUpdate();
            }
        }
    }
}
 */

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