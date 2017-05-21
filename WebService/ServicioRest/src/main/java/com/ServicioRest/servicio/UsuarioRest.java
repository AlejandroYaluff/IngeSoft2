/*
 * 
 */
package com.ServicioRest.servicio;

import com.ServicioRest.entities.Usuarios;
import com.ServicioRest.sessions.AbstractFacade;
import com.ServicioRest.sessions.UsuariosFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * 
 */
@javax.ejb.Stateless
@Path("usuario")
public class UsuarioRest extends AbstractFacade<Usuarios> {
  
    private EntityManager em;
    
    @EJB
    private UsuariosFacade ejbUsuariosFacade;
    
    public UsuarioRest() {
        super(Usuarios.class);
    }
    
    @GET
    @Path("/getusuarios")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuarios>findALL() {
        return ejbUsuariosFacade.findAll();
    }
    
    @POST
    @Path("/addusuario")
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Usuarios usuario) {
        ejbUsuariosFacade.create(usuario);
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/editusuario/{id}")
    public void edit(@PathParam("id")Integer id, Usuarios usuario) {
        ejbUsuariosFacade.edit(usuario);
    }
    
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/deleteusuario/{id}")
    public void remove(@PathParam("id")Integer id) {
        ejbUsuariosFacade.remove(ejbUsuariosFacade.find(id));
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getusuariobyid/{id}")
    public Usuarios findById (@PathParam("id")Integer id) {
        return ejbUsuariosFacade.find(id);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        em = Persistence.createEntityManagerFactory("com.mycompany_ServicioRest_war_1.0-SNAPSHOTPU").createEntityManager();
        return em;
    }
    
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/validarusuario")
    public String validarusuario (@QueryParam("correo") String correo) throws IOException {
        String valido = ejbUsuariosFacade.validarcorreo(correo);
        return valido;
    }
    
    
} 

