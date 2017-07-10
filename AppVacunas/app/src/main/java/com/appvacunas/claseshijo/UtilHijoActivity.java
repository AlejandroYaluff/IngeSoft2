package com.appvacunas.claseshijo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appvacunas.VisualizarVacunas;

import com.appvacunas.VisualizarVacunas;
import com.appvacunas.R;
import com.appvacunas.claseshijo.Notificacion;

public class UtilHijoActivity extends AppCompatActivity {

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_hijo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getStringExtra(Notificacion.EXTRA_LAWYER_ID);

        UtilFragmentActivity fragment = (UtilFragmentActivity) getSupportFragmentManager().findFragmentById(R.id.hijo_detalle_container);
        if (fragment == null) {
            fragment = UtilFragmentActivity.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.hijo_detalle_container, fragment)
                    .commit();
        }
    }

    // Metodo para visualizar los datos de las vacunas
    public void visualizarVacunas (View v){
        Intent intento = new Intent(this, VisualizarVacunas.class);
        intento.putExtra("param", id);
        startActivity(intento);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
