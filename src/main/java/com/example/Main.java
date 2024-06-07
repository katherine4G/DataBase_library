package com.example;

// ////////////////// ORACLE//////////////////////////////////////////

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.List;

// import com.example.connection.OracleConnection;
// import com.example.mapping.OracleData;
// import com.example.model.CAR;
// import com.example.model.TRABAJADOR;
// public class Main {
//     public static void main(String[] args) throws IllegalAccessException, SQLException {
//         try (Connection conexion = OracleConnection.conectarOracleXE()) {
//             OracleData oracleData = new OracleData(conexion);
//             conexion.setAutoCommit(false);
            
//             CAR car = new CAR(1,"Toyota","corolla",2000);
//             // Mapear la clase a la tabla
//             oracleData.mapearClaseATabla(car);
//             // Eliminar la tabla CAR si existe
//             oracleData.eliminarTabla("trabajador");
            
//             //objetos de la clase TRABAJADOR
//             TRABAJADOR worker0 = new TRABAJADOR(0,"Joan","CS","181273","JC@gmail.com","developer");
//             TRABAJADOR worker1 = new TRABAJADOR(1,"kathy","G","12345","k@gmail.com","manager");
            
//             oracleData.mapearClaseATabla(worker0); // Mapear la clase a la tabla e insertar trabajadores
//             oracleData.mapearClaseATabla(worker1);
            
//             // Actualizar datos de un trabajador
//             worker0.setName("Johan");
//             oracleData.actualizarDatos("TRABAJADOR", worker0, "trabajador_id", worker0.getID());
//             worker1.setLastName("Guatemala");
//             oracleData.actualizarDatos("TRABAJADOR", worker1, "trabajador_id", worker1.getID());
            
//             // Eliminar datos de un trabajador
//             oracleData.eliminarDatos("TRABAJADOR", "trabajador_id", worker0.getID());
//             oracleData.eliminarDatos("TRABAJADOR", "trabajador_id", worker1.getID());
            
//             // to string
//             List<TRABAJADOR> trabajadores = oracleData.recuperarDeTabla(TRABAJADOR.class);
//             for (TRABAJADOR trabajador : trabajadores) {
//                 System.out.println(trabajador.getID() + " " + trabajador.getName() + " " + trabajador.getLastName() +
//                         " " + trabajador.getWorker_id() + " " + trabajador.getEmail() + " " + trabajador.getJob_position());
//             }
            
//             // Buscar un carro por nombre
//             oracleData.findByFieldAndPrint(CAR.class, "marca", "Toyota");
//         }

//     }
// }


// ///////////////////////////////Mongo/////////////////////

// import java.sql.SQLException;
// import java.util.List;

// import com.example.connection.MongoDBConnection;
// import com.example.mapping.MongoCRUD;
// import com.example.model.TRABAJADOR;
// import com.mongodb.client.MongoDatabase;

// public class Main {
//     public static void main(String[] args) throws SQLException {
       
//        MongoDatabase database = MongoDBConnection.conectarMongoDB();// Conectar a la base de datos  
//        MongoCRUD mongoData = new MongoCRUD(database);// Crear una instancia de MongoData

//        // Crear objetos Trabajador
//        TRABAJADOR trabajador1 = new TRABAJADOR(1,"Maria","Perez","1234254","mari@gmail.com","limpieza");
//        TRABAJADOR trabajador2 = new TRABAJADOR(2,"José","Ureña","438942","jose@gmail.com","limpieza");

//        // Insertar objetos Trabajador en la colección
//        mongoData.insert(trabajador1);
//        mongoData.insert(trabajador2);

//        // Seleccionar todos los trabajadores (to string)
//        List<TRABAJADOR> trabajadores = mongoData.selectAll(TRABAJADOR.class);
//        for (TRABAJADOR trabajador : trabajadores) {
//            System.out.println(trabajador.getID()+"-"+trabajador.getName() + " " + trabajador.getLastName()+ "." +trabajador.getWorker_id()+". " + trabajador.getEmail() + ". " + trabajador.getJob_position() );
//        }
    
//        TRABAJADOR workerRecovered = MongoCRUD.selectById(TRABAJADOR.class,1);// Seleccionar un trabajador por ID

//        // Modificar al trabajador seleccionado
//        workerRecovered.setJob_position("Secretaria");
//        mongoData.modify(workerRecovered);
//        System.out.println("Trabajador modificado: " + workerRecovered.getName()+" = "+ workerRecovered.getJob_position());

//        //mongoData.delete(TRABAJADOR.class, 1);// Eliminar un trabajador
//        //mongoData.delete(TRABAJADOR.class, 2);
       
    
//        mongoData.findByFieldAndPrint(TRABAJADOR.class, "nombre", "Ma");
    

//        MongoDBConnection.close();
   
//     }
// }


// /////////////////////////////MySql/////////////////////////////////////

// import java.sql.Connection;
// import java.sql.SQLException;

// import com.example.connection.MySqlConnection;
// import com.example.mapping.MySlqCRUD;
// import com.example.model.TRABAJADOR;

// public class Main {
//     public static void main(String[] args) throws SQLException {
//         Connection conexion = MySqlConnection.conectarMySQL();
//         MySlqCRUD mapeo = new MySlqCRUD(conexion);
//         // Crear un objeto Persona
//         TRABAJADOR worker0 = new TRABAJADOR(0,"jaime","Corrales","387429","jaime@gmail.com","security");
//         TRABAJADOR worker1 = new TRABAJADOR(1,"katherine","Barrientos","387429","kat@gmail.com","admin");

//         // Insertar el objeto Persona en la base de datos
//         mapeo.insertar(worker0);
//         mapeo.insertar(worker1);


//         // // Eliminar el objeto de la base de datos
//         // mapeo.eliminar(TRABAJADOR.class, 0);
//         // mapeo.eliminar(TRABAJADOR.class, 1);


//         // Seleccionar todas las personas de la tabla y mostrarlas
//         System.out.println("Personas en la base de datos:");
//         mapeo.seleccionarTodo(TRABAJADOR.class).forEach(System.out::println);
//         mapeo.findByFieldAndPrint(TRABAJADOR.class, "nombre", "katherine");

//         }
    
// }
