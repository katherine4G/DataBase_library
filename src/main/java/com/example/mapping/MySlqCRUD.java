package com.example.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySlqCRUD {

    private final Connection conexion;

    public MySlqCRUD(Connection conexion) {
        this.conexion = conexion;
    }
    public void insertar(Object objeto) {
        try {
            Class<?> clase = objeto.getClass();
            String nombreTabla = clase.getSimpleName().toLowerCase(); // Suponemos que el nombre de la tabla es igual al nombre de la clase en minúsculas
            try {
                // Crear tabla si no existe
                crearTablaSiNoExiste(nombreTabla, clase);
            } catch (SQLException e) {
                System.err.println("Error al crear la tabla: " + e.getMessage());
            }

            // Insertar datos en la tabla
            insertarDatos(nombreTabla, clase, objeto);
            System.out.println("Datos insertados en la tabla " + nombreTabla + " correctamente."); // Agregado mensaje de éxito
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error al mapear la clase a la tabla: " + e.getMessage());
        }
    }

    private boolean tablaExiste(String nombreTabla) throws SQLException {
        String query = "SELECT count(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombreTabla.toLowerCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private void crearTablaSiNoExiste(String nombreTabla, Class<?> clase) throws SQLException {
        if (tablaExiste(nombreTabla)) {
            System.out.println("La tabla " + nombreTabla + " ya existe.");
            return;
        }

        StringBuilder query = new StringBuilder("CREATE TABLE ")
                .append(nombreTabla)
                .append(" (");

        Field[] campos = clase.getDeclaredFields();
        boolean tieneClavePrimaria = false;
        for (Field campo : campos) {
            campo.setAccessible(true); // Permitir acceso a campos privados
            String nombreCampo = campo.getName();
            String tipoDato = obtenerTipoDato(campo.getType());
            query.append(nombreCampo).append(" ").append(tipoDato);
            // Verificar si es clave primaria (para que tenga llave primaria debe llamarse nombredelaclase_id, todo en minuscula)
            if (nombreCampo.equals(nombreTabla + "_id")) {
                query.append(" PRIMARY KEY");
                tieneClavePrimaria = true;
            }
            query.append(", ");
        }

        // Eliminar la coma y el espacio extra al final de la definición de la tabla
        query.delete(query.length() - 2, query.length());
        query.append(")");

        if (!tieneClavePrimaria) {
            throw new SQLException("La tabla " + nombreTabla + " no tiene un campo clave primaria.");
        }

        try (PreparedStatement statement = conexion.prepareStatement(query.toString())) {
            statement.executeUpdate();
            System.out.println("Tabla " + nombreTabla + " creada correctamente.");
        }
    }

    private String obtenerTipoDato(Class<?> tipo) {
        if (tipo == String.class) {
            return "VARCHAR(255)";
        } else if (tipo == int.class || tipo == Integer.class) {
            return "INT";
        } else if (tipo == double.class || tipo == Double.class) {
            return "DOUBLE";
        } else if (tipo == float.class || tipo == Float.class) {
            return "FLOAT";
        } else if (tipo == boolean.class || tipo == Boolean.class) {
            return "BOOLEAN";
        } else {
            return "VARCHAR(255)"; // Por defecto, se considera como String
        }
    }

    public void insertarSiNoExiste(Object objeto) {
        try {
            // Verificar si el objeto ya existe en la base de datos
            if (existeObjeto(objeto)) {
                System.out.println("El objeto ya ha sido creado previamente.");
                return; // Salir del método si el objeto ya existe
            }
            
            // Insertar el objeto si no existe
            insertar(objeto);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error al insertar el objeto: " + e.getMessage());
        }
    }
    private boolean existeObjeto(Object objeto) throws SQLException, IllegalAccessException {
        Class<?> clase = objeto.getClass();
        String nombreTabla = clase.getSimpleName().toLowerCase(); // Suponemos que el nombre de la tabla es igual al nombre de la clase en minúsculas
        
        // Construir la consulta SQL para verificar si el objeto ya existe en la base de datos
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM ")
                                    .append(nombreTabla)
                                    .append(" WHERE ");

        Field[] campos = clase.getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            String nombreCampo = campo.getName();
            Object valorCampo = campo.get(objeto);

            // Agregar cada campo y su valor a la consulta
            query.append(nombreCampo).append(" = '").append(valorCampo).append("' AND ");
        }

        // Eliminar el "AND" adicional al final de la consulta
        query.delete(query.length() - 5, query.length());

        // Ejecutar la consulta SQL
        try (PreparedStatement statement = conexion.prepareStatement(query.toString())) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Retorna true si existe al menos un registro, false de lo contrario
                }
            }
        }

        return false; // Si no se encontraron registros
    }
    private void insertarDatos(String nombreTabla, Class<?> clase, Object objeto) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("INSERT INTO ").append(nombreTabla).append(" (");
        StringBuilder values = new StringBuilder("VALUES (");

        Field[] campos = clase.getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            String nombreCampo = campo.getName();
            Object valorCampo = campo.get(objeto);

            query.append(nombreCampo).append(", ");
            values.append("'").append(valorCampo).append("', ");
        }

        // Eliminar la coma y el espacio extra al final de las listas de campos y valores
        query.delete(query.length() - 2, query.length());
        values.delete(values.length() - 2, values.length());

        query.append(") ");
        values.append(")");

        String insertQuery = query.toString() + values.toString();

        try (PreparedStatement statement = conexion.prepareStatement(insertQuery)) {
            statement.executeUpdate();
            System.out.println("Datos insertados en la tabla " + nombreTabla + " correctamente."); 
        }
    }

    
    public <T> void findByFieldAndPrint(Class<T> clase, String nombreCampo, Object valorCampo) {
        String nombreTabla = clase.getSimpleName().toLowerCase();

        String query = "SELECT * FROM " + nombreTabla + " WHERE " + nombreCampo + " = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setObject(1, valorCampo);
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean encontrado = false;
                while (resultSet.next()) {
                    encontrado = true;
                    System.out.println("Registro encontrado en la tabla " + nombreTabla + ":");
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        String columnName = resultSet.getMetaData().getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        System.out.println(columnName + ": " + columnValue);
                    }
                }
                if (!encontrado) {
                    System.out.println("No se encontró ningún registro en la tabla " + nombreTabla + " con " + nombreCampo + " = " + valorCampo + ".");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar y mostrar los registros en la tabla " + nombreTabla + ": " + e.getMessage());
        }
    }

    // SELECCIONAR 
    public <T> List<T> seleccionarTodo(Class<T> clase) {
        List<T> resultados = new ArrayList<>();
        String nombreTabla = clase.getSimpleName().toLowerCase();

        try {
            String query = "SELECT * FROM " + nombreTabla;
            try (PreparedStatement statement = conexion.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T instancia = construirInstancia(clase, resultSet);
                    resultados.add(instancia);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al recuperar objetos de la tabla: " + e.getMessage());
        }
        return resultados;
    }

    public <T> T seleccionarPorId(Class<T> clase, Object id) {
        String nombreTabla = clase.getSimpleName().toLowerCase();
        String nombreCampoId = nombreTabla + "_id";

        String query = "SELECT * FROM " + nombreTabla + " WHERE " + nombreCampoId + " = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return construirInstancia(clase, resultSet);
                } else {
                    System.out.println("No se encontró ningún registro con ID " + id + " en la tabla " + nombreTabla + ".");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al seleccionar el registro con ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    private <T> T construirInstancia(Class<T> clase, ResultSet resultSet) throws SQLException {
        try {
            Constructor<T> constructor = clase.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instancia = constructor.newInstance();

            Field[] campos = clase.getDeclaredFields();
            for (Field campo : campos) {
                campo.setAccessible(true);
                String nombreCampo = campo.getName();
                Object valorCampo = resultSet.getObject(nombreCampo);

                if (valorCampo != null) {
                    if (campo.getType() == int.class || campo.getType() == Integer.class) {
                        valorCampo = ((Number) valorCampo).intValue();
                    } else if (campo.getType() == double.class || campo.getType() == Double.class) {
                        valorCampo = ((Number) valorCampo).doubleValue();
                    } else if (campo.getType() == float.class || campo.getType() == Float.class) {
                        valorCampo = ((Number) valorCampo).floatValue();
                    } else if (campo.getType() == long.class || campo.getType() == Long.class) {
                        valorCampo = ((Number) valorCampo).longValue();
                    } else if (campo.getType() == boolean.class || campo.getType() == Boolean.class) {
                        valorCampo = (valorCampo instanceof Number) ? ((Number) valorCampo).intValue() != 0 : Boolean.valueOf(valorCampo.toString());
                    }

                    campo.set(instancia, valorCampo);
                }
            }

            return instancia;
        } catch (NoSuchMethodException e) {
            throw new SQLException("No se pudo encontrar el constructor predeterminado para la clase " + clase.getSimpleName() + ": " + e.getMessage(), e);
        } catch (InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new SQLException("Error al instanciar la clase " + clase.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    // ELIMINAR 
    public void eliminar(Class<?> clase, Object id) {
        String nombreTabla = clase.getSimpleName().toLowerCase();
        String nombreCampoId = nombreTabla + "_id";

        String query = "DELETE FROM " + nombreTabla + " WHERE " + nombreCampoId + " = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setObject(1, id);
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Registro con ID " + id + " eliminado correctamente de la tabla " + nombreTabla + ".");
            } else {
                System.out.println("No se encontró ningún registro con ID " + id + " en la tabla " + nombreTabla + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el registro con ID " + id + ": " + e.getMessage());
        }
    }

    // MODIFICAR 
    public void modificar(Object objeto) {
        try {
            Class<?> clase = objeto.getClass();
            String nombreTabla = clase.getSimpleName().toLowerCase();

            if (!tablaExiste(nombreTabla)) {
                System.err.println("La tabla " + nombreTabla + " no existe.");
                return;
            }

            modificarDatos(nombreTabla, clase, objeto);
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error al modificar la clase en la tabla: " + e.getMessage());
        }
    }

    private void modificarDatos(String nombreTabla, Class<?> clase, Object objeto) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("UPDATE ").append(nombreTabla).append(" SET ");

        Field[] campos = clase.getDeclaredFields();
        String nombreCampoId = nombreTabla + "_id";
        boolean encontrado = false;
        for (Field campo : campos) {
            campo.setAccessible(true);
            String nombreCampo = campo.getName();
            if (nombreCampo.equals(nombreCampoId)) {
                encontrado = true;
                continue; // Saltar el campo ID
            }

            query.append(nombreCampo).append(" = ?, ");
        }

        if (!encontrado) {
            throw new SQLException("No se encontró el campo ID en la clase " + clase.getSimpleName());
        }

        query.delete(query.length() - 2, query.length());
        query.append(" WHERE ").append(nombreCampoId).append(" = ?");

        try (PreparedStatement statement = conexion.prepareStatement(query.toString())) {
            int index = 1;
            for (Field campo : campos) {
                String nombreCampo = campo.getName();
                if (nombreCampo.equals(nombreCampoId)) {
                    continue; // Saltar el campo ID
                }
                Object valorCampo = campo.get(objeto);
                statement.setObject(index, valorCampo);
                index++;
            }

            Field idField;
            try {
                idField = clase.getDeclaredField(nombreCampoId);
                idField.setAccessible(true);
                Object id = idField.get(objeto);
                statement.setObject(index, id);
            } catch (NoSuchFieldException e) {
                throw new SQLException("No se encontró el campo ID en la clase " + clase.getSimpleName());
            }

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Datos modificados en la tabla " + nombreTabla + " correctamente.");
            } else {
                System.out.println("No se encontró ningún registro con el ID en la tabla " + nombreTabla + ".");
            }
        }
    }
}