package com.example.diana.vacunacion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DesplegarActivity extends AppCompatActivity {

    ListView lista;
    Spinner spinnereleccion;
    Spinner spinnerfiltrar;
    DbHelper db;
    List<String> item = null;
    List<String> item2 = null;
    int idhijo;
    String datoid = null;
    String servidor="http://192.168.0.2:8080";
    //String linkService = servidor+"/ServicioRest/webresources/vacuna/obtenervacunas/";
    String linkService = servidor+"/ServicioRest/webresources/vacuna/ordenar/";
    String linkService2 = servidor+"/ServicioRest/webresources/vacuna/filtrar/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplegar);
        //String datoid = null;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            datoid = (String)extras.get("idhijo");
        }

        spinnereleccion = (Spinner) findViewById(R.id.spinner);
        spinnerfiltrar = (Spinner) findViewById(R.id.spinner2);


        spinnereleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        desplegarVacunas(datoid,1);
                        break;
                    case 1:
                        desplegarVacunas(datoid,2);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerfiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        desplegarvacunasfiltrar(datoid,1);
                        break;
                    case 1:
                        desplegarvacunasfiltrar(datoid,2);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lista = (ListView)findViewById(R.id.lista2);
        //desplegarVacunas(datoid);
    }

    /*private void desplegarVacunas (String id) {

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
    }*/


    /*private void desplegarVacunas(String id) {

        idhijo = Integer.parseInt(id);
        item = new ArrayList<String>();
        String nombre = "",fecha_aplicacion = "",aplicada = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService + idhijo);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray datosJSON = new JSONArray(respStr);
            for (int i = 0; i < datosJSON.length(); i++) {
                JSONObject obj = datosJSON.getJSONObject(i);
                nombre = obj.getString("nombre");
                fecha_aplicacion = obj.getString("fecha_aplicacion");
                aplicada = obj.getString("aplicada");
                item.add(nombre +" | "+ fecha_aplicacion +" | "+ aplicada);
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
            lista.setAdapter(adaptador);
        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un error!!", ex);
        }

    }*/

    private void desplegarVacunas(String id, Integer eleccion) {

        idhijo = Integer.parseInt(id);
        item = new ArrayList<String>();
        String nombre = "",fecha_aplicacion = "",aplicada = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService + idhijo + "/" + eleccion);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray datosJSON = new JSONArray(respStr);
            for (int i = 0; i < datosJSON.length(); i++) {
                JSONObject obj = datosJSON.getJSONObject(i);
                nombre = obj.getString("nombre");
                fecha_aplicacion = obj.getString("fecha_aplicacion");
                aplicada = obj.getString("aplicada");
                item.add(nombre +" | "+ fecha_aplicacion +" | "+ aplicada);
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
            lista.setAdapter(adaptador);
        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un error!!", ex);
        }

    }

    private void desplegarvacunasfiltrar (String id, Integer eleccion) {
        String filtro;
        idhijo = Integer.parseInt(id);
        item2 = new ArrayList<String>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService2 + idhijo + "/" + eleccion);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            //String respStr = EntityUtils.toString(resp.getEntity());
            InputStream instream = resp.getEntity().getContent();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream), 8000);
            StringBuilder recibido = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                recibido.append(line);
            }
            instream.close();
            filtro = recibido.toString();

            List<String> listadias = new ArrayList<String>(Arrays.asList(filtro.split(",")));
            for(int x=0;x<listadias.size();x++) {
                item2.add(listadias.get(x));

            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item2);
            lista.setAdapter(adaptador);


        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un error!!", ex);
        }
    }



}
