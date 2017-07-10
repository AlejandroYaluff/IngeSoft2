package com.appvacunas.clases;

import android.content.ContentValues;
import android.database.Cursor;

import com.appvacunas.clases.DatosHijo.HijoTable;

/**
 * Clase que maneja las operaciones con los datos del hijo
 */


// Declaracion de las variables usadas para los datos del hijo
public class AccionesHijo {
    private String id;
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String lugar_nacimiento;
    private String sexo;
    private String nacionalidad;
    private String direccion;
    private String departamento;
    private String municipio;
    private String barrio;
    private String referencia_domicilio;
    private String responsable;
    private String telefono_contacto;
    private String seguro_medico;
    private String alergias;


    public AccionesHijo() {
        this.id = "";
        this.nombre = "";
        this.apellido = "";
        this.fecha_nacimiento = "";
        this.lugar_nacimiento = "";
        this.sexo = "";
        this.nacionalidad = "";
        this.direccion = "";
        this.departamento = "";
        this.municipio = "";
        this.barrio = "";
        this.referencia_domicilio = "";
        this.responsable = "";
        this.telefono_contacto = "";
        this.seguro_medico = "";
        this.alergias = "";
    }


    public AccionesHijo(String id, String nombre, String apellido, String fecha_nacimiento, String lugar_nacimiento,
                String sexo, String nacionalidad, String direccion, String departamento, String municipio,
                String barrio, String referencia_domicilio, String responsable, String telefono_contacto,
                String seguro_medico, String alergias) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lugar_nacimiento = lugar_nacimiento;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.barrio = barrio;
        this.referencia_domicilio = referencia_domicilio;
        this.responsable = responsable;
        this.telefono_contacto = telefono_contacto;
        this.seguro_medico = seguro_medico;
        this.alergias = alergias;
    }

    // Carga de los valores correspondientes en las variables
    public AccionesHijo(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(HijoTable.ID));
        nombre = cursor.getString(cursor.getColumnIndex(HijoTable.NOMBRE));
        apellido = cursor.getString(cursor.getColumnIndex(HijoTable.APELLIDO));
        fecha_nacimiento = cursor.getString(cursor.getColumnIndex(HijoTable.FECHA_NACIMIENTO));
        lugar_nacimiento = cursor.getString(cursor.getColumnIndex(HijoTable.LUGAR_NACIMIENTO));
        sexo = cursor.getString(cursor.getColumnIndex(HijoTable.SEXO));
        nacionalidad = cursor.getString(cursor.getColumnIndex(HijoTable.NACIONALIDAD));
        direccion = cursor.getString(cursor.getColumnIndex(HijoTable.DIRECCION));
        departamento = cursor.getString(cursor.getColumnIndex(HijoTable.DEPARTAMENTO));
        municipio = cursor.getString(cursor.getColumnIndex(HijoTable.MUNICIPIO));
        barrio = cursor.getString(cursor.getColumnIndex(HijoTable.BARRIO));
        referencia_domicilio = cursor.getString(cursor.getColumnIndex(HijoTable.REFERENCIA_DOMICILIO));
        responsable = cursor.getString(cursor.getColumnIndex(HijoTable.RESPONSABLE));
        telefono_contacto = cursor.getString(cursor.getColumnIndex(HijoTable.TELEFONO_CONTACTO));
        seguro_medico = cursor.getString(cursor.getColumnIndex(HijoTable.SEGURO_MEDICO));
        alergias = cursor.getString(cursor.getColumnIndex(HijoTable.ALERGIAS));
    }

    // Carga del contenedor con los datos correspondientes
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HijoTable.ID, id);
        values.put(HijoTable.NOMBRE, nombre);
        values.put(HijoTable.APELLIDO, apellido);
        values.put(HijoTable.FECHA_NACIMIENTO, fecha_nacimiento);
        values.put(HijoTable.LUGAR_NACIMIENTO, lugar_nacimiento);
        values.put(HijoTable.SEXO, sexo);
        values.put(HijoTable.NACIONALIDAD, nacionalidad);
        values.put(HijoTable.DIRECCION, direccion);
        values.put(HijoTable.DEPARTAMENTO, departamento);
        values.put(HijoTable.MUNICIPIO, municipio);
        values.put(HijoTable.BARRIO, barrio);
        values.put(HijoTable.REFERENCIA_DOMICILIO, referencia_domicilio);
        values.put(HijoTable.RESPONSABLE, responsable);
        values.put(HijoTable.TELEFONO_CONTACTO, telefono_contacto);
        values.put(HijoTable.SEGURO_MEDICO, seguro_medico);
        values.put(HijoTable.ALERGIAS, alergias);
        return values;
    }

    // Getters y Setters de los datos del hijo
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setReferencia_domicilio(String referencia_domicilio) {
        this.referencia_domicilio = referencia_domicilio;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public void setSeguro_medico(String seguro_medico) {
        this.seguro_medico = seguro_medico;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getReferencia_domicilio() {
        return referencia_domicilio;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public String getSeguro_medico() {
        return seguro_medico;
    }

    public String getAlergias() {
        return alergias;
    }
}

