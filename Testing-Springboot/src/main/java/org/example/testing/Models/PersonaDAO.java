package org.example.testing.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private Connection conn;

    public PersonaDAO() {
        conn = Conexion.getInstancia().getConnection();
    }

    public void insertarPersona(Persona persona) {
        String sql = "INSERT INTO personas(nombre,apellido,dni,email) VALUES(?,?,?,?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, persona.getNombre());
            preparedStatement.setString(2, persona.getApellido());
            preparedStatement.setString(3, persona.getDni());
            preparedStatement.setString(4, persona.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Persona> obtenerTodasLasPersonas() {
        Persona persona = null;
        List<Persona> listaPersonas = new ArrayList<>();
        String sql = "SELECT * FROM personas";
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                persona = new Persona(rs.getString("nombre"), rs.getString("apellido"), rs.getString("dni"), rs.getString("email"));
                listaPersonas.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPersonas;
    }

    public boolean eliminarPersona(String dni) {
        String sql = "DELETE FROM personas WHERE dni="+dni;
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
