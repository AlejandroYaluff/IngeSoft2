package com.example.diana.vacunacion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    DbHelper db;
    List<String> item = null;
    private EditText caja;
    private Button vervacunas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caja = (EditText) findViewById(R.id.editText);
        vervacunas = (Button) findViewById(R.id.button2);

        vervacunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DesplegarActivity.class);
                String dato = caja.getText().toString();
                intent.putExtra("id",dato);
                startActivity(intent);
                //startActivityForResult(intent,0);

            }
        });

        lista = (ListView)findViewById(R.id.lista);

        showNotes();

    }

    private void showNotes () {
        db = new DbHelper(this);
        Cursor c = db.getDatos();
        item = new ArrayList<String>();
        String nombre = "", fecha = "", sexo = "";
        int id;
        if (c.moveToFirst()) {
            do {
                id = c.getInt(0);
                nombre = c.getString(1);
                fecha = c.getString(2);
                sexo = c.getString(3);
                item.add(id +" - "+ nombre +" - "+ fecha+" - "+ sexo);
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
        lista.setAdapter(adaptador);


    }
}
