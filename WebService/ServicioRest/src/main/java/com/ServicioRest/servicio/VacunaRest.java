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
import com.ServicioRest.entities.Vacunas;
import com.ServicioRest.sessions.VacunasFacade;
import com.ServicioRest.sessions.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
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
@Path("vacuna")
public class VacunaRest extends AbstractFacade<Vacunas> {

    
    private EntityManager em;
    
    @EJB
    private VacunasFacade ejbVacunasFacade;

    public VacunaRest() {
        super(Vacunas.class);
    }

    @POST
    @Override
    @Path("/addvacuna")
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Vacunas entity) {
        super.create(entity);
    }

    @PUT
    @Path("/editvacuna/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Vacunas entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("/deletevacuna/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("/getvacunabyid/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Vacunas find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("/getvacunas")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Vacunas> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        em = Persistence.createEntityManagerFactory("com.mycompany_ServicioRest_war_1.0-SNAPSHOTPU").createEntityManager();
        return em;
    }
    
    @GET
    @Path("/obtenervacunas/{idhijo}")
    @Produces({MediaType.APPLICATION_JSON})
    public String obtenervacunas(@PathParam("idhijo") Integer idhijo) {
        //return super.obtenerlistahijos(idpadre);
        return ejbVacunasFacade.obtenerlistavacunas(idhijo);
    }
    
}

