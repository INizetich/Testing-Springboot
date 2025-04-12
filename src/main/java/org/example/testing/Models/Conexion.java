package org.example.testing.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final static String URL = "jdbc:mysql://shinkansen.proxy.rlwy.net:19447/";
    private final static String DB_NAME = "railway";
    private final static String USER = "root";
    private final static String PASS = "GyfQLZCHyJOrRYVKQXIwsGXCnndgLYSw";
    private static Conexion instancia;
    private Connection conn;

    private Conexion(){
        try{
            conn = DriverManager.getConnection(URL + DB_NAME, USER,PASS);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static Conexion getInstancia(){
        if (instancia ==null){
            instancia = new Conexion();
        }

        return instancia;
    }

    public Connection getConnection(){
        return conn;
    }
}
