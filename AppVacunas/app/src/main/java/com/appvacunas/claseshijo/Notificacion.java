package com.appvacunas.claseshijo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;

import com.appvacunas.R;
import com.appvacunas.clases.ControladorDB;

import static java.lang.Boolean.TRUE;

/* Clase para la construccion de la notificacion */

public class Notificacion extends AppCompatActivity {

    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";
    public static final int NOTIF_ALERTA_ID = 55;
    protected String id_usuario;

    public static boolean notificacionCar = false;
    public static boolean notificacionNoti = false;

    ArrayList<String> listafechas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id_usuario = getIntent().getExtras().getString("id_usuario");
        Bundle bundle = new Bundle();
        bundle.putString("id_usuario", id_usuario);
        HijoFragment fragment = (HijoFragment) getSupportFragmentManager().findFragmentById(R.id.hijo_container);

        if (fragment == null) {
            fragment = HijoFragment.newInstance();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.hijo_container, fragment).commit();
        }

        if (!notificacionCar) {
            ControladorDB fechas = new ControladorDB(getApplicationContext(), "Hijo.db", null, 1,"");
            listafechas = fechas.obtenerfechas();
            Date date = new Date();
            CharSequence fechaActual = DateFormat.format("yyyy-MM-dd", date.getTime());

            for (int i = 0; i<listafechas.size();i++) {

                if (listafechas.get(i).equals(fechaActual)) {
                    notificacionNoti = true;
                    break;
                }
            }

            // Lanzar la notificacion si corresponde
            if (notificacionNoti) {

                Uri tono = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icono)
                        .setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.drawable.icono)).getBitmap()))
                        .setContentTitle("Aviso Importante")
                        .setContentText("Fecha aplicacion vacuna en 2 dias")
                        .setSound(tono)
                        .setAutoCancel(TRUE);

                Intent intento = new Intent(this, HijoFragment.class);
                PendingIntent contIntent = PendingIntent.getActivity(this, 0, intento, 0);
                mBuilder.setContentIntent(contIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
            }

            notificacionCar = true;
        }

    }


}
