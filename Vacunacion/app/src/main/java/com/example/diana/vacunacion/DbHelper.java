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

    public void insertar (DataBaseManager datos) {
        db.insert(DataBaseManager.TABLE_NAME,null,generarValores(datos));
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
