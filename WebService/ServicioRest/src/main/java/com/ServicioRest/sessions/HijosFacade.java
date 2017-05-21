/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServicioRest.sessions;

import com.ServicioRest.entities.Hijos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * 
 */
@Stateless
public class HijosFacade extends AbstractFacade<Hijos> {
    @PersistenceContext(unitName = "com.mycompany_ServicioRest_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HijosFacade() {
        super(Hijos.class);
    }
    
}
