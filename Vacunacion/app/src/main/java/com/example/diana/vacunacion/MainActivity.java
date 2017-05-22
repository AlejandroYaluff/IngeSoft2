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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

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


            }
        });

        lista = (ListView)findViewById(R.id.lista);

        ObtenerHijos tarea = new ObtenerHijos();
        tarea.execute();
        //showNotes();
        llamarNotificacion();

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

    private void notificacionFecha (String fecha, String vacuna) {
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(getBaseContext());
        notificacion.setSmallIcon(android.R.drawable.ic_dialog_info);

        notificacion.setTicker("Informacion Vacunas");
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle(vacuna);
        notificacion.setContentText(fecha);
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

    private void llamarNotificacion () {
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

    }

    private class ObtenerHijos extends AsyncTask<String, Void, Void> {

        List<String> item2 = new ArrayList<String>();
        ListView lista;
        public static final String ip="192.168.0.2";
        public static final String url ="http://"+ip+":8080/ServicioRest/webresources/hijo/obtenerhijos/";
        String nombre = "",sexo = "";
        int edad;
        int idpadre = 14;

        @Override
        protected Void doInBackground(String... params) {

            Log.i("ConsultaHijos","doInBackground");
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url + idpadre);
            get.setHeader("content-type", "application/json");

            try {
                HttpResponse resp = httpClient.execute(get);
                String respString = EntityUtils.toString(resp.getEntity());

                JSONObject respJSON = new JSONObject(respString);
                nombre = respJSON.getString("nombre");
                sexo = respJSON.getString("sexo");
                edad = respJSON.getInt("edad");
                item2.add(nombre +" | "+ sexo +" | "+ edad);
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
