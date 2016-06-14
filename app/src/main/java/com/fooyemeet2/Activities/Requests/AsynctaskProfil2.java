package com.fooyemeet2.Activities.Requests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.fooyemeet2.Activities.Fragments.FragmentProfil;
import com.fooyemeet2.Activities.Fragments.FragmentProfileTab;
import com.fooyemeet2.Activities.Fragments.Profil;
import com.fooyemeet2.Activities.parserJson.ParserJson;
import com.fooyemeet2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Mehdi on 07/03/2016.
 */
public class AsynctaskProfil2 extends AsyncTask<String , Void, String> {

    private String url = null;
    private int code2;
    private Context act;
    private ProgressDialog dialo;


    public AsynctaskProfil2(Context act) {
        this.act = act;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
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
        InputStream is;

        String logs = raw.toString();

        URL uri;
        HttpURLConnection conn = null;

        try {
            uri = new URL(url);

            conn = (HttpURLConnection) uri.openConnection();

            String token;

            SharedPreferences sharedPreferences = act.getSharedPreferences("clerep", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("token", null);


            conn.setRequestMethod("GET");
            conn.setRequestProperty("x-access-token", token);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(15000);

            conn.setRequestProperty("Content-Type", "application/json");

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
        url = "http://ec2-52-59-251-0.eu-central-1.compute.amazonaws.com:8080/api/users/me";
        return (url);
    }

    private String getConnexion(String url, String... params) { //creat json param a l api avec le chemin
        JSONObject json = new JSONObject();
        String resultat = null;
        String token = null;
        try {
            json.put("token", token);

            resultat = request(url, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialo.cancel();
        return (resultat);
    }

    @Override
    protected String doInBackground(String... params) {
        String resultat;
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
        JSONObject new_js = new JSONObject();
        try {
            JSONObject js = new JSONObject(s);
            new_js = js.getJSONObject("fooyemeet");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Profil profilslist = ParserJson.parserGetProfil(new_js.toString());

        FragmentProfil fragmentProfil = new FragmentProfil();
//        fragmentProfil.getProfil(profilslist);
        ((FragmentActivity)act).getSupportFragmentManager().beginTransaction().replace(R.id.container_login, new FragmentProfileTab()).commit();
    }
}
