/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviciorest;

/* Clase que maneja las fechas de aplicacion de las vacunas
*/

public class DatosFecha {
    String fechavacuna;
    public DatosFecha(){
        fechavacuna = "";
    }

    public DatosFecha(String fechavacuna) {
        this.fechavacuna = fechavacuna;
    }

    public String getFecha() {
        return fechavacuna;
    }

    public void setFecha(String fechavacuna) {
        this.fechavacuna = fechavacuna;
    }

   
    
}
