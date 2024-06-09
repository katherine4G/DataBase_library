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
    private static final String USER_ORACLE = "example_user";
    private static final String PASSWORD = "example_password";  
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
}
```

#### MongoDB
[![mongoDB](https://upload.wikimedia.org/wikipedia/commons/9/93/MongoDB_Logo.svg "mongoDB")](https://upload.wikimedia.org/wikipedia/commons/9/93/MongoDB_Logo.svg "mongoDB")

```java
	public class MongoDBConnection {
	//Parameters
	  private static final String CONNECTION_STRING="mongodb://localhost:27017";
    private static final String DATABASE_NAME = "example"; 
	}
 ```
#### My SQL
[![mySql](https://upload.wikimedia.org/wikipedia/labs/8/8e/Mysql_logo.png "mySql")](https://upload.wikimedia.org/wikipedia/labs/8/8e/Mysql_logo.png "mySql")


     
	  // Parámetros conexión a MySQL
     private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/mysql_conn";
     private static final String USER_MYSQL = "your user";
     private static final String PASSWORD = "your password";


------------


### Use
Below are some examples of how to use the functions provided by the library:


###  Oracle SQL developer

- #### Create Table and insert objects 

```java
 CAR car = new CAR(1,"Toyota","corolla",2000); //Example of class for table.
 
oracleData.mapearClaseATabla(car); // Map the class to the table.

```
- #### Delete a table

```java
oracleData.eliminarTabla("car");  //Delete the CAR table, if it exists.
```
- #### Delete table objects

```java
oracleData.eliminarDatos("TRABAJADOR", "car_id", worker0.getID());
```

- #### Update or edit Table data

```java
 // Search for a car by name
   oracleData.findByFieldAndPrint(CAR.class, "carBrand", "Toyota");
```


------------

### MongoDB

- #### Create collections and objects  

```java	
       Worker worker1 = new Worker(1,"Maria","Perez","1234254","mari@gmail.com","secretary"); //Create Worker class objects (for example)

       mongoData.insert(worker1); //Insert objects into collection

```

- #### Update or edit Table data

```java
	TRABAJADOR workerRecovered = MongoCRUD.selectById(TRABAJADOR.class, worker1.getID()); // Select a worker by ID
	
       workerRecovered.setJob_position("developer"); // Modify the selected worker
       mongoData.modify(workerRecovered);
```

- #### Delete objects in MongoDB Table 

   ```java
mongoData.delete(TRABAJADOR.class, 1);// Delete a worker
```


------------


### MySQL

- #### Create table  and objects 

```java
        TRABAJADOR worker1 = new TRABAJADOR(id,"name","lastName","id_person","email","admin");
        // Insert
        mapeo.insertar(worker0);
        mapeo.insertar(worker1);
```

- #### Find data 

```java
// Select all people from the table and print 
        mapeo.seleccionarTodo(TRABAJADOR.class).forEach(System.out::println);
		//find by field
        mapeo.findByFieldAndPrint(TRABAJADOR.class, "name", "name_example");

```

- #### Delete objects on table
```java
 // Delete 
        mapeo.eliminar(TRABAJADOR.class, worker0.getPrimaryKey());  //delete by id number
```


------------

### Contributions

Contributions are welcome! If you have any ideas to improve this library. feel free to send your recommendations.

### License
This project is licensed under an  License.
