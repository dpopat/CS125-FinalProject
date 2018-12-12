package com.example.natashagoel.com;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class RetrieveTask extends AsyncTask<String, Integer, String> {
    String data = "";
    final int minWordLength = 4;
    final int maxWordLength = 12;
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

        MainActivity.dictionary = parseJSON(this.data);
    }

    private ArrayList<String> parseJSON(String input) {
        ArrayList<String> listWords = new ArrayList<>();
        try {
            JSONObject js = new JSONObject(input);
            JSONArray results = js.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jObj = (JSONObject) results.get(i);
                String word = jObj.getString("word");
                boolean hasDigit = word.matches(".*\\d+.*");
                if (minWordLength <= word.length() && word.length() <= maxWordLength && !hasDigit && word.indexOf("-") == -1 && word.indexOf(" ") == -1) {
                    listWords.add(word);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listWords;
    }
}