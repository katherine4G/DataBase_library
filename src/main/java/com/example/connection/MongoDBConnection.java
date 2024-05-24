package com.example.connection;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Mongo_DB";
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    static {
        try {
            ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
// import com.mongodb.MongoClientURI;
// import com.mongodb.client.MongoClient;

// import com.mongodb.MongoClient;
// import com.mongodb.client.MongoDatabase;

// public class MongoDBConnection {
//     private static final String CONNECTION_STRING = "mongodb://localhost:27017";
//     private static final String DATABASE_NAME = "miBaseDeDatos";
//     private static MongoClient mongoClient = null;
//     private static MongoDatabase database = null;

//     static {
//         try {
//             MongoClientURI uri = new MongoClientURI(CONNECTION_STRING);
//             mongoClient = new MongoClient(uri);
//             database = mongoClient.getDatabase(DATABASE_NAME);
//         } catch (Exception e) {
//         }
//     }

//     public static MongoDatabase getDatabase() {
//         return database;
//     }

//     public static void close() {
//         if (mongoClient != null) {
//             mongoClient.close();
//         }
//     }
// }
////////////////////////////////////////////
// public class MongoDB_conn {
//      private static final String CONNECTION_STRING = "mongodb://localhost:27017";
//     private static final String DATABASE_NAME = "miBaseDeDatos";
//     private static MongoClient mongoClient = null;
//     private static MongoDatabase database = null;

//     static {
//         try {
//             MongoClientURI uri = new MongoClientURI(CONNECTION_STRING);
//             mongoClient = new MongoClient(uri);
//             database = mongoClient.getDatabase(DATABASE_NAME);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public static MongoDatabase getDatabase() {
//         return database;
//     }

//     public static void close() {
//         if (mongoClient != null) {
//             mongoClient.close();
//         }
//     }
// }
