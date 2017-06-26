/*
 * 
 */
package com.ServicioRest.sessions;

import com.ServicioRest.entities.Hijos;
import com.ServicioRest.entities.Usuarios;
import static com.ServicioRest.entities.Usuarios_.correo;
import com.ServicioRest.entities.Vacunas;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.validator.Validator;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.json.simple.JSONObject;


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
    
    public String obtenerfechanotificacion () {
        
        String diferencia = "";
        String diferencia2 = "";
        
        
        
        try {
            diferencia = getEntityManager().createNativeQuery("SELECT (TO_DATE(v.fecha_aplicacion, 'DD-MM-YYYY') - current_date) FROM Vacunas v").getResultList().toString();
            int tam = diferencia.length();
            //String diferencia2 = "";
            char buf[] = new char[tam-2];
            diferencia.getChars(2, tam-1, buf, 1);
            for (int i=1;i<tam-2;i++) {
                diferencia2 = diferencia2 + buf[i];
            }
            //List<String> listadias = new ArrayList<String>(Arrays.asList(diferencia2.split(",")));
            //System.out.println(listadias);
            
            //List<String> listadias = new ArrayList<String>(Arrays.asList(diferencia.split(",")));
            //  || diferencia.charAt(i) != ']')
            /*for (int i=0;i<diferencia.length();i++) {
                char convertir = diferencia.charAt(i);
                if (diferencia.charAt(i) != letra || diferencia.charAt(i) != letra2) {
                    diferencia2 = diferencia2 + diferencia.charAt(i);
                    System.out.println(diferencia.charAt(i));
                }
            }*/
            
        } catch (NoResultException e) {
            //diferencia = 0;
            
        }
        return diferencia2;
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
    
    public String ordenar (Integer idhijo, Integer eleccion) {
        
        List<T> lista = null;
        switch (eleccion) {
            case 1:
                lista = getEntityManager().createNativeQuery("SELECT to_json(v.*) FROM Vacunas v WHERE v.idhijo = '"+idhijo+"' order by v.nombre").getResultList();
                break;
            case 2:
                lista = getEntityManager().createNativeQuery("SELECT to_json(v.*) FROM Vacunas v WHERE v.idhijo = '"+idhijo+"' order by TO_DATE(v.fecha_aplicacion, 'DD-MM-YYYY')").getResultList();
                break;
        }
        return lista.toString();
    }
    
    public String filtrar (Integer idhijo, Integer eleccion) {
        
        List<T> lista = null;
        String listanueva = "";
        String listamodificada = "";
        switch (eleccion) {
            case 1:
                lista = getEntityManager().createNativeQuery("SELECT to_json (v.nombre) FROM Vacunas v WHERE v.idhijo = '"+idhijo+"'").getResultList();
                break;
            case 2:
                lista = getEntityManager().createNativeQuery("SELECT to_json(v.fecha_aplicacion) FROM Vacunas v WHERE v.idhijo = '"+idhijo+"'").getResultList();
                break;
        }
        listamodificada = lista.toString();
        int tam = listamodificada.length();
            char buf[] = new char[tam-2];
            listamodificada.getChars(1, tam-1, buf, 0);
            for (int i=0;i<tam-2;i++) {
                listanueva = listanueva + buf[i];
            }
        return listanueva;
    }
    
    public Usuarios validateuser (String correo) throws ClassNotFoundException, SQLException{ 
        Usuarios u = new Usuarios();
        // instanciacion del objeto CriteriaBuilder
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        //Construccion de la consulta 
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("correo"), correo));
        Query query = getEntityManager().createQuery(criteriaQuery);
        //Object lista = query.getSingleResult();
        List<Usuarios> resul = query.getResultList();
        for (Usuarios c : resul) {
           
            u.setId(c.getId());
            u.setNombre(c.getNombre());
            u.setCorreo(c.getCorreo());
        }
        
        return u;
     
    }
    
    public ArrayList<Hijos> obtenerhijos (int idpadre) throws ClassNotFoundException, SQLException{ 
        ArrayList<Hijos> hijos = new ArrayList();
        // instanciacion del objeto CriteriaBuilder
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        //Construccion de la consulta 
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("idpadre"), idpadre));
        Query query = getEntityManager().createQuery(criteriaQuery);
        //Object lista = query.getSingleResult();
        List<Hijos> resul = query.getResultList();
        for (Hijos c : resul) {
            Hijos lista = new Hijos();
            lista.setId(c.getId());
            lista.setNombre(c.getNombre());
            lista.setSexo(c.getSexo());
            lista.setEdad(c.getEdad());
            hijos.add(lista);
        }
        
        return hijos;
     
    }
    
    
    public ArrayList<Vacunas> obtenervacunas (int idhijo) throws ClassNotFoundException, SQLException{ 
        ArrayList<Vacunas> vacunas = new ArrayList();
        // instanciacion del objeto CriteriaBuilder
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        //Construccion de la consulta 
        CriteriaQuery criteriaQuery;
        criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(employee.get("idhijo"), idhijo));
        Query query = getEntityManager().createQuery(criteriaQuery);
        //Object lista = query.getSingleResult();
        List<Vacunas> resul = query.getResultList();
        for (Vacunas c : resul) {
            Vacunas lista = new Vacunas();
            lista.setNombre(c.getNombre());
            lista.setFechaAplicacion(c.getFechaAplicacion());
            lista.setAplicada(c.getAplicada());
            vacunas.add(lista);
        }
        
        return vacunas;
     
    }
    

    
}
