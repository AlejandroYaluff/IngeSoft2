package com.example.diana.vacunacion;




public class DataBaseManager {

    public static final String TABLE_NAME = "hijos";

    public static final String CN_ID = "_id";
    public static final String CN_NOMBRE = "nombre";
    public static final String CN_FECHA_NACIMIENTO = "fechadenacimiento";
    public static final String CN_SEXO = "sexo";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NOMBRE + " text,"
            + CN_FECHA_NACIMIENTO + " text,"
            + CN_SEXO + " text);";

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


