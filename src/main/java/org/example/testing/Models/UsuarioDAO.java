package org.example.testing.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        conn = Conexion.getInstancia().getConnection();
    }

    public void insertarPersona(Usuario usuario) {
        String sql = "INSERT INTO personas(nombre,apellido,dni,email) VALUES(?,?,?,?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getDni());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Usuario> obtenerTodasLasPersonas() {
        Usuario usuario = null;
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM personas";
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                usuario = new Usuario(rs.getString("nombre"), rs.getString("apellido"), rs.getString("dni"), rs.getString("email"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaUsuarios;
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
