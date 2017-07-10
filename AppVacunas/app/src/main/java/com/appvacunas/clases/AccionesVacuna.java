package com.appvacunas.clases;

import android.content.ContentValues;
import com.appvacunas.clases.DatosVacuna.VacunaTable;

/**
 * Clase que maneja las operaciones con los datos de las vacunas
 */

// Declaracion de las variables usadas para los datos de las vacunas
public class AccionesVacuna {
    private int id_vacuna;
    private String nombre;
    private int dosis;
    private int edad;
    private String fecha;
    private String lote;
    private String nombre_medico;
    private String descripcion;
    private String id_hijo;
    private int aplicada;

    public AccionesVacuna(int id_vacuna, String nombre, int dosis, int edad, String fecha, String lote,
                  String nombre_medico, String descripcion, String id_hijo, int aplicada) {
        this.id_vacuna = id_vacuna;
        this.nombre = nombre;
        this.dosis = dosis;
        this.edad = edad;
        this.fecha = fecha;
        this.lote = lote;
        this.nombre_medico = nombre_medico;
        this.descripcion = descripcion;
        this.id_hijo = id_hijo;
        this.aplicada=aplicada;
    }


    public AccionesVacuna (){
        id_vacuna = 0;
        nombre = "";
        dosis = 0;
        edad = 0;
        fecha = "";
        lote = "";
        nombre_medico = "";
        descripcion = "";
        id_hijo = "";
        aplicada = 0;
    }


    // Carga del contenedor con los datos correspondientes
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VacunaTable.ID, id_vacuna);
        values.put(VacunaTable.NOMBRE, nombre);
        values.put(VacunaTable.DOSIS, dosis);
        values.put(VacunaTable.EDAD,edad);
        values.put(VacunaTable.FECHA,fecha);
        values.put(VacunaTable.LOTE,lote);
        values.put(VacunaTable.NOMBRE_MEDICO,nombre_medico);
        values.put(VacunaTable.DESCRIPCION,descripcion);
        values.put(VacunaTable.ID_HIJO,id_hijo);
        values.put(VacunaTable.APLICADA,aplicada);

        return values;
    }

    // Getters y Setters de los datos de las vacunas
    public String getId_hijo() {
        return id_hijo;
    }

    public void setId_hijo(String id_hijo) {
        this.id_hijo = id_hijo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre_medico() {
        return nombre_medico;
    }

    public void setNombre_medico(String nombre_medico) {
        this.nombre_medico = nombre_medico;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAplicada() {
        return aplicada;
    }

    public void setAplicada(int aplicada) {
        this.aplicada = aplicada;
    }

    public int getId_vacuna() {
        return id_vacuna;
    }

    public void setId_vacuna(int id_vacuna) {
        this.id_vacuna = id_vacuna;
    }


    // Metodo toString que devuelve los datos del objeto Vacuna
    @Override
    public String toString() {
        return "Vacuna{" +
                "id_vacuna=" + id_vacuna +
                ", nombre='" + nombre + '\'' +
                ", dosis=" + dosis +
                ", edad=" + edad +
                ", fecha='" + fecha + '\'' +
                ", lote='" + lote + '\'' +
                ", nombre_medico='" + nombre_medico + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id_hijo=" + id_hijo +
                ", aplicada=" + aplicada +"}";
    }
}

