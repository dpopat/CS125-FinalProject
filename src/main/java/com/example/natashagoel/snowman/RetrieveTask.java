package com.example.natashagoel.snowman;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RetrieveTask extends AsyncTask<String, Integer, String> {
    String data = "";
    @Override
    protected String doInBackground(String... params) {
        final String app_id = "88ef67b7";
        final String app_key = "d4f5440e071bc53f7f722b31d5389605";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            data = stringBuilder.toString();
            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        MainActivity.data.setText(this.data);
    }
}