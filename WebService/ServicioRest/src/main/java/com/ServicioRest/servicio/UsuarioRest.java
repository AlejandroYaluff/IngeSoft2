/*
 * 
 */
package com.ServicioRest.servicio;

import com.ServicioRest.entities.Usuarios;
import com.ServicioRest.sessions.UsuariosFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * 
 */
@Path("usuario")
public class UsuarioRest {
    @EJB
    private UsuariosFacade ejbUsuariosFacade;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuarios>findALL() {
        return ejbUsuariosFacade.findAll();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Usuarios usuario) {
        ejbUsuariosFacade.create(usuario);
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public void edit(@PathParam("id")Integer id, Usuarios usuario) {
        ejbUsuariosFacade.edit(usuario);
    }
    
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public void remove(@PathParam("id")Integer id) {
        ejbUsuariosFacade.remove(ejbUsuariosFacade.find(id));
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Usuarios findById (@PathParam("id")Integer id) {
        return ejbUsuariosFacade.find(id);
    }
    
}

