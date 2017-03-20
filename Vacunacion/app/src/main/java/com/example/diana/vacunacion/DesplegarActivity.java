package com.example.diana.vacunacion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DesplegarActivity extends AppCompatActivity {

    ListView lista;
    DbHelper db;
    List<String> item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplegar);
        String datoid = null;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            datoid = (String)extras.get("id");
        }
        

        lista = (ListView)findViewById(R.id.lista2);
        desplegarVacunas(datoid);
    }

    private void desplegarVacunas (String id) {

        db = new DbHelper(this);
        Cursor c = db.getDatosVacunas();
        item = new ArrayList<String>();

        
        String vacuna = "", fecha = "", aplicada = "";
        if (c.moveToFirst()) {
            do {
                String datoidhijo = Integer.toString(c.getInt(4));
                if (id.equals(datoidhijo)) {
                    vacuna = c.getString(1);
                    fecha = c.getString(2);
                    aplicada = c.getString(3);
                    item.add(vacuna +" - "+ fecha+" - "+ aplicada);
                }

            } while (c.moveToNext());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
        lista.setAdapter(adaptador);
    }
}
