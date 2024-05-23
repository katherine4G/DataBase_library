package com.example.mapping;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleData {

    private final Connection conexion;

    public OracleData(Connection conexion) {
        this.conexion = conexion;
    }

    public void mapearClaseATabla(Object objeto) {
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
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Error al mapear la clase a la tabla: " + e.getMessage());
        }
    }

    private boolean tablaExiste(String nombreTabla) throws SQLException {
        String query = "SELECT count(*) FROM user_tables WHERE table_name = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombreTabla.toUpperCase());
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
            // Verificar si es clave primaria (para que tenga llave primaria debe llamarse nombredelaclase_id, todo en minúscula)
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

    public <T> List<T> recuperarDeTabla(Class<T> clase) {
        List<T> resultados = new ArrayList<>();
        String nombreTabla = clase.getSimpleName().toLowerCase(); // Derivar el nombre de la tabla del nombre de la clase
        try {
            String query = "SELECT * FROM " + nombreTabla;
            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        T instancia = construirInstancia(clase, resultSet);
                        resultados.add(instancia);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al recuperar objetos de la tabla: " + e.getMessage());
        }
        return resultados;
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
                    // Convertir tipos si es necesario
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

    public void actualizarDatos(String nombreTabla, Object objeto, String clavePrimaria, Object valorClavePrimaria) throws SQLException, IllegalAccessException {
        StringBuilder query = new StringBuilder("UPDATE ").append(nombreTabla).append(" SET ");

        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            String nombreCampo = campo.getName();
            Object valorCampo = campo.get(objeto);

            if (!nombreCampo.equals(clavePrimaria)) { // Evitar actualizar la clave primaria
                query.append(nombreCampo).append(" = '").append(valorCampo).append("', ");
            }
        }

        // Eliminar la coma y el espacio extra al final de la lista de campos
        query.delete(query.length() - 2, query.length());
        query.append(" WHERE ").append(clavePrimaria).append(" = '").append(valorClavePrimaria).append("'");

        try (PreparedStatement statement = conexion.prepareStatement(query.toString())) {
            statement.executeUpdate();
            System.out.println("Datos actualizados en la tabla " + nombreTabla + " correctamente.");
        }
    }

    public void eliminarDatos(String nombreTabla, String clavePrimaria, Object valorClavePrimaria) throws SQLException {
        String query = "DELETE FROM " + nombreTabla + " WHERE " + clavePrimaria + " = ?";

        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setObject(1, valorClavePrimaria);
            statement.executeUpdate();
            System.out.println("Datos eliminados de la tabla " + nombreTabla + " correctamente.");
        }
    }
}
