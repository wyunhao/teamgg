package com.example.vince.eatwise.Utility;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GetImage extends AsyncTask<String, Void, Drawable> {
    public AsyncResponse delegate;

    public GetImage(final AsyncResponse delegate){
        this.delegate = delegate;
    }

    /**
     * Extending the AsyncTask class to make the actual API call.
     * @param params String: contains the query URL
     * @return Drawable: the JSON string returned by the API call
     */
    protected Drawable doInBackground(final String... params) {
        try {
            final String url = params[0];
            final InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(final Drawable result) {
        return;
    }
}

