package com.example.diana.vacunacion;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Proyecto extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton SignIn;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    String email;
    public static final String servidor="http://192.168.0.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto);
        SignIn = (SignInButton) findViewById(R.id.bn_login);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bn_login:
                        signIn();
                        break;

                }
            }
        });

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).addApi(AppIndex.API).build();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
        //validar obtener = new validar();
        //obtener.execute();

    }


    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            email = String.valueOf(account.getEmail());

            validar obtener = new validar();
            obtener.execute();
            //goMainScreen();

        } else {
            Toast.makeText(this,"No se puede iniciar sesion", Toast.LENGTH_SHORT).show();
        }
    }

    /*private void goMainScreen() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == REQ_CODE) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleResult(result);
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Proyecto Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient.connect();
        AppIndex.AppIndexApi.start(googleApiClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(googleApiClient, getIndexApiAction());
        googleApiClient.disconnect();
    }

    /*private class validar extends AsyncTask<String,Integer,Boolean> {

        public static final String ip="192.168.0.2";
        public static final String url ="http://"+ip+":8080/ServicioRest/webresources/usuario/validarusuario?correo=";
        private String id;
        //private boolean resu=true;
        protected Boolean doInBackground(String... params) {

            boolean resul = false;

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet del = new HttpGet(url + email);
            del.setHeader("content-type", "text/plain");

            try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                System.out.println(respStr);

                HttpResponse response = httpClient.execute(del);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream instream = response.getEntity().getContent();
                    BufferedReader r = new BufferedReader(new InputStreamReader(
                            instream), 8000);
                    StringBuilder recibido = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        recibido.append(line);
                    }
                    instream.close();
                    id = recibido.toString();

                    if (!id.equals("novalido")) {
                        resul= true;
                    }

                }
            } catch (Exception ex) {
                Log.e("Servicio", "Ocurrio un error!!", ex);

            }
            //
            return resul;
        }

        protected void onPostExecute(Boolean resul) {
            if (resul) {
                Intent intent = new Intent(Proyecto.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idpadre", id);
                startActivity(intent);
            }else{
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Usuario No Registrado", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }*/

    private class validar extends AsyncTask<String,Integer,Boolean> {

        static final String linkService =servidor+"/ServicioRest/webresources/usuario/isuser";
        private int id_usuario;
        private String id_usu;
        protected Boolean doInBackground(String... params) {
            JSONObject jsonParam = new JSONObject();
            boolean resultado = false;
            try {
                jsonParam.put("correo", email);
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost del = new HttpPost(linkService);
                del.setHeader("Accept", "application/json");
                del.setHeader("Content-type", "application/json");
                StringEntity se = new StringEntity(jsonParam.toString());
                del.setEntity(se);
                //
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONObject obj = new JSONObject(respStr);
                if (obj != null) {
                    id_usuario = obj.getInt("id");
                    resultado = true;
                }
                else {
                    resultado = false;
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
            }
            return resultado;
        }

        protected void onPostExecute(Boolean resultado) {
            if (resultado) {
                id_usu = Integer.toString(id_usuario);
                Intent intent = new Intent(Proyecto.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idpadre", id_usu);
                startActivity(intent);
            }else{
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Usuario No Registrado", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }

}



