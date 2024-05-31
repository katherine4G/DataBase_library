package com.example.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoCRUD {
    private static MongoDatabase conexion = null;

    public MongoCRUD(MongoDatabase conexion) {
        MongoCRUD.conexion = conexion;
    }
    public boolean existsById(Class<?> clase, Object id) {
        String nombreColeccion = clase.getSimpleName().toLowerCase();
        MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);
        Document query = new Document("_id", id);
        return coleccion.find(query).first() != null;
    }
    public void insert(Object objeto) {
        try {
            Class<?> clase = objeto.getClass();
            String nombreColeccion = clase.getSimpleName().toLowerCase();
            MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);
    
            // Obtener el campo ID
            String idFieldName = clase.getSimpleName().toLowerCase() + "_id";
            Field idField = clase.getDeclaredField(idFieldName);
            idField.setAccessible(true);
            Object id = idField.get(objeto);
    
            // Verificar si el objeto ya existe
            if (existsById(clase, id)) {
                System.out.println("El documento con ID " + id + " ya existe en la colección " + nombreColeccion + ".");
                return;
            }         
            Document doc = convertirAJson(objeto, clase); // Convertir el objeto a un documento MongoDB
    
            // Insertar el documento en la colección
            coleccion.insertOne(doc);
            System.out.println("Documento insertado en la colección " + nombreColeccion + " correctamente.");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.err.println("Error al insertar el objeto en MongoDB: " + e.getMessage());
        }
    }

    private Document convertirAJson(Object objeto, Class<?> clase) throws IllegalAccessException {
        Document doc = new Document();
        Field[] campos = clase.getDeclaredFields();
        String idFieldName = clase.getSimpleName().toLowerCase() + "_id";

        for (Field campo : campos) {
            campo.setAccessible(true); // Permitir acceso a campos privados
            String nombreCampo = campo.getName();
            Object valorCampo = campo.get(objeto);

            if (nombreCampo.equals(idFieldName)) {
                doc.append("_id", valorCampo); // Si el campo es el id, se añade como _id
            } else {
                doc.append(nombreCampo, valorCampo);
            }
        }
        return doc;
    }

    // buscar un objeto por el valor de un campo específico
    public <T> T findByFieldAndPrint(Class<T> clase, String fieldName, Object value) {
        String nombreColeccion = clase.getSimpleName().toLowerCase();
        MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);
        Document query = new Document(fieldName, value);

        try {
            Document doc = coleccion.find(query).first();
            if (doc != null) {
                T instancia = buildInstance(clase, doc);
               // System.out.println("Documento encontrado:");
                System.out.println(doc.toJson());
                return instancia;
            }
         else {
            System.out.println("No se encontró ningún objeto");
        }
            
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            System.err.println("Error al buscar el objeto en MongoDB: " + e.getMessage());
        }
        return null;
    }
    // public <T> TRABAJADOR findByFieldAndPrint(Class<T> clase, String fieldName, Object value) {
    //     String nombreColeccion = clase.getSimpleName().toLowerCase();
    //     MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);
    //     Document query = new Document(fieldName, value);

    //     try {
    //         Document doc = coleccion.find(query).first();
    //         if (doc != null) {
    //             System.out.println("Documento encontrado:");
    //             System.out.println(doc.toJson());
    //         } else {
    //             System.out.println("No se encontró ningún documento con " + fieldName + " igual a " + value);
    //         }
    //     } catch (Exception e) {
    //         System.err.println("Error al buscar el objeto en MongoDB: " + e.getMessage());
    //     }
    //     return null;
    // }

    // Seleccionar todo
    public <T> List<T> selectAll(Class<T> clase) {
        List<T> resultados = new ArrayList<>();
        String nombreColeccion = clase.getSimpleName().toLowerCase();
        MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);

        try (MongoCursor<Document> cursor = coleccion.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                T instancia = buildInstance(clase, doc);
                resultados.add(instancia);
            }
 
        } catch (Exception e) {
            System.err.println("Error al recuperar objetos de la colección: " + e.getMessage());
        }
        return resultados;
    }
    // Seleccionar uno
    public static <T> T selectById(Class<T> clase, Object id) {
        String nombreColeccion = clase.getSimpleName().toLowerCase();
        MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);
        Document query = new Document("_id", id);

        try {
            Document doc = coleccion.find(query).first();
            if (doc != null) {
                return buildInstance(clase, doc);
            }
        } 
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            System.err.println("Error al recuperar el objeto de la colección: " + e.getMessage());
        }
        return null;
    }

    private static <T> T buildInstance(Class<T> clase, Document doc) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Constructor<T> constructor = clase.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instancia = constructor.newInstance();
        Field[] campos = clase.getDeclaredFields();

        for (Field campo : campos) {
            campo.setAccessible(true);
            String nombreCampo = campo.getName();
            Object valorCampo = doc.get(nombreCampo);

            if (valorCampo == null && nombreCampo.equals(clase.getSimpleName().toLowerCase() + "_id")) {
                valorCampo = doc.get("_id");
            }

            campo.set(instancia, valorCampo);
        }

        return instancia;
    }
    //  Eliminar Colección
    public <T> void delete(Class<T> clase, Object id) {
        try {
            String nombreColeccion = clase.getSimpleName().toLowerCase();
            MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);
            Document query = new Document("_id", id);
    
            coleccion.deleteOne(query);
            System.out.println("Documento eliminado de la colección " + nombreColeccion + " correctamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar el objeto de MongoDB: " + e.getMessage());
        }
    }
    // Modificar
    public void modify(Object objeto) {
        try {
            Class<?> clase = objeto.getClass();
            String nombreColeccion = clase.getSimpleName().toLowerCase();
            MongoCollection<Document> coleccion = conexion.getCollection(nombreColeccion);

            // Convertir el objeto a un documento MongoDB
            Document doc = convertirAJson(objeto, clase);
            String idFieldName = clase.getSimpleName().toLowerCase() + "_id";
            Field idField = clase.getDeclaredField(idFieldName);
            idField.setAccessible(true);
            Object id = idField.get(objeto);

            if (id == null) {
                throw new IllegalArgumentException("El campo ID no puede ser nulo");
            }

            // Reemplazar el documento en la colección
            coleccion.replaceOne(Filters.eq("_id", id), doc);
            System.out.println("Documento actualizado en la colección " + nombreColeccion + " correctamente.");
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            System.err.println("Error al actualizar el objeto en MongoDB: " + e.getMessage());
        }
    }
}