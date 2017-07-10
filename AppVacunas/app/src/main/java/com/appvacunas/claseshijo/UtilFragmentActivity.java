package com.appvacunas.claseshijo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appvacunas.R;
import com.appvacunas.clases.AccionesHijo;
import com.appvacunas.clases.ControladorDB;
import com.appvacunas.claseshijo.HijoFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UtilFragmentActivity#newInstance} factory method to
 * create an instance of this fragment.
 */

public class UtilFragmentActivity extends Fragment {

    private static final String ARG_LAWYER_ID = "lawyerId";
    private ControladorDB insControlDB;
    private String  mId;

    private CollapsingToolbarLayout mCollapsingView;
    private TextView Id;
    private TextView FechaNacimiento;
    private TextView Nacionalidad;
    private TextView LugarNacimiento;
    private TextView Sexo;
    private TextView Domicilio;
    private TextView Telefono;
    private TextView Alergias;

    public UtilFragmentActivity() {

    }

    public static UtilFragmentActivity newInstance(String lawId) {
        UtilFragmentActivity fragment = new UtilFragmentActivity();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, lawId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ARG_LAWYER_ID);
        }
        setHasOptionsMenu(true);
    }

    // Creacion de la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.activity_util_fragment, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        Id = (TextView) vista.findViewById(R.id.tv_id);
        FechaNacimiento = (TextView) vista.findViewById(R.id.tv_fecha_nac);
        Nacionalidad = (TextView) vista.findViewById(R.id.tv_nacionalidad);
        LugarNacimiento = (TextView) vista.findViewById(R.id.tv_lugar_nac);
        Sexo = (TextView) vista.findViewById(R.id.tv_sex);
        Domicilio= (TextView) vista.findViewById(R.id.tv_domic);
        Telefono = (TextView) vista.findViewById(R.id.tv_telefono);
        Alergias = (TextView) vista.findViewById(R.id.tv_alergias);

        insControlDB = new ControladorDB(getActivity(), "Hijo.db", null, 1,"");
        cargaHijo();
        return vista;
    }

    private void cargaHijo() {
        new obtenerhijobyidTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == HijoFragment.REQUEST_UPDATE_DELETE_LAWYER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void mostrarError() {
        Toast.makeText(getActivity(), "Error al cargar...", Toast.LENGTH_SHORT).show();
    }

    // Metodo para visualizar los datos del hijo
    private void mostrarHijo(AccionesHijo hijo) {

        mCollapsingView.setTitle("Datos Hijo");
        Id.setText(hijo.getId());
        FechaNacimiento.setText(hijo.getFecha_nacimiento());
        Nacionalidad.setText(hijo.getNacionalidad());
        LugarNacimiento.setText(hijo.getLugar_nacimiento());
        Sexo.setText(hijo.getSexo());
        Domicilio.setText(hijo.getDireccion());
        Telefono.setText(hijo.getTelefono_contacto());
        Alergias.setText(hijo.getAlergias());
    }

    // Tarea en segundo plano para obtener el hijo por el ID
    private class obtenerhijobyidTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return insControlDB.cursorHijoId(mId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                mostrarHijo(new AccionesHijo(cursor));
            } else {
                mostrarError();
            }
        }
    }
}
