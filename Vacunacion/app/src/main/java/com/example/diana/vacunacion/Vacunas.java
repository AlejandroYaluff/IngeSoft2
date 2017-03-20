package com.example.diana.vacunacion;


public class Vacunas {

    public static final String TABLE_NAME = "vacunas";

    public static final String CN_ID = "_id";
    public static final String CN_VACUNA = "vacuna";
    public static final String CN_EDAD = "edad";
    public static final String CN_DOSIS = "dosis";
    public static final String CN_FECHA = "fecha";
    public static final String CN_LOTE = "lote";
    public static final String CN_RESPONSABLE = "responsable";
    public static final String CN_DOSIS_UNICA = "dosisunica";
    public static final String CN_1RA_DOSIS = "dosis1";
    public static final String CN_2DA_DOSIS = "dosis2";
    public static final String CN_3RA_DOSIS = "dosis3";
    public static final String CN_AL_ANHO= "alanho";
    public static final String CN_15_MESES = "meses15";
    public static final String CN_1ER_REFUERZO = "refuerzo1";
    public static final String CN_2DO_REFUERZO = "refuerzo2";
    public static final String CN_APLICADA = "aplicada";
    public static final String CN_EDAD_IDEAL = "edadideal";
    public static final String CN_ID_HIJO = "idhijo";



    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_VACUNA + " text,"
            + CN_EDAD + " text,"
            + CN_DOSIS + " text,"
            + CN_FECHA + " text,"
            + CN_LOTE + " text,"
            + CN_RESPONSABLE + " text,"
            + CN_DOSIS_UNICA + " numeric,"
            + CN_1RA_DOSIS + " numeric,"
            + CN_2DA_DOSIS + " numeric,"
            + CN_3RA_DOSIS + " numeric,"
            + CN_AL_ANHO + " numeric,"
            + CN_15_MESES + " numeric,"
            + CN_1ER_REFUERZO + " numeric,"
            + CN_2DO_REFUERZO + " numeric,"
            + CN_APLICADA + " text,"
            + CN_EDAD_IDEAL + " text,"
            + CN_ID_HIJO + " integer,"
            + " FOREIGN KEY ("+CN_ID_HIJO+") REFERENCES "+DataBaseManager.TABLE_NAME+"("+DataBaseManager.CN_ID+"));";


    private int id;
    private String vacuna;
    private String fecha;
    private String aplicada;
    private int idhijo;


    public Vacunas(String vacuna, String fecha, String aplicada, int idhijo) {
        this.vacuna = vacuna;
        this.fecha = fecha;
        this.aplicada = aplicada;
        this.idhijo = idhijo;
    }

    public Vacunas () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAplicada() {
        return aplicada;
    }

    public void setAplicada(String aplicada) {
        this.aplicada = aplicada;
    }

    public int getIdHijo () {
        return idhijo;
    }

    public void setIdHijo (int idhijo) {
        this.idhijo = idhijo;
    }
}
