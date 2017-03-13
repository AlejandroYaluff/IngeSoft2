package com.example.diana.vacunacion;




public class DataBaseManager {

    public static final String TABLE_NAME = "hijos";

    public static final String CN_ID = "_id";
    public static final String CN_NOMBRE = "nombre";
    public static final String CN_FECHA_NACIMIENTO = "fechadenacimiento";
    public static final String CN_SEXO = "sexo";
    public static final String CN_CI = "ci";
    public static final String CN_APELLIDO = "apellido";
    public static final String CN_LUGAR_NACIMIENTO = "lugarnacimiento";
    public static final String CN_NACIONALIDAD = "nacionalidad";
    public static final String CN_DIRECCION = "direccion";
    public static final String CN_DEPARTAMENTO = "departamento";
    public static final String CN_MUNICIPIO = "municipio";
    public static final String CN_BARRIO= "barrio";
    public static final String CN_REFERENCIA_DOMICILIO = "referenciadedomicilio";
    public static final String CN_NOMBRE_RESPONSABLE = "nombredelresponsable";
    public static final String CN_TELEFONO = "telefono";
    public static final String CN_SEGURO_MEDICO = "seguromedico";
    public static final String CN_ALERGIA= "alergia";


    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NOMBRE + " text,"
            + CN_FECHA_NACIMIENTO + " text,"
            + CN_SEXO + " text,"
            + CN_CI + " text,"
            + CN_APELLIDO + " text,"
            + CN_LUGAR_NACIMIENTO + " text,"
            + CN_NACIONALIDAD + " text,"
            + CN_DIRECCION + " text,"
            + CN_DEPARTAMENTO + " text,"
            + CN_MUNICIPIO + " text,"
            + CN_BARRIO + " text,"
            + CN_REFERENCIA_DOMICILIO + " text,"
            + CN_NOMBRE_RESPONSABLE + " text,"
            + CN_TELEFONO + " text,"
            + CN_SEGURO_MEDICO + " text,"
            + CN_ALERGIA + " text);";


    private int id;
    private String nombre;
    private String fechadenacimiento;
    private String sexo;

    public DataBaseManager(String nombre, String fechadenacimiento, String sexo) {
        this.nombre = nombre;
        this.fechadenacimiento = fechadenacimiento;
        this.sexo = sexo;
    }

    public DataBaseManager () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechadenacimiento() {
        return fechadenacimiento;
    }

    public void setFechadenacimiento(String fechadenacimiento) {
        this.fechadenacimiento = fechadenacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}


