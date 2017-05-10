package com.example.diana.vacunacion;



import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Servicio {

    public void tarea (String correo) {
        ValidarUsuario tarea = new ValidarUsuario();
        tarea.execute(correo);

    }

    private class ValidarUsuario extends AsyncTask<String, Void, Void> {

        private String TAG="ValidarUsuario";
        private String respuesta;

        @Override
        protected Void doInBackground(String... params) {

            Log.i(TAG, "doInBackground");
            HttpClient httpClient = new DefaultHttpClient();
            String iCorreo = params[0];

            HttpPost post = new HttpPost("http://localhost:8080/ServicioRest/webresources/usuario");
            post.setHeader("content-type", "application/x-www-form-urlencoded");

            try {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("correo", iCorreo));
                post.setEntity(new UrlEncodedFormEntity(pairs));

                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());

                JSONObject respJSON = new JSONObject(respStr);

                respuesta = respJSON.getString("res");

            }
            catch (Exception ex)
            {
                Log.e("ServicioRest", "Error", ex);
                ex.printStackTrace();
            }

            return null;


        }

        @Override
        protected void onPostExecute (Void result) {
            Log.i(TAG, "onPostExecute");

        }

        @Override
        protected void onPreExecute () {
            Log.i(TAG, "onPreExecute");
        }
    }
}


