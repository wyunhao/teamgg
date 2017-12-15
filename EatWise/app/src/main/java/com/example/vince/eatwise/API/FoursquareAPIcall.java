package com.example.vince.eatwise.API;


/*
    CLASS THAT DEALS WITH THE API CALL. EXTENDS ASYNCTASK SO THAT IT CAN ACCESS INTERNET
 */

import android.os.AsyncTask;

import com.example.vince.eatwise.Utility.AsyncResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoursquareAPIcall extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;

    public FoursquareAPIcall(AsyncResponse delegate){
        this.delegate = delegate;
    }

    /**
     * Extending the AsyncTask class to make the actual API call.
     * @param params contains the query URL
     * @return the JSON string returned by the API call
     */
    protected String doInBackground(String... params) {
        try {
            String queryURL = params[0];
            URL url = new URL (queryURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty  ("Authorization", "Bearer " + "KVIzqS5JwpvzDhTOELrii4Yl563Yg2FTxaaJBmNKIi0Igkhd3dCDhWfslEq3jLkG1ZQ7_aX6MZUgp2oWtU32GkdLsVGvpGmRJstpQFcAQpvnME8Kqx-ZufrRuZoLWnYx");
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   =
                    new BufferedReader (new InputStreamReader(content));
            String line;
            String JsonStr = "";
            while ((line = in.readLine()) != null) {
                JsonStr += line;
            }
            delegate.processFinish(JsonStr);
            return JsonStr;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void onPostExecute(String result) {
        return;
    }
}



