/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciorest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/* Clase que contiene los servicios disponibles
   en el web service
*/

public class Services {
    
    ConectarDB conexion;
    Connection conectar;
    public Services() {
        conexion = new ConectarDB();
        conectar = null;
    }

    // Agregar un usuario //
    void agregarUsuario(DatosUsuario usuario) throws SQLException, ClassNotFoundException {
        String query="insert into \"Usuarios\" (nombre,correo) values(?,?)";
        conectar = conexion.conectarDB();
            
        PreparedStatement consulta=conectar.prepareStatement(query);
        consulta.setString(1,usuario.getNombre());
        consulta.setString(2,usuario.getCorreo());   
        consulta.execute();
        consulta.close();
        conectar.close();
        conexion.cerrarDB();    
    }
    
    // Eliminar un usuario //
    public void borrarUsuario(int id) throws ClassNotFoundException, SQLException {
        String query="delete from \"Usuarios\" where id = ?";
        conectar = conexion.conectarDB();
        
        PreparedStatement consulta = conectar.prepareStatement(query);
        consulta.setInt(1, id);
        consulta.executeUpdate();
        consulta.close();
        conectar.close();
        conexion.cerrarDB();
    }
    
    // Modificar un usuario //
    public void editarUsuario(int id, DatosUsuario usuario) throws SQLException, ClassNotFoundException {
        String query = "update \"Usuarios\" set nombre = ?, correo = ? where id = ?";
        conectar = conexion.conectarDB();
        
        PreparedStatement consulta = conectar.prepareStatement(query);
        consulta.setString(1, usuario.getNombre());
        consulta.setString(2, usuario.getCorreo());
        consulta.setInt(3, id);
        consulta.executeUpdate();
        consulta.close();
        conectar.close();
        conexion.cerrarDB();
    }
    
    // Retornar lista de usuarios registrados //
    public ArrayList<DatosUsuario> getUsuarios() throws SQLException, ClassNotFoundException {
        ArrayList<DatosUsuario> lista = new ArrayList();
        conectar = conexion.conectarDB();
        Statement consulta = conectar.createStatement();
        ResultSet result = consulta.executeQuery("select nombre,correo from \"Usuarios\"");
        while (result.next()) {
            DatosUsuario obj = new DatosUsuario ();
            obj.setNombre(result.getString("nombre"));
            obj.setCorreo(result.getString("correo"));
            lista.add(obj);
        }
        conectar.close();
        conexion.cerrarDB();
        return lista;
    }
    
    // Retornar lista de hijos registrados //
    public ArrayList<DatosHijo> getHijos(int idusuario) throws SQLException, ClassNotFoundException {
        ArrayList<DatosHijo> lista = new ArrayList();
        conectar = conexion.conectarDB();
        Statement consulta = conectar.createStatement();
        String query = "select nombre,apellido,fecha_nacimiento::varchar fecha,"
                   + "lugar_nacimiento, id_hijo::varchar id_hijo, barrio, responsable,"
                   + "sexo, direccion, nacionalidad, departamento, municipio, "
                   + "referencia_domicilio, responsable, referencia_domicilio, "
                   + "telefono_contacto, seguro_medico, alergia "
                   + "from \"Hijos\" "
                   + "where id_usuario = "+idusuario;
        ResultSet result = consulta.executeQuery(query);
        while (result.next()) {
            DatosHijo obj = new DatosHijo();
            obj.setId(result.getString("id_hijo"));
            obj.setNombre(result.getString("nombre"));
            obj.setApellido(result.getString("apellido"));
            obj.setSexo(result.getString("sexo"));
            obj.setFechaNacimiento(result.getString("fecha"));
            obj.setLugarNacimiento(result.getString("lugar_nacimiento"));
            obj.setDireccion(result.getString("direccion"));
            obj.setNacionalidad(result.getString("nacionalidad"));
            obj.setMunicipio(result.getString("municipio"));
            obj.setDepartamento(result.getString("departamento"));
            obj.setBarrio(result.getString("barrio"));
            obj.setReferenciaDomicilio(result.getString("referencia_domicilio"));
            obj.setResponsable(result.getString("responsable"));
            obj.setTelefonoContacto(result.getString("telefono_contacto"));
            obj.setSeguroMedico(result.getString("seguro_medico"));
            obj.setAlergia(result.getString("alergia"));
            lista.add(obj);
        }
        conectar.close();
        conexion.cerrarDB();
        return lista;
    }
    
    // Retornar lista de vacunas //
    public ArrayList<DatosVacuna> getVacunas(int idusuario) throws SQLException, ClassNotFoundException {
        ArrayList<DatosVacuna> lista = new ArrayList();
        conectar = conexion.conectarDB();
        Statement consulta = conectar.createStatement();
        String query = "select rv.estado, coalesce(rv.fecha::varchar,'') fecha, v.nombre, "
                   + "coalesce(rv.responsable,'') responsable, "
                   + "v.id_vacuna, rv.id_hijo::varchar id_hijo, rv.dosis, rv.edad_meses, coalesce(rv.lote,'') lote "
                   + "from \"RegistroVacuna\" rv "
                   + "join \"Vacunas\" v on v.id_vacuna=rv.id_vacuna "
                   + "join \"Hijos\" h on rv.id_hijo = h.id_hijo "
                   + "where h.id_usuario = "+idusuario;
        ResultSet result = consulta.executeQuery(query);
        while (result.next()) {
            DatosVacuna obj = new DatosVacuna();
            obj.setNombreVacuna(result.getString("nombre"));
            obj.setEstado(result.getInt("estado"));
            obj.setFecha(result.getString("fecha"));
            obj.setDosis(result.getInt("dosis"));
            obj.setEdad(result.getInt("edad_meses"));
            obj.setLote(result.getString("lote"));
            obj.setHijoId(result.getString("id_hijo"));
            obj.setVacunaId(result.getInt("id_vacuna"));
            obj.setResponsable(result.getString("responsable"));
            
            lista.add(obj);
        }
        conectar.close();
        conexion.cerrarDB();
        return lista;
    }
    
    // Retornar lista de fechas de aplicacion de las vacunas // 
    public ArrayList<DatosFecha> getFechasVacunas(int idusuario) throws SQLException, ClassNotFoundException {
        ArrayList<DatosFecha> lista = new ArrayList();
        conectar = conexion.conectarDB();
        Statement consulta = conectar.createStatement();
        String query = "select DISTINCT(coalesce(rv.fecha::varchar,'')) fecha "
                  
                   
                   + "from \"RegistroVacuna\" rv "
                   + "join \"Vacunas\" v on v.id_vacuna=rv.id_vacuna "
                   + "join \"Hijos\" h on rv.id_hijo = h.id_hijo "
                   + "where h.id_usuario = "+idusuario + "and rv.estado = 0";
        ResultSet result = consulta.executeQuery(query);
        while (result.next()) {
            DatosFecha obj = new DatosFecha();
            
            obj.setFecha(result.getString("fecha"));
            
            lista.add(obj);
        }
        conectar.close();
        conexion.cerrarDB();
        return lista;
    }
    
    // Retornar usuario por id //
    public DatosUsuario getUsuarioById(int id) throws ClassNotFoundException, SQLException {
        conectar = conexion.conectarDB();
        Statement consulta = conectar.createStatement();
        ResultSet result = consulta.executeQuery("select nombre,correo from \"Usuarios\" where id = "+id);
        DatosUsuario usuario = new DatosUsuario();
        while (result.next()) {
            usuario.setNombre(result.getString("nombre"));
            usuario.setCorreo(result.getString("correo"));
        }
        conectar.close();
        conexion.cerrarDB();
        return usuario;
    }
    
    // Validar usuario por correo //
    public DatosUsuario validarUsuario(String correo) throws ClassNotFoundException, SQLException {
        conectar = conexion.conectarDB();
        DatosUsuario usuario = new DatosUsuario();
        Statement consulta = conectar.createStatement();
        ResultSet result = consulta.executeQuery("select id_usuario, nombre, correo from \"Usuarios\" where correo = '"+correo+"'");
        if (result.next()) {
            usuario.setId_usuario(result.getInt(1));
            usuario.setNombre(result.getString(2));
            usuario.setCorreo(result.getString(3));
            
        }
        else {
            usuario = null;
        }
        return usuario;
    }
}

