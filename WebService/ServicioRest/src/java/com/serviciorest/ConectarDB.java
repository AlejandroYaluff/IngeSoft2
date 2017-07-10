/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciorest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Clase que sirve de conexion entre la base de datos postgres y 
   el web service 
*/

public class ConectarDB {
    Connection conexion;
    String usuario;
    String password;
    String servidor;
    String database;
    public ConectarDB () {
        conexion = null;
        usuario = "postgres";
        password = "postgres";
        servidor = "localhost:5432";
        database = "servicios";
    }
    
    public Connection conectarDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String urlconexion="jdbc:postgresql://"+servidor+"/"+database;
        conexion = DriverManager.getConnection(urlconexion, usuario, password);
        return conexion;      
    }
    
    public void cerrarDB() throws SQLException {
        conexion.close();
    }
}

