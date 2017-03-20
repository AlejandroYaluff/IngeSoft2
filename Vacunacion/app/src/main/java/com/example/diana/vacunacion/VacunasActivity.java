package com.example.diana.vacunacion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VacunasActivity extends AppCompatActivity {

    ListView lista;
    DbHelper db;
    List<String> item = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

        lista = (ListView)findViewById(R.id.lista2);

        showNotes();
    }


    private void showNotes () {
        db = new DbHelper(this);
        Cursor c = db.getDatosVacunas();
        item = new ArrayList<String>();
        String vacuna = "", fecha = "", aplicada = "";
        if (c.moveToFirst()) {
            do {
                vacuna = c.getString(1);
                fecha = c.getString(2);
                aplicada = c.getString(3);
                item.add(vacuna +" - "+ fecha+" - "+ aplicada);
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
        lista.setAdapter(adaptador);


    }


}
