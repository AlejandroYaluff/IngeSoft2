/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServicioRest.servicio;

/**
 *
 * 
 */
import com.ServicioRest.entities.Hijos;
import com.ServicioRest.sessions.AbstractFacade;
import com.ServicioRest.sessions.HijosFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ejb.EJB;
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
@Stateless
@Path("hijo")
public class HijoRest extends AbstractFacade<Hijos> {

    
    private EntityManager em;
    
    @EJB
    private HijosFacade ejbHijosFacade;

    public HijoRest() {
        super(Hijos.class);
    }

    @POST
    @Override
    @Path("/addhijo")
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Hijos entity) {
        super.create(entity);
    }

    @PUT
    @Path("/edithijo/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Hijos entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("/deletehijo/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("/gethijobyid/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Hijos find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("/gethijos")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Hijos> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        em = Persistence.createEntityManagerFactory("com.mycompany_ServicioRest_war_1.0-SNAPSHOTPU").createEntityManager();
        return em;
    }
    
    @GET
    @Path("/obtenerhijos/{idpadre}")
    @Produces({MediaType.APPLICATION_JSON})
    public String obtenerhijos(@PathParam("idpadre") Integer idpadre) {
        //return super.obtenerlistahijos(idpadre);
        return ejbHijosFacade.obtenerlistahijos(idpadre);
    }
    
}
