package com.example.diana.vacunacion;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    DbHelper db;
    List<String> item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView)findViewById(R.id.lista);
        showNotes();

    }

    private void showNotes () {
        db = new DbHelper(this);
        Cursor c = db.getDatos();
        item = new ArrayList<String>();
        String nombre = "", fecha = "", sexo = "";
        if (c.moveToFirst()) {
            do {
                nombre = c.getString(1);
                fecha = c.getString(2);
                sexo = c.getString(3);
                item.add(nombre +" - "+ fecha+" - "+ sexo);
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
        lista.setAdapter(adaptador);


    }
}
