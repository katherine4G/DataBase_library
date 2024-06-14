package com.example;

// // ////////////////// ORACLE//////////////////////////////////////////

// import java.sql.Connection;
// import java.sql.SQLException;

// import com.example.connection.OracleConnection;
// import com.example.mapping.OracleData;
// public class Main {
//     public static void main(String[] args) throws IllegalAccessException, SQLException {
//         try (Connection conexion = OracleConnection.conectarOracleXE()) {
//             OracleData oracleData = new OracleData(conexion);
//             conexion.setAutoCommit(false);

//             // // Objetos de la clase CAR
//             // CAR car0  = new CAR(0,"Toyota","corolla",2000);
//             // CAR car1 = new CAR(1, "Honda", "Civic", 2010);
//             // CAR car2 = new CAR(2, "Ford", "Focus", 2012);

//             // oracleData.mapearClaseATabla(car0);// Mapear la clase a la tabla e insertar carros
//             // oracleData.mapearClaseATabla(car1); 
//             // oracleData.mapearClaseATabla(car2);

//             // // Actualizar datos         
//             // car0.setMarca("Hyundai");
//             // oracleData.actualizarDatos("CAR", car0, "car_id", car0.getId());
//             // car1.setModelo("Fiesta");
//             // oracleData.actualizarDatos("CAR", car1, "car_id", car1.getId());

          
//             // //Eliminar datos
//             //  oracleData.eliminarDatos("CAR", "car_id", car0.getId());
//             //  oracleData.eliminarDatos("CAR", "car_id", car1.getId());
//             // oracleData.eliminarDatos("CAR", "car_id", car2.getId());

//             // //Eliminar la tabla 
//            //  oracleData.eliminarTabla("car");

//           //  // Buscar un carro (ej: por id)
//         //    oracleData.findByFieldAndPrint(CAR.class, "car_id", "0");
//         //    oracleData.findByFieldAndPrint(CAR.class, "modelo", "algo");
     
//             //  Buscar un carro por nombre
//             //  oracleData.findByFieldAndPrint(CAR.class, "marca", "Toyota");

//             // Recuperar y mostrar todos los carros en la base de datos
//             // List<CAR> cars = oracleData.recuperarDeTabla(CAR.class);
//             // for (CAR retrievedCar : cars) {
//             //     System.out.println(retrievedCar.getId() + " " + retrievedCar.getMarca() + " " + 
//             //     retrievedCar.getModelo() + " " + retrievedCar.getAño());
//             // }
//         }

//     }
// }


// ///////////////////////////////Mongo/////////////////////

// import java.sql.SQLException;

// import com.example.connection.MongoDBConnection;
// import com.example.mapping.MongoCRUD;
// import com.example.model.TRABAJADOR;
// import com.mongodb.client.MongoDatabase;

// public class Main {
//     public static void main(String[] args) throws SQLException {
       
//        MongoDatabase database = MongoDBConnection.conectarMongoDB();// Conectar a la base de datos  
//        MongoCRUD mongoData = new MongoCRUD(database);// Crear una instancia de MongoData

//       // Crear objetos Trabajador
//       TRABAJADOR trabajador1 = new TRABAJADOR(1,"Maria","Perez","1234254","mari@gmail.com","limpieza");
//       TRABAJADOR trabajador2 = new TRABAJADOR(2,"José","Ureña","438942","jose@gmail.com","limpieza");

//      // Insertar objetos Trabajador en la colección
//     //   mongoData.insert(trabajador1);
//     //   mongoData.insert(trabajador2);

//     //    // Seleccionar todos los trabajadores (to string)
//     //    List<TRABAJADOR> trabajadores = mongoData.selectAll(TRABAJADOR.class);
//     //    for (TRABAJADOR trabajador : trabajadores) {
//     //        System.out.println(trabajador.getID()+"-"+trabajador.getName() + " " + 
//     //        trabajador.getLastName()+ "." +trabajador.getWorker_id()+". " + 
//     //        trabajador.getEmail() + ". " + trabajador.getJob_position() );
//     //    }

//     // // Modificar al trabajador seleccionado
//     //    TRABAJADOR workerRecovered = MongoCRUD.selectById(TRABAJADOR.class,1);// Seleccionar un trabajador por ID

//     //    workerRecovered.setJob_position("Developer");
//     //    mongoData.modify(workerRecovered);
//     //    System.out.println("Trabajador modificado: " + workerRecovered.getName()+" = "+ workerRecovered.getJob_position());

//     //mongoData.delete(TRABAJADOR.class, 1);// Eliminar un trabajador
//    //// mongoData.eliminarColeccion("trabajador"); 
       
    
//     //    mongoData.findByFieldAndPrint(TRABAJADOR.class, "nombre", "José");

//     //    mongoData.findByFieldAndPrint(TRABAJADOR.class, "nombre", "Maria");
    

//        MongoDBConnection.close();
   
//     }
// }


//  ///////////////////////////MySql/////////////////////////////////////

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
//         TRABAJADOR worker0 = new TRABAJADOR(0,"jaime","Castro","387429","jaime@hotmail.com","security");
//         TRABAJADOR worker1 = new TRABAJADOR(1,"katherine","Barrientos","8012309","kat@gmail.com","admin");

//         // // Insertar el objeto Persona en la base de datos
//         // mapeo.insertar(worker0);
//         // mapeo.insertar(worker1);

//         //  // Actualizar datos de un trabajador          
//             //  worker0.setEmail("jaimeCR@gmail.com");
//             //  mapeo.modificar(worker0);
   

//         // // Eliminar el objeto de la base de datos
//         //  mapeo.eliminar(TRABAJADOR.class, 0);
//         //  mapeo.eliminar(TRABAJADOR.class, 1);

//         // // Eliminar la Tabla de la base de datos
//          mapeo.eliminarTabla("trabajador");

//         // Seleccionar todas las personas de la tabla y mostrarlas
//         // System.out.println("Personas en la base de datos:");
//         // mapeo.seleccionarTodo(TRABAJADOR.class).forEach(System.out::println);

//         // //Encontrar un registro
//         // mapeo.findByFieldAndPrint(TRABAJADOR.class, "nombre", "katherine");
//         // mapeo.findByFieldAndPrint(TRABAJADOR.class, "nombre", "jose");


//         }
    
// }
