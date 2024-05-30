package com.example.connection;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    //Parameters
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Mongo_DB";
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    public static MongoDatabase conectarMongoDB() {
   
            try {
                ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
                mongoClient = MongoClients.create(connectionString);
                database = mongoClient.getDatabase(DATABASE_NAME);
            } catch (Exception e) {
            }          
            return database;
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