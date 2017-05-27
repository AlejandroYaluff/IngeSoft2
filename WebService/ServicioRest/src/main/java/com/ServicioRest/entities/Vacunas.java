/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ServicioRest.entities;

/**
 *
 * 
 */

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * 
 */
@Entity
@Table(name = "vacunas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacunas.findAll", query = "SELECT v FROM Vacunas v")
    , @NamedQuery(name = "Vacunas.findById", query = "SELECT v FROM Vacunas v WHERE v.id = :id")
    , @NamedQuery(name = "Vacunas.findByIdHijo", query = "SELECT v FROM Vacunas v WHERE v.idhijo = :idhijo")
    , @NamedQuery(name = "Vacunas.findByNombre", query = "SELECT v FROM Vacunas v WHERE v.nombre = :nombre")
    , @NamedQuery(name = "Vacunas.findByFechaAplicacion", query = "SELECT v FROM Vacunas v WHERE v.fecha_aplicacion = :fecha_aplicacion")
    , @NamedQuery(name = "Vacunas.findByAplicada", query = "SELECT v FROM Vacunas v WHERE v.aplicada = :aplicada")})
public class Vacunas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //@Size(max = 32)
    @Column(name = "idhijo")
    private Integer idhijo;
    //@Size(max = 15)
    @Column(name = "nombre")
    private String nombre;
    //@Size(max = 8)
    @Column(name = "fecha_aplicacion")
    private String fecha_aplicacion;
    @Column(name = "aplicada")
    private String aplicada;
    

    public Vacunas() {
    }

    public Vacunas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdHijo() {
        return idhijo;
    }

    public void setIdHijo(Integer idhijo) {
        this.idhijo = idhijo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaAplicacion() {
        return fecha_aplicacion;
    }

    public void setFechaAplicacion(String fecha_aplicacion) {
        this.fecha_aplicacion = fecha_aplicacion;
    }

    public String getAplicada() {
        return aplicada;
    }

    public void setAplicada(String aplicada) {
        this.aplicada = aplicada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacunas)) {
            return false;
        }
        Vacunas other = (Vacunas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ServicioRest.entities.Vacunas[ id=" + id + " ]";
    }
    
}
