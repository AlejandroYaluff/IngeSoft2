package com.example.diana.vacunacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class splash_screen extends AppCompatActivity {
    private boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        cargarpreferencias();
        if (estado) {
            Intent intent = new Intent(splash_screen.this,Proyecto.class);
            startActivity(intent);
            finish();
        } else {
            ProcesoCarga proceso = new ProcesoCarga();
            proceso.execute();
        }
    }

    private void cargarpreferencias () {
        SharedPreferences mispreferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        estado = mispreferencias.getBoolean("isLoad",false);
    }

    private void guardarPreferencias (boolean valor) {
        SharedPreferences mispreferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mispreferencias.edit();
        editor.putBoolean("isLoad",valor);
        editor.commit();
    }

    private class ProcesoCarga extends AsyncTask<Void,Void,Void> {

        ProgressDialog dialog;
        ArrayList<DataBaseManager> datos = new ArrayList<DataBaseManager>(Arrays.asList(
                new DataBaseManager("Alejandro","1994-06-12","masculino"),
                new DataBaseManager("Valeria","1996-05-17","femenino"),
                new DataBaseManager("Beto","1999-11-02","masculino")
        ));
        ArrayList<Vacunas> datosVacunas = new ArrayList<>(Arrays.asList(
                new Vacunas("Tuberculosis","2017-04-12","SI APLICADA",1),
                new Vacunas("Pentabalente","2015-08-13","SI APLICADA",1),
                new Vacunas("Influenza 1ra","2017-06-05","NO APLICADA",3),
                new Vacunas("IPV","2016-04-12","NO APLICADA",2)
        ));

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(splash_screen.this);
            dialog .setTitle("ESTO ES EL TITULO");
            dialog.setMessage("INSERTANDO EN BD");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            guardarPreferencias(true);
            if(dialog.isShowing()) {
                dialog.dismiss();
                Intent intent = new Intent(splash_screen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            DbHelper helper = new DbHelper(splash_screen.this);
            for (int i = 0 ; i<datos.size() ; i++) {
                DataBaseManager dat = new DataBaseManager();
                dat = datos.get(i);
                helper.insertar(dat);

            }
            for (int i = 0 ; i<datosVacunas.size() ; i++) {
                Vacunas dat2 = new Vacunas();
                dat2 = datosVacunas.get(i);
                helper.insertarVacunas(dat2);

            }

            return null;
        }


    }
}

