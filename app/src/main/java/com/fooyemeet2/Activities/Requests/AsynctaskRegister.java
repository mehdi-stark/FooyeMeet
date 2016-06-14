package com.fooyemeet2.Activities.Requests;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.fooyemeet2.Activities.Fragments.FragmentProfil;
import com.fooyemeet2.Activities.Fragments.FragmentProfileTab;
import com.fooyemeet2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mehdi on 07/03/2016.
 */

public class AsynctaskRegister  extends AsyncTask<String , Void, String> {

    private String url = null;
    private int code2;
    private Activity act;
    private ProgressDialog dialo;


    public AsynctaskRegister(Activity act) {
        this.act = act;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String request(String url, JSONObject raw) {

        String result = "false";
        OutputStream os;
        InputStream is;

        String logs = raw.toString();

        URL uri;
        HttpURLConnection conn = null;

        try {
            uri = new URL(url);

            conn = (HttpURLConnection) uri.openConnection();

            conn.setRequestMethod("POST");

            conn.setReadTimeout(5000);
            conn.setConnectTimeout(15000);

            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoInput(true); //autoriser ecriture
            conn.setDoOutput(true); // lecture
            conn.setUseCaches(false);

            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(logs.getBytes());

            os.flush(); //ecrire
            conn.connect();

            code2 = conn.getResponseCode(); //valeur de retour de lapi
            if (code2 == HttpURLConnection.HTTP_CREATED) {
                is = conn.getInputStream();
                result = convertStreamToString(is);
            } else {
                result = "false";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert conn != null;
            conn.disconnect();
        }
        return result;
    }

    private String getUrl() {
        url = "http://ec2-52-59-251-0.eu-central-1.compute.amazonaws.com:8080/api/users";
        return (url);
    }

    private String getConnexion(String url, String... params) { //creat json param a l api avec le chemin
        JSONObject json = new JSONObject();
        String resultat = null;

        try {
            json.put("name", params[0]);
            json.put("username", params[1]);
            json.put("password", params[2]);
            json.put("email", params[3]);
            json.put("city", params[4]);

            resultat = request(url, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialo.cancel();
        return (resultat);
    }

    @Override
    protected String doInBackground(String... params) {
        String resultat = null;

        resultat = getConnexion(getUrl(), params);

        return resultat;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!isOnline()) {
            cancel(true);
        } else {
            this.dialo = new ProgressDialog(act);
            dialo.setMessage("Login... Wait");
            dialo.show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (dialo.isShowing() || dialo != null) {
            dialo.dismiss();
            dialo = null;
        }
        FragmentProfileTab fragmentPofil = new FragmentProfileTab();
        ((FragmentActivity)act).getSupportFragmentManager().beginTransaction().replace(R.id.container_login, fragmentPofil).commit();
    }
}