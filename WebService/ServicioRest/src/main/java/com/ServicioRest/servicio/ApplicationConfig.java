/*
 * 
 */
package com.ServicioRest.servicio;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * 
 */
@ApplicationPath("webresources")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
       
        resources.add(com.ServicioRest.servicio.HijoRest.class);
        resources.add(com.ServicioRest.servicio.UsuarioRest.class);
        resources.add(com.ServicioRest.servicio.VacunaRest.class);
    }
    
}
