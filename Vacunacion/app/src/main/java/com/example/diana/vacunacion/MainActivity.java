package com.example.diana.vacunacion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    DbHelper db;
    List<String> item = null;
    private EditText caja;
    private Button vervacunas;
    int idpadre;
    String servidor="http://192.168.0.2:8080";
    String linkService = servidor+"/ServicioRest/webresources/hijo/obtenerhijos/";
    String linkService2 = servidor+"/ServicioRest/webresources/vacuna/obtenerfecha";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        caja = (EditText) findViewById(R.id.editText);
        vervacunas = (Button) findViewById(R.id.button2);

        String datoidpadre = null;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            datoidpadre = (String)extras.get("idpadre");
        }

        vervacunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DesplegarActivity.class);
                String dato = caja.getText().toString();
                intent.putExtra("idhijo",dato);
                startActivity(intent);


            }
        });

        lista = (ListView)findViewById(R.id.lista);

        //ObtenerHijos tarea = new ObtenerHijos();
        //tarea.execute(datoidpadre);
        MostrarHijos tarea = new MostrarHijos();
        tarea.execute(datoidpadre);

        llamarNotificacion();

    }

    private void mostrarHijos(String id) {

        idpadre = Integer.parseInt(id);
        item = new ArrayList<String>();
        String nombre = "",sexo = "";
        int idhijo,edad;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService + idpadre);
        del.setHeader("content-type", "application/json");
        try {
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray datosJSON = new JSONArray(respStr);
            for (int i = 0; i < datosJSON.length(); i++) {
                JSONObject obj = datosJSON.getJSONObject(i);
                idhijo = obj.getInt("id");
                nombre = obj.getString("nombre");
                sexo = obj.getString("sexo");
                edad = obj.getInt("edad");
                item.add(idhijo +" | "+ nombre +" | "+ sexo +" | "+ edad);
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
            lista.setAdapter(adaptador);
        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un error!!", ex);
        }

    }

    /*private void showNotes () {
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

                item.add(id +" | "+ nombre +" | "+ fecha+" | "+ sexo);
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);
        lista.setAdapter(adaptador);


    }*/

    private void notificacionFecha () {
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(getBaseContext());
        notificacion.setSmallIcon(android.R.drawable.ic_dialog_info);

        notificacion.setTicker("Informacion Vacunas");
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle("vacunas");
        notificacion.setContentText("notificacion");
        notificacion.setContentInfo("Se acerca fecha de vacunacion");

        Uri sonido = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
        notificacion.setSound(sonido);

        PendingIntent pendingIntent;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Context context = getApplicationContext();

        intent.setClass(context, Notification.class);
        intent.putExtra("ID", 1);

        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notificacion.setContentIntent(pendingIntent);

        Notification n = notificacion.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1,n);
    }

    /*private void llamarNotificacion () {

        db = new DbHelper(this);
        Cursor c = db.getDatosNotificacion();
        String fecha = "",vacuna = "";
        int id,diferencia;
        if (c.moveToFirst()) {
            do {
                id = c.getInt(0);
                fecha = c.getString(1);
                vacuna = c.getString(2);
                diferencia = c.getInt(3);
                if (diferencia == 2) {
                    notificacionFecha(fecha,vacuna);
                }
            } while (c.moveToNext());
        }

    }*/

    /*private void insertarfilas () {


        String nombre = "",fecha_aplicacion = "",aplicada = "";
        int idhijo=0;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService2);
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
                idhijo = obj.getInt("idhijo");
                ArrayList<Vacunas> datosVacunas = new ArrayList<>(Arrays.asList(
                        new Vacunas(nombre,fecha_aplicacion,aplicada,idhijo)
                ));
            }
            for (int i = 0 ; i<datosVacunas.size() ; i++) {
                Vacunas dat2 = new Vacunas();
                dat2 = datosVacunas.get(i);
                helper.insertarVacunas(dat2);

            }

        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un error!!", ex);
        }
    }*/

    private void llamarNotificacion () {
        String dias;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(linkService2);
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
            dias = recibido.toString();

            List<String> listadias = new ArrayList<String>(Arrays.asList(dias.split(",")));
            for(int x=0;x<listadias.size();x++) {
                float p = Float.parseFloat(listadias.get(x));
                System.out.println(p);
                if (p == 2) {
                    System.out.println(p);
                    notificacionFecha();
                }

            }


        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un error!!", ex);
        }
    }



    private class MostrarHijos extends AsyncTask<String, Void, Void> {

        List<String> item2 = new ArrayList<String>();
        //ListView lista;
        public static final String ip="192.168.0.2";
        public static final String url ="http://"+ip+":8080/ServicioRest/webresources/hijo/obtenerhijos";
        String nombre = "",sexo = "";
        int edad;
        int idpadre,idhijo;

        @Override
        protected Void doInBackground(String... params) {

            idpadre = Integer.parseInt(params[0]);
            Log.i("ConsultaHijos","doInBackground");

            //HttpGet get = new HttpGet(url);
            //get.setHeader("content-type", "application/json");
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("id", idpadre);
                HttpClient httpClient = new DefaultHttpClient();
                //HttpClient httpClient = new DefaultHttpClient();
                HttpPost del = new HttpPost(url);
                del.setHeader("Accept", "application/json");
                del.setHeader("Content-type", "application/json");
                StringEntity se = new StringEntity(jsonParam.toString());
                del.setEntity(se);
                //
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONArray respJSON = new JSONArray(respStr);
                for (int i = 0; i <respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    idhijo = obj.getInt("id");
                    nombre = obj.getString("nombre");
                    sexo = obj.getString("sexo");
                    edad = obj.getInt("edad");
                    item2.add(idhijo + " | " + nombre + " | " + sexo + " | " + edad);
                }
                //ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,item2);
                //lista.setAdapter(adaptador);

            }
            catch (Exception ex) {
                Log.e("ServicioRest", "Error", ex);
                ex.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("ServicioRest","onPostExecute");
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,item2);
            lista.setAdapter(adaptador);
        }

        @Override
        protected void onPreExecute() {
            Log.i("ServicioRest","onPreExecute");
        }

    }



}

