package com.example.diana.vacunacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "agenda.sqlite";
    private static final int DB_SCHEME_VERSION = 1;
    private SQLiteDatabase db;


    public DbHelper(Context context) {

        super(context,DB_NAME , null, DB_SCHEME_VERSION);
        db = this.getWritableDatabase();
    }

    private ContentValues generarValores (DataBaseManager datos) {
        ContentValues valores = new ContentValues();
        valores.put(DataBaseManager.CN_NOMBRE,datos.getNombre());
        valores.put(DataBaseManager.CN_FECHA_NACIMIENTO,datos.getFechadenacimiento());
        valores.put(DataBaseManager.CN_SEXO,datos.getSexo());
        return valores;
    }

    private ContentValues generarValoresVacunas (Vacunas datos) {
        ContentValues valores = new ContentValues();
        valores.put(Vacunas.CN_VACUNA,datos.getVacuna());
        valores.put(Vacunas.CN_FECHA,datos.getFecha());
        valores.put(Vacunas.CN_APLICADA,datos.getAplicada());
        valores.put(Vacunas.CN_ID_HIJO,datos.getIdHijo());
        return valores;
    }

    public void insertar (DataBaseManager datos) {
        db.insert(DataBaseManager.TABLE_NAME,null,generarValores(datos));
    }

    public void insertarVacunas (Vacunas datos) {
        db.insert(Vacunas.TABLE_NAME,null,generarValoresVacunas(datos));
    }

    public Cursor getDatos() {

        String columnas [] = {DataBaseManager.CN_ID, DataBaseManager.CN_NOMBRE, DataBaseManager.CN_FECHA_NACIMIENTO, DataBaseManager.CN_SEXO};
        Cursor c = db.query(DataBaseManager.TABLE_NAME, columnas, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                DataBaseManager p = new DataBaseManager();
                p.setId(c.getInt(0));
                p.setNombre(c.getString(1));
                p.setFechadenacimiento(c.getString(2));
                p.setSexo(c.getString(3));


            } while (c.moveToNext());
        }
        return c;
    }

    public Cursor getDatosVacunas() {

        String columnas [] = {Vacunas.CN_ID,Vacunas.CN_VACUNA, Vacunas.CN_FECHA, Vacunas.CN_APLICADA, Vacunas.CN_ID_HIJO};
        Cursor c = db.query(Vacunas.TABLE_NAME, columnas, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Vacunas p = new Vacunas();
                p.setId(c.getInt(0));
                p.setVacuna(c.getString(1));
                p.setFecha(c.getString(2));
                p.setAplicada(c.getString(3));
                p.setIdHijo(c.getInt(4));


            } while (c.moveToNext());
        }
        return c;
    }

    public Cursor getDatosNotificacion() {

        String columnas [] = {Vacunas.CN_ID, Vacunas.CN_FECHA};
        Cursor c = db.rawQuery("Select _id,fecha,vacuna,CAST(ROUND((julianday(fecha) - julianday('now'))) AS INTEGER) from vacunas",null);
        if (c.moveToFirst()) {
            do {
                Vacunas p = new Vacunas();
                p.setId(c.getInt(0));
                p.setFecha(c.getString(1));
                p.setVacuna(c.getString(2));
            } while (c.moveToNext());
        }
        return c;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE);
        db.execSQL(Vacunas.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}