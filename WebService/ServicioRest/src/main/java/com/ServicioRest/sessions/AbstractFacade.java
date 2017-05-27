/*
 * 
 */
package com.ServicioRest.sessions;

import com.ServicioRest.entities.Hijos;
import com.ServicioRest.entities.Usuarios;
import static com.ServicioRest.entities.Usuarios_.correo;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.validator.Validator;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;


/**
 *
 * 
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    
    public String validarcorreo (String correo) throws IOException {
        
        String validado;
        try {
            validado = getEntityManager().createNativeQuery("SELECT u.id FROM Usuarios u WHERE u.correo = '"+correo+"'").getSingleResult().toString();
            
        } catch (NoResultException e) {
            validado = "novalido";
        }
        return validado;
        
    }
    
    public String obtenerlistahijos(Integer idpadre) {
        return getEntityManager().createNativeQuery("SELECT to_json(h.*) FROM Hijos h WHERE h.idpadre = '"+idpadre+"'").getResultList().toString();
    }
    
    public String obtenerlistavacunas(Integer idhijo) {
        return getEntityManager().createNativeQuery("SELECT to_json(v.*) FROM Vacunas v WHERE v.idhijo = '"+idhijo+"'").getResultList().toString();
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
}
