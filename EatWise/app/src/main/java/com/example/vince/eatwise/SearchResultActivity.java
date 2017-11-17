package com.example.vince.eatwise;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vince.eatwise.QueryData.QueryFilter;
import com.example.vince.eatwise.Utility.AsyncResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity implements AsyncResponse{
    private final String client_id = "2S9ar0NXnZr9RFPo25zgBA";
    private final String client_secret = "Llc2Gjdua4U4llGf532aspiuQs841gmbGhvGQBOhCJhhoxKscYkV79H7UFKvMElB";
    private final String token = "Bearer";
    private final String token_secret = "KVIzqS5JwpvzDhTOELrii4Yl563Yg2FTxaaJBmNKIi0Igkhd3dCDhWfslEq3jLkG1ZQ7_aX6MZUgp2oWtU32GkdLsVGvpGmRJstpQFcAQpvnME8Kqx-ZufrRuZoLWnYx";

    YelpAPIcall yelpAPIcall = new YelpAPIcall(this);
    private String JsonStr = "";
    private JsonArray businesses_arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        QueryFilter filter = (QueryFilter) intent.getExtras().getSerializable("filter"); //gets the query filter
        String url = generateURL(filter);

        try {
            String result = yelpAPIcall.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // JsonStr value assigned
        //parsing JSON str
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(this.JsonStr, JsonObject.class);
        // businesses_arr contains all the restaurants' metadata as elements
        if(!JsonStr.isEmpty()){
            businesses_arr =  jsonObject.getAsJsonArray("businesses");
        }
        else{
            businesses_arr = null;
        }
//        String name = businesses_arr.get(0).getAsJsonObject().get("name").getAsString();
//        textView.setText(name);

        gotoResultListFragment();
    }

    /*
    HELPER FUNCTION TO GENERATE SEARCH URL
     */

    public JsonArray getResults() {
        return businesses_arr;
    }

    private void gotoResultListFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new ResultListFragment()).commit();
    }

    /**
     * Use filter information to create valid URL to search for restaurant information
     * @param filter object that contains all the filter information
     * @return a valid URL that integrates the filter information
     */
    private String generateURL(QueryFilter filter){
        String keyword = "food";
        Integer distance = filter.getDistance();
        String distance_s = distance + "";
        String location = filter.getLocation();
        String queryURL = "https://api.yelp.com/v3/businesses/search?";
        queryURL += "term=" + keyword + "&";
        queryURL += "location=" + location + "&";
        queryURL += "radius=" + distance_s + "&";
        queryURL += "limit=10";
        return queryURL;
    }

    @Override
    public void processFinish(String output){
        this.JsonStr = output;
    }
}

 /*
    CLASS THAT DEALS WITH THE API CALL. EXTENDS ASYNCTASK SO THAT IT CAN ACCESS INTERNET
 */

class YelpAPIcall extends AsyncTask<String, Void, String>{
    public AsyncResponse delegate = null;

    public YelpAPIcall(AsyncResponse delegate){
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


