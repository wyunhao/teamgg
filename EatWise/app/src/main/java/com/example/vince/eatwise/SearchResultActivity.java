package com.example.vince.eatwise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vince.eatwise.QueryData.QueryFilter;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {
    String client_id = "2S9ar0NXnZr9RFPo25zgBA";
    String client_secret = "Llc2Gjdua4U4llGf532aspiuQs841gmbGhvGQBOhCJhhoxKscYkV79H7UFKvMElB";
    String token = "Bearer";
    String token_secret = "KVIzqS5JwpvzDhTOELrii4Yl563Yg2FTxaaJBmNKIi0Igkhd3dCDhWfslEq3jLkG1ZQ7_aX6MZUgp2oWtU32GkdLsVGvpGmRJstpQFcAQpvnME8Kqx-ZufrRuZoLWnYx";

//    YelpAPIFactory apiFactory = null;
//    YelpAPI yelpAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

//        createAPI();
//        Intent intent = getIntent();
//        QueryFilter filter = (QueryFilter) intent.getExtras().getSerializable("query"); //gets the query filter

    }

//    private void createAPI(){
//        apiFactory = new YelpAPIFactory(client_id, client_secret, token, token_secret);
//        yelpAPI = apiFactory.createAPI();
//    }
//
//    private void APIsearch(QueryFilter filter){
//        try {
//            String ll = filter.getLocation();
//            String latitude = "";
//            String longitude = "";
//            for (int i = 0; i < ll.length(); i++) {
//                char c = ll.charAt(i);
//                if (c == ',') {
//                    latitude = ll.substring(0, i);
//                    longitude = ll.substring(i + 1);
//                }
//            }
//            Double latitude_d = Double.parseDouble(latitude);
//            Double longitude_d = Double.parseDouble(longitude);
//            Long radius = filter.getDistance();
//            String radius_s = radius + "";
//            String keyword = filter.getKeyword();
//            Map<String, String> params = new HashMap<>();
//            params.put("term", keyword);
//            params.put("radius_filter", radius_s);
//            CoordinateOptions coordinate = CoordinateOptions.builder()
//                    .latitude(latitude_d)
//                    .longitude(longitude_d).build();
//            Call<SearchResponse> call = yelpAPI.search(coordinate, params);
//            Response<SearchResponse> response = call.execute();
//            SearchResponse searchResponse = response.body();
//
//            ArrayList<Business> businesses = searchResponse.businesses();
//            String businessName = businesses.get(0).name();
//            Double rating = businesses.get(0).rating();
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    }
}
