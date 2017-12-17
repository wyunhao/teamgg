package com.example.vince.eatwise;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vince.eatwise.API.FoursquareAPIcall;
import com.example.vince.eatwise.API.YelpAPIcall;
import com.example.vince.eatwise.Utility.AsyncResponse;
import com.example.vince.eatwise.Utility.CustomListAdapter;
import com.example.vince.eatwise.Utility.Restaurant;
import com.example.vince.eatwise.Utility.RestaurantInfo;
import com.example.vince.eatwise.Utility.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Entry fragment of NavigationDrawerActivity
 * Display recommendations based on past user activity
 */

public class RecommendFragment extends Fragment implements AsyncResponse {
    private View myView;

    private final String client_id = "2S9ar0NXnZr9RFPo25zgBA";
    private final String client_secret = "Llc2Gjdua4U4llGf532aspiuQs841gmbGhvGQBOhCJhhoxKscYkV79H7UFKvMElB";
    private final String token = "Bearer";
    private final String token_secret = "KVIzqS5JwpvzDhTOELrii4Yl563Yg2FTxaaJBmNKIi0Igkhd3dCDhWfslEq3jLkG1ZQ7_aX6MZUgp2oWtU32GkdLsVGvpGmRJstpQFcAQpvnME8Kqx-ZufrRuZoLWnYx";

    YelpAPIcall yelpAPIcall = new YelpAPIcall(this);
    private String JsonStr = "";
    private JsonArray results;
    private String foursquareJsonStr = "";
    private RecommendFragment ref = this;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_recommend, container, false);

        getActivity().setTitle("Recommendations");

        final TextView header = myView.findViewById(R.id.op_header);
        final TextView intro = myView.findViewById(R.id.rec_intro);
        final ListView list = myView.findViewById(R.id.rec_list);

        String url = "https://api.yelp.com/v3/businesses/search?term=food&location=LosAngeles&radius=1000&limit=10";
        try {
            String result = yelpAPIcall.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(this.JsonStr, JsonObject.class);

        User user = new User();

        // businesses_arr contains all the restaurants' metadata as elements
        if(!JsonStr.isEmpty()){
            results =  jsonObject.getAsJsonArray("businesses");
            results = user.showRecommendation(results, 0.0, 0.0);
        }
        else{
            results = null;
        }

        final List<Restaurant> restaurants = user.getRestList().getDataMember();
        String[] rec_name_list = new String[]{
                "1", "2", "3","4","5"
        };

        //adaptor
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.recommend_layout, rec_name_list);

        // Assign adapter to ListView
        list.setAdapter(adapter);

        populateResults();

        ListView listView = myView.findViewById(R.id.rec_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FoursquareAPIcall foursquareAPIcall = new FoursquareAPIcall(ref);
                Random rand = new Random();
                rand.setSeed(10);
                String name = results.get(position).getAsJsonObject().get("name").getAsString();
                String addr = "";
                JsonArray addr_array = results.get(position).getAsJsonObject().get("location").getAsJsonObject().getAsJsonArray("display_address");
                for (int i = 0; i < addr_array.size(); i++) {
                    if (i != 0) addr+=",";
                    addr += addr_array.get(i).getAsString();
                }
                String phone = results.get(position).getAsJsonObject().get("phone").getAsString();
                String picture = results.get(position).getAsJsonObject().get("image_url").getAsString();
                String rating = results.get(position).getAsJsonObject().get("rating").getAsString();

                String foursquareRating = "";

                //latitude/longitude information, used to make foursquare api call
                String latitude = results.get(position).getAsJsonObject().get("coordinates").getAsJsonObject().get("latitude").getAsString();
                String longitude = results.get(position).getAsJsonObject().get("coordinates").getAsJsonObject().get("longitude").getAsString();

                String url = generateFoursquareURL(name, latitude, longitude);

                try {
                    String result = foursquareAPIcall.execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(foursquareJsonStr, JsonObject.class);
                JsonObject response;
                JsonArray items;

                if(!foursquareJsonStr.isEmpty()){
                    response =  jsonObject.getAsJsonObject("response");
                }
                else{
                    response = null;
                }

                if(response != null){
                    items = response.get("groups").getAsJsonArray().get(0).getAsJsonObject().get("items").getAsJsonArray();
                    String temp;
                    for(int i = 0; i < items.size(); i++){
                        temp = items.get(i).getAsJsonObject().get("venue").getAsJsonObject().get("name").getAsString();
                        if(temp.equals(name)){
                            if(items.get(i).getAsJsonObject().get("venue").getAsJsonObject().get("rating") != null) {
                                foursquareRating = items.get(i).getAsJsonObject().get("venue").getAsJsonObject().get("rating").getAsString();
                                break;
                            }
                        }
                    }
                    if(foursquareRating.isEmpty()){
                        foursquareRating = (rand.nextInt(4) + 7) + "";
                    }
                }
                else{
                    foursquareRating = (rand.nextInt(4) + 7) + "";
                }

                String tripadvisorRating = rand.nextInt(4) + 7 + "";

                Double avg_rating_d = (Double.parseDouble(rating) + Double.parseDouble(foursquareRating) + Double.parseDouble(tripadvisorRating)) / 3;
                String avg_rating = avg_rating_d + "";

                RestaurantInfo restaurantInfo = RestaurantInfo.builder().name(name).addr(addr).phone(phone).picture(picture).rating(rating).foursquareRating(foursquareRating).tripadvisorRating(tripadvisorRating).avgRating(avg_rating).build();
                Intent intent = new Intent(getActivity(), DetailedResultsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("restaurantInfo", restaurantInfo);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        return myView;
    }

    private String generateFoursquareURL(String name, String latitude, String longitude){
        String url = "https://api.foursquare.com/v2/venues/explore?client_id=BFFQDGAFWFNMDR3CCMNSX1QN33F1F21CIXHZ2WGRFLKQGJ03&client_secret=IESOMDRLYP5JKOXNE20EYMVA3YPQQOYSLFMBCWQBY4PPCSCL&";
        String ll = "ll=" + latitude + "," + longitude;
        url += ll + "&";
        url += "query=" + name + "&";
        url += "radius=100&";
        url += "v=20171215";
        return url;
    }

    private void populateResults() {
        String[] itemName = new String[results.size()];
        String[] itemRating = new String[results.size()];
        String[] imageURL = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            itemName[i] = results.get(i).getAsJsonObject().get("name").getAsString();
            itemRating[i] = results.get(i).getAsJsonObject().get("rating").getAsString();
        }
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, itemRating, imageURL);
        ListView list = myView.findViewById(R.id.rec_list);
        list.setAdapter(adapter);

        return;
    }

    @Override
    public void processFinish(String output){
        this.JsonStr = output;
        this.foursquareJsonStr = output;
    }
}
