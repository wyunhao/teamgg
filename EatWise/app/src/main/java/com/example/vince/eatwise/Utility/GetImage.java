package com.example.vince.eatwise.Utility;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

public class GetImage extends AsyncTask<String, Void, Drawable> {
    public AsyncResponse delegate = null;

    public GetImage(AsyncResponse delegate){
        this.delegate = delegate;
    }
    /**
     * Extending the AsyncTask class to make the actual API call.
     * @param params contains the query URL
     * @return the JSON string returned by the API call
     */
    protected Drawable doInBackground(String... params) {
        try {
            String url = params[0];
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(Drawable result) {
        return;
    }
}

