package com.fooyemeet2.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class FragmentProfil extends Fragment {

    private TextView username;
    private TextView age;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile2, container, false);
        AsynctaskProfil asynctaskProfil = new AsynctaskProfil();
        asynctaskProfil.execute();
        
        username = (TextView) rootView.findViewById(R.id.nameProfile);
        age = (TextView) rootView.findViewById(R.id.ageProfile);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Change Username")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return rootView;
    }
    
    class AsynctaskProfil extends AsyncTask<String , Void, String> {

        private String url = null;
        private int code2;
        private ProgressDialog dialo;
        
        private String convertStreamToString(InputStream is) {
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

        private String request(String url) {

            String result = "false";
            InputStream is;

            URL uri;
            HttpURLConnection conn = null;

            try {
                uri = new URL(url);

                conn = (HttpURLConnection) uri.openConnection();

                String token;

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("clerep", Context.MODE_PRIVATE);
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
                }
                else {
                    is = conn.getInputStream();
                    result = convertStreamToString(is);                }
                }
                catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                assert conn != null;
                conn.disconnect();
            }
            return result;
        }

        private String getUrl() {
            url = "http://ec2-52-59-251-0.eu-central-1.compute.amazonaws.com:8080/api/users/me";
            return (url);
        }

        @Override
        protected String doInBackground(String... params) {
            String resultat;
            resultat = request(getUrl());

            return resultat;
        }

        private boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isOnline()) {
                cancel(true);
            } else {
                this.dialo = new ProgressDialog(getActivity());
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
            username.setText(profilslist.getUsername());
            age.setText(profilslist.getAge());
        }
    }
}
