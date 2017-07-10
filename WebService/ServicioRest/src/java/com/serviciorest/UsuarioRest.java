/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciorest;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;



@Path("usuario") 
public class UsuarioRest {
    
    Services servicio = new Services();
    
    @GET
    @Path("/getusuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<DatosUsuario> getUsuarios() throws ClassNotFoundException, SQLException {
        return servicio.getUsuarios();
    }
    
    
    @POST
    @Path("/agregarusuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public String agregarUsuario(DatosUsuario usu) throws SQLException, ClassNotFoundException {
        DatosUsuario usuario = new DatosUsuario();
        usuario.setNombre(usu.getNombre());
        usuario.setCorreo(usu.getCorreo());
        servicio.agregarUsuario(usuario);
        String result = "Usuario agregado: " + usuario.getNombre()+", "+usuario.getCorreo();
        
        return result;
    }
    
    
    @DELETE
    @Path("/borrarusuario")
    @Produces("text/plain")
    public String borrarUsuario(@QueryParam("id") int id) throws ClassNotFoundException, SQLException {
        servicio.borrarUsuario(id);
        String result = "Usuario eliminado";
        return result;
    }
   
    
    @PUT
    @Path("/editarusuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public String editarUsuario(@QueryParam("id") int id, DatosUsuario usuario) throws SQLException, ClassNotFoundException {
        servicio.editarUsuario(id, usuario);
        String result = "Usuario modificado";
        return result;
    }
    
    
    @GET
    @Path("/getusuariobyid")
    @Produces(MediaType.APPLICATION_JSON)
    public DatosUsuario getUsuarioById(@QueryParam("id") int id) throws ClassNotFoundException, SQLException {
        DatosUsuario usuario = new DatosUsuario();
        usuario = servicio.getUsuarioById(id);
        return usuario;
    }
    
    
    @POST
    @Path("/validarusuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DatosUsuario validarUsuario(DatosUsuario usu) throws ClassNotFoundException, SQLException {
        DatosUsuario usuario = new DatosUsuario();
        usuario = servicio.validarUsuario(usu.getCorreo());
        return usuario;
    }
    
    
    @POST
    @Path("/gethijos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<DatosHijo> getHijos(DatosUsuario usu) throws ClassNotFoundException, SQLException {
        
        ArrayList<DatosHijo> listahijos = new ArrayList();
        listahijos = servicio.getHijos(usu.getId_usuario());
        return listahijos;
    }
    
    
    @POST
    @Path("/getvacunas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<DatosVacuna> getVacunas(DatosUsuario usu) throws ClassNotFoundException, SQLException {
        
        ArrayList<DatosVacuna> listavacunas = new ArrayList();
        listavacunas = servicio.getVacunas(usu.getId_usuario());
        return listavacunas;
    }
    
    @GET
    @Path("/getfechasvacunas")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<DatosFecha> getFechaVacuna(@QueryParam("idusuario") int idusuario) throws ClassNotFoundException, SQLException {
        
        ArrayList<DatosFecha> listafechas = new ArrayList();
        listafechas = servicio.getFechasVacunas(idusuario);
        return listafechas;
    }
}

