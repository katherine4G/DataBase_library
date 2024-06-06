# Database Access Library
This library provides functions to access and manipulate data in different database management systems , including Oracle SQL Developer, MongoDB and MySQL. With this library, you can perform common operations (CRUD) like create / delete tables or inserting, updating , deleting data , and more.

### Download

To use this library in your project, follow these steps:

2. Download the library JAR file.
3. Add the JAR file to your Java project.
4. Import the necessary classes into your code.

### Setting
Before starting to use the library, make sure you correctly configure the access credentials to your databases. This can be done through environment variables, configuration files, or directly in code. Here's how to configure credentials for each database type:

#### Oracle SQL Developer

[![oracle](https://upload.wikimedia.org/wikipedia/commons/5/50/Oracle_logo.svg "oracle")](https://upload.wikimedia.org/wikipedia/commons/5/50/Oracle_logo.svg "oracle")

```java
public class OracleConnection {
    // Parameters for Oracle XE connection
    private static final String USER_ORACLE = "example";
    private static final String PASSWORD = "example";  
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
}
```

#### MongoDB
[![mongoDB](https://upload.wikimedia.org/wikipedia/commons/9/93/MongoDB_Logo.svg "mongoDB")](https://upload.wikimedia.org/wikipedia/commons/9/93/MongoDB_Logo.svg "mongoDB")

```java
	public class MongoDBConnection {
	//Parameters
	  private static final String CONNECTION_STRING="mongodb://localhost:27017";
    private static final String DATABASE_NAME = "example"; //
	}
 ```
#### My SQL
[![mySql](https://upload.wikimedia.org/wikipedia/labs/8/8e/Mysql_logo.png "mySql")](https://upload.wikimedia.org/wikipedia/labs/8/8e/Mysql_logo.png "mySql")


     
	  // Parámetros conexión a MySQL
     private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/mysql_conn";
     private static final String USER_MYSQL = "your user";
     private static final String PASSWORD = "your password";


### Use
Below are some examples of how to use the functions provided by the library:

#### Create/ delete a table and insert object  in Oracle SQL Developer

```java
 CAR car = new CAR(1,"Toyota","corolla",2000); //Example of class for table.
 
oracleData.mapearClaseATabla(car); // Map the class to the table.

oracleData.eliminarTabla("car");  //Delete the CAR table, if it exists.
```

#### Create collections and Insert data into MongoDB
```java	
       Worker worker1 = new Worker(1,"Maria","Perez","1234254","mari@gmail.com","developer"); //Create Worker objects (for example)
	   
       mongoData.insert(worker1); //Insert objects into collection
	   
       workerRecovered.setJob_position("Secretary"); // Modify the selected worker
       mongoData.modify(workerRecovered);
       
       mongoData.delete(TRABAJADOR.class, 1);// Delete a worker
```

```java
        TRABAJADOR worker1 = new TRABAJADOR(id,"name","lastName","id_person","email","admin");
        // Insert
        mapeo.insertar(worker0);
        mapeo.insertar(worker1);
        // Delete 
        mapeo.eliminar(TRABAJADOR.class, 1);
		// Select all people from the table and print 
        mapeo.seleccionarTodo(TRABAJADOR.class).forEach(System.out::println);
		//find by field
        mapeo.findByFieldAndPrint(TRABAJADOR.class, "name", "name_example");

		

```
### Contributions

Contributions are welcome! If you have any ideas to improve this library. feel free to send your recommendations.

### License
This project is licensed under an  License.
