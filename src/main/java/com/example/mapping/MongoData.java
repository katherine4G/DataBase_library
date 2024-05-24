package com.example.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.example.model.CAR;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

public class MongoData {
    private final MongoCollection<Document> collection;

    public MongoData(MongoDatabase database) {
        this.collection = database.getCollection("CAR");
    }

    public void insertarCarros() {
        CAR car1 = new CAR(1, "Toyota", "Corolla", 2020);
        CAR car2 = new CAR(2, "Honda", "Civic", 2019);

        Document docCar1 = new Document("id", car1.getId())
                .append("marca", car1.getMarca())
                .append("modelo", car1.getModelo())
                .append("año", car1.getAño());

        Document docCar2 = new Document("id", car2.getId())
                .append("marca", car2.getMarca())
                .append("modelo", car2.getModelo())
                .append("año", car2.getAño());

        collection.insertMany(Arrays.asList(docCar1, docCar2));
        System.out.println("Datos de carros insertados exitosamente.");
    }

    public List<Document> obtenerTodosLosCarros() {
        return collection.find().into(new ArrayList<>());
    }

    public Document obtenerCarPorId(int id) {
        return collection.find(eq("id", id)).first();
    }

    public void actualizarCarro(int id, CAR car) {
        Bson filter = eq("id", id);
        Bson update = new Document("$set", new Document("marca", car.getMarca())
                .append("modelo", car.getModelo())
                .append("año", car.getAño()));
        collection.updateOne(filter, update);
        System.out.println("Carro con id " + id + " actualizado exitosamente.");
    }

    public void eliminarCarro(int id) {
        collection.deleteOne(eq("id", id));
        System.out.println("Carro con id " + id + " eliminado exitosamente.");
    }
}