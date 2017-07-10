package com.appvacunas.clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import com.appvacunas.clases.DatosHijo.HijoTable;

/*
 * Controlador de la base de datos
 */

public class ControladorDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Hijo.db";
    private ArrayList<AccionesHijo> hijolista = new ArrayList<>();
    private ArrayList<AccionesVacuna> vacunalista = new ArrayList<>();
    private static String id_usuario;
    private String direcip = "http://192.168.0.9:8080";  // direccion del servidor donde se encuentra el Web Service


    public ControladorDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String id) {
        super(context, name, factory, version);
        this.id_usuario = id;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Creacion de la tabla Hijo en la BD
        try {
            db.execSQL(
                    "CREATE TABLE " + HijoTable.TABLE_NAME + " ("
                            + HijoTable._ID + " TEXT PRIMARY KEY,"
                            + HijoTable.ID + " TEXT NOT NULL,"
                            + HijoTable.NOMBRE + " TEXT NOT NULL,"
                            + HijoTable.APELLIDO + " TEXT NOT NULL,"
                            + HijoTable.FECHA_NACIMIENTO + " TEXT NOT NULL,"
                            + HijoTable.LUGAR_NACIMIENTO + " TEXT NOT NULL,"
                            + HijoTable.SEXO + " TEXT NOT NULL,"
                            + HijoTable.NACIONALIDAD + " TEXT NOT NULL,"
                            + HijoTable.DIRECCION + " TEXT NOT NULL,"
                            + HijoTable.DEPARTAMENTO + " TEXT NOT NULL,"
                            + HijoTable.MUNICIPIO + " TEXT NOT NULL,"
                            + HijoTable.BARRIO + " TEXT NOT NULL,"
                            + HijoTable.REFERENCIA_DOMICILIO + " TEXT NOT NULL,"
                            + HijoTable.RESPONSABLE + " TEXT NOT NULL,"
                            + HijoTable.TELEFONO_CONTACTO + " TEXT NOT NULL,"
                            + HijoTable.SEGURO_MEDICO + " TEXT NOT NULL,"
                            + HijoTable.ALERGIAS + " TEXT NOT NULL,"
                            + "UNIQUE (" + HijoTable.ID + "))");

            // Creacion de la tabla Vacunas en la BD
            db.execSQL("CREATE TABLE IF NOT EXISTS Vacunas (" +
                    "id_vacuna integer not null, " +
                    "nombre text not null, " +
                    "dosis intenger not null," +
                    "edad integer," +
                    "fecha text," +
                    "lote text," +
                    "nombre_medico text," +
                    "descripcion text," +
                    "id_hijo TEXT not null," +
                    "aplicada INTEGER," +
                    "PRIMARY KEY (id_vacuna, id_hijo,dosis)," +
                    "FOREIGN KEY(id_hijo) REFERENCES hijo(id_hijo));");

            llenarlistaHijos(db);
            llenarlistaVacunas(db);

        }catch (Exception e){
            System.out.println("Ocurrio un error...");
        }
    }

    // Metodo para obtener los hijos desde el Web Service
    private void obtenerHijos() {
        String urlservicio = direcip + "/ServicioRest/webresources/usuario/gethijos";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost del = new HttpPost(urlservicio);
            del.setHeader("Accept", "application/json");
            del.setHeader("Content-type", "application/json");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_usuario", id_usuario);
            StringEntity se = new StringEntity(jsonParam.toString());
            del.setEntity(se);
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray respJSON = new JSONArray(respStr);
            for (int i = 0; i <respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                AccionesHijo objhijo;
                objhijo = new AccionesHijo();
                objhijo.setId(obj.getString("id"));
                objhijo.setNombre(obj.getString("nombre"));
                objhijo.setApellido(obj.getString("apellido"));
                objhijo.setFecha_nacimiento(obj.getString("fechaNacimiento"));
                objhijo.setLugar_nacimiento(obj.getString("lugarNacimiento"));
                objhijo.setBarrio(obj.getString("barrio"));
                objhijo.setDepartamento(obj.getString("departamento"));
                objhijo.setDireccion(obj.getString("direccion"));
                objhijo.setMunicipio(obj.getString("municipio"));
                objhijo.setReferencia_domicilio(obj.getString("referenciaDomicilio"));
                objhijo.setTelefono_contacto(obj.getString("telefonoContacto"));
                objhijo.setResponsable(obj.getString("responsable"));
                objhijo.setSeguro_medico(obj.getString("seguroMedico"));
                objhijo.setAlergias(obj.getString("alergia"));
                objhijo.setNacionalidad(obj.getString("nacionalidad"));
                objhijo.setSexo(obj.getString("sexo"));
                hijolista.add(i, objhijo);
            }
        } catch (Exception ex) {
            Log.e("ServicioRest", "Error!", ex);
        }
    }

    // Metodo para obtener las vacunas desde el Web Service
    private void obtenerVacunas() {
        String urlservicio = direcip + "/ServicioRest/webresources/usuario/getvacunas?idusuario=";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost del = new HttpPost(urlservicio);
            del.setHeader("Accept", "application/json");
            del.setHeader("Content-type", "application/json");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_usuario", id_usuario);
            StringEntity se = new StringEntity(jsonParam.toString());
            del.setEntity(se);
            HttpResponse resp = httpClient.execute(del);
            String respStr = EntityUtils.toString(resp.getEntity());
            JSONArray respJSON = new JSONArray(respStr);
            for (int i = 0; i <respJSON.length(); i++) {
                JSONObject obj = respJSON.getJSONObject(i);
                AccionesVacuna vac = new AccionesVacuna();
                vac.setId_hijo(obj.getString("hijoId"));
                vac.setEdad(obj.getInt("edad"));
                vac.setId_vacuna(obj.getInt("vacunaId"));
                vac.setDosis(obj.getInt("dosis"));
                vac.setLote(obj.getString("lote"));
                vac.setNombre(obj.getString("nombreVacuna"));
                vac.setNombre_medico(obj.getString("responsable"));
                vac.setDescripcion("");
                vac.setAplicada(obj.getInt("estado"));
                vac.setFecha(obj.getString("fecha"));
                vacunalista.add(vac);
            }
        } catch (Exception ex) {
            Log.e("Servicio", "Ocurrio un Error...", ex);
        }
    }

    // Insertar los datos del hijo en la BD
    private long insertardatosHijo(SQLiteDatabase db, AccionesHijo contenedorhijo) {
        return db.insert(HijoTable.TABLE_NAME, null, contenedorhijo.toContentValues());
    }

    // Insertar los datos de la vacuna en la BD
    private long insertardatosVacuna(SQLiteDatabase db, AccionesVacuna contenedorvacuna) {
        return db.insert(DatosVacuna.VacunaTable.TABLE_NAME, null, contenedorvacuna.toContentValues());
    }

    // Llenar la lista de hijos con los datos correspondientes
    private void llenarlistaHijos(SQLiteDatabase sqLiteDatabase) {
        obtenerHijos();
        for (int i = 0; i <hijolista.size(); i++) {
            insertardatosHijo(sqLiteDatabase, new AccionesHijo(hijolista.get(i).getId(),
                    hijolista.get(i).getNombre(), hijolista.get(i).getApellido(),hijolista.get(i).getFecha_nacimiento(),
                    hijolista.get(i).getLugar_nacimiento(), hijolista.get(i).getSexo(), hijolista.get(i).getNacionalidad(),
                    hijolista.get(i).getDireccion(), hijolista.get(i).getDepartamento(), hijolista.get(i).getMunicipio(),
                    hijolista.get(i).getBarrio(), hijolista.get(i).getReferencia_domicilio(), hijolista.get(i).getResponsable(),
                    hijolista.get(i).getTelefono_contacto(), hijolista.get(i).getSeguro_medico(), hijolista.get(i).getAlergias()));
        }
    }

    // Llenar la lista de vacunas con los datos correspondientes
    private void llenarlistaVacunas(SQLiteDatabase sqLiteDatabase) {
        obtenerVacunas();
        for (int i = 0; i < vacunalista.size(); i++) {
            insertardatosVacuna(sqLiteDatabase, new AccionesVacuna(vacunalista.get(i).getId_vacuna(),
                    vacunalista.get(i).getNombre(),vacunalista.get(i).getDosis(),vacunalista.get(i).getEdad(),
                    vacunalista.get(i).getFecha(),vacunalista.get(i).getLote(),vacunalista.get(i).getNombre_medico(),
                    vacunalista.get(i).getDescripcion(),vacunalista.get(i).getId_hijo(),vacunalista.get(i).getAplicada()));
        }
    }

    // Obtener cursor con los datos del hijo
    public Cursor cursorHijos() {
        return getReadableDatabase().query(HijoTable.TABLE_NAME, null, null, null, null, null, null);
    }

    // Obtener cursor con los datos del hijo por el ID
    public Cursor cursorHijoId(String idhijo) {
        return getReadableDatabase().query(HijoTable.TABLE_NAME, null, HijoTable.ID + " LIKE ?", new String[]{idhijo},
                null,
                null,
                null);
    }

    // Llenar la lista de vacunas
    public ArrayList cargarlistavacunas(int opcion, String id_hijo){
        ArrayList<AccionesVacuna> lista = new ArrayList<>();
        String query;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor registrodb;

        if (opcion==0){
            query = "SELECT * FROM Vacunas where id_hijo=?;";
            registrodb= database.rawQuery(query, new String[]{id_hijo});
        } else if (opcion == 1) {
            query = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
            registrodb = database.rawQuery(query, new String[]{"1", id_hijo});
        } else if (opcion == 2) {
            query = "SELECT * FROM Vacunas where aplicada=? and id_hijo=?;";
            registrodb = database.rawQuery(query, new String[]{"0", id_hijo});
        } else if (opcion == 3) {
            query = "SELECT * FROM Vacunas where id_hijo=? order by nombre;";
            registrodb = database.rawQuery(query, new String[]{id_hijo});
        } else {
            query = "SELECT * FROM Vacunas where id_hijo=? order by fecha;";
            registrodb = database.rawQuery(query, new String[]{id_hijo});
        }
        AccionesVacuna vacunas;

        if(registrodb.moveToFirst()){
            do{
                vacunas=new AccionesVacuna();
                vacunas.setNombre(registrodb.getString(1));
                vacunas.setFecha(registrodb.getString(4));
                lista.add(vacunas);
            }while(registrodb.moveToNext());
        }
        return lista;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Obtener la lista de fechas de las vacunas correspondientes
    public ArrayList obtenerfechas(){
        ArrayList<String> listafechas = new ArrayList<>();
        String query;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor registrodb;

        try {
            query = "SELECT fecha FROM Vacunas order by fecha;";
            registrodb = database.rawQuery(query, null);
            if (registrodb.moveToFirst()) {
                do {
                    listafechas.add(registrodb.getString(0));
                } while (registrodb.moveToNext());
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        return listafechas;
    }
}
