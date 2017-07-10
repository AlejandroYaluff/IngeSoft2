package com.appvacunas.claseshijo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appvacunas.R;
import com.appvacunas.clases.ControladorDB;
import com.appvacunas.claseshijo.UtilHijoActivity;

import static com.appvacunas.clases.DatosHijo.HijoTable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HijoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HijoFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;
    private ControladorDB insControlDB;
    private CursorHijo insAdapHijo;

    public HijoFragment() {

    }

    public static HijoFragment newInstance() {
        return new HijoFragment();
    }

    // Creacion de la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.activity_hijo_fragment, container, false);
        String id_usuario = getArguments().getString("id_usuario");
        ListView listahijos = (ListView) vista.findViewById(R.id.hijo_list);
        insAdapHijo = new CursorHijo(getActivity(), null);
        listahijos.setAdapter(insAdapHijo);
        listahijos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) insAdapHijo.getItem(i);
                String currentId = currentItem.getString(currentItem.getColumnIndex(HijoTable.ID));
                showDetails(currentId);
            }
        });

        getActivity().deleteDatabase(ControladorDB.DATABASE_NAME);
        insControlDB = new ControladorDB(getActivity(), "Hijo.db", null, 1, id_usuario);
        cargaHijos();
        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_LAWYER:
                    cargaHijos();
                    break;
            }
        }
    }

    private void cargaHijos() {
        new cargaHijosTask().execute();
    }

    private void showDetails(String lawId) {
        Intent intent = new Intent(getActivity(), UtilHijoActivity.class);
        intent.putExtra(Notificacion.EXTRA_LAWYER_ID, lawId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    // Tarea en segundo plano para cargar los hijos
    private class cargaHijosTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return insControlDB.cursorHijos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                insAdapHijo.swapCursor(cursor);
            }
        }
    }
}

