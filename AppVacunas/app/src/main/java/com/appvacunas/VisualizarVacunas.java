package com.appvacunas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;
import java.util.ArrayList;

import com.appvacunas.clases.ControladorDB;
import com.appvacunas.clases.AccionesVacuna;
import com.appvacunas.utiles.Filtrar;
import com.appvacunas.utiles.TablaVacunas;

/*
 *  Clase para visualizar la lista de vacunas correspondientes
 */

public class VisualizarVacunas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<AccionesVacuna> listavacunas;
    protected Spinner filtroSpin;
    ArrayList<Filtrar> listafiltrar = new ArrayList<>();
    public int orden;
    protected TableLayout table;
    TablaVacunas tabla;
    String id_hijo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_vacunas);
        id_hijo = getIntent().getExtras().getString("param");

        Filtrar aux=new Filtrar();
        aux.setNombre("Seleccionar Filtro");
        aux.setPosicion(0);
        listafiltrar.add(aux);

        // Filtrar vacunas aplicadas
        aux=new Filtrar();
        aux.setNombre("Aplicadas");
        aux.setPosicion(1);
        listafiltrar.add(aux);

        // Filtrar vacunas no aplicadas
        aux=new Filtrar();
        aux.setNombre("No Aplicadas");
        aux.setPosicion(2);
        listafiltrar.add(aux);

        // Filtrar vacunas por orden alfabetico
        aux=new Filtrar();
        aux.setNombre("Orden Alfabetico");
        aux.setPosicion(3);
        listafiltrar.add(aux);

        // Filtrar vacunas por fecha de aplicacion
        aux=new Filtrar();
        aux.setNombre("Por fecha");
        aux.setPosicion(4);
        listafiltrar.add(aux);

        // Spinner con las opciones de filtro
        filtroSpin = (Spinner) findViewById(R.id.spinner);
        filtroSpin.setOnItemSelectedListener(this);
        ArrayAdapter<Filtrar> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listafiltrar);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtroSpin.setAdapter(dataAdapter);
        table = (TableLayout) findViewById(R.id.tabla);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        if(position == 0){
            Toast.makeText(parent.getContext(), "Seleccionar un Filtro ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(parent.getContext(), "Se ha seleccionado: " + item, Toast.LENGTH_SHORT).show();
        }
        table.removeAllViews();
        tabla = new TablaVacunas(this, (TableLayout)findViewById(R.id.tabla));

        if(position!=0) {
            ControladorDB controlDB = new ControladorDB(getApplicationContext(), "Hijo.db", null, 1,"");

            listavacunas = controlDB.cargarlistavacunas(position, id_hijo);
            int tamano = listavacunas.size();
            tabla.agregarCabecera(R.array.cabecera_tabla);
            for (int i = 0; i < tamano; i++) {
                ArrayList<String> elementosVacunas = new ArrayList<>();
                elementosVacunas.add(listavacunas.get(i).getNombre());
                elementosVacunas.add(listavacunas.get(i).getFecha());
                tabla.agregarFilaTabla(elementosVacunas);
            }
        }
        orden = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
