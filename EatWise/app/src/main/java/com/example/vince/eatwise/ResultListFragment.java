package com.example.vince.eatwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vince.eatwise.API.FoursquareAPIcall;
import com.example.vince.eatwise.Utility.AsyncResponse;
import com.example.vince.eatwise.Utility.CustomListAdapter;
import com.example.vince.eatwise.Utility.RestaurantInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * This fragment displays the list of restaurants with names and images.
 */

public class ResultListFragment extends Fragment implements AsyncResponse{
    private View myView;
    private JsonArray results;
    private ArrayList<RestaurantInfo> restaurants = new ArrayList<RestaurantInfo>();
    private String foursquareJsonStr = "";
    private ResultListFragment ref = this;

    /**
     *  Get rating information from yelp, foursquare and tripadvisor APIs, display in the restaurants with their rates in a list and send the rating to DetailedActivity for display
     * @param inflater LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_result_list, container, false);
        getActivity().setTitle(R.string.title_result_list);
        results = ((SearchResultActivity)getActivity()).getResults();

        FloatingActionButton fab =  myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i < results.size(); i++) {
                    RestaurantInfo restaurantInfo = constructRestaurantInfo(i);
                    restaurants.add(restaurantInfo);
                }

                Intent intent = new Intent(getActivity(), MapsActivity.class);

                Bundle b = new Bundle();
                b.putSerializable("coordinates", restaurants);
                intent.putExtras(b);

                startActivity(intent);
            }
        });

        TextView textView = myView.findViewById(R.id.error_msg);
        ListView listView = myView.findViewById(R.id.list);

        if (results.size() != 0) {
            textView.setVisibility(View.GONE);
            populateResults();
        } else {
            listView.setVisibility(View.GONE);
        }

        // TODO: send to DetailedActivity: RestaurantInfo
        /**
         * get rating information from yelp, foursquare and tripadvisor, and send the rating
         * to DetailedActivity for display
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // TODO: build RestaurantInfo Object
                final RestaurantInfo restaurantInfo = constructRestaurantInfo(position);

                Intent intent = new Intent(getActivity(), DetailedResultsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("restaurantInfo", restaurantInfo);
                intent.putExtras(b);
                startActivity(intent);
            }

            /**
             * given the name, latitude and longitude of the selected restaurant, generate the corresponding foursquare api query string
             * @param name name of the restaurant
             * @param latitude latitude of the restaurant
             * @param longitude longitude of the restaurant
             * @return
             */
            private String generateFoursquareURL(String name, String latitude, String longitude){
                String url = "https://api.foursquare.com/v2/venues/explore?client_id=BFFQDGAFWFNMDR3CCMNSX1QN33F1F21CIXHZ2WGRFLKQGJ03&client_secret=IESOMDRLYP5JKOXNE20EYMVA3YPQQOYSLFMBCWQBY4PPCSCL&";
                String ll = "ll=" + latitude + "," + longitude;
                url += ll + "&";
                url += "query=" + name + "&";
                url += "radius=100&";
                url += "v=20171215";
                return url;
            }
        });

        return myView;
    }

    /**
     * Retrieve data from Json array that is returned by API
     * Store them in forms of array and list
     * Populate the list view with our custom list adapter and these data
     */
    private void populateResults() {
        String[] itemName = new String[results.size()];
        String[] itemRating = new String[results.size()];
        String[] imageURL = new String[results.size()];
        List<List<Double>> c = new ArrayList<>();


        for (int i = 0; i < results.size(); i++) {
            itemName[i] = results.get(i).getAsJsonObject().get("name").getAsString();
            itemRating[i] = results.get(i).getAsJsonObject().get("rating").getAsString();
            imageURL[i] = results.get(i).getAsJsonObject().get("image_url").getAsString();
        }

        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, itemRating, imageURL);
        ListView list = myView.findViewById(R.id.list);
        list.setAdapter(adapter);

        return;
    }



    public void processFinish(String output){
        this.foursquareJsonStr = output;
    }


    private RestaurantInfo constructRestaurantInfo(int position) {
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

        return RestaurantInfo.builder().name(name).addr(addr).phone(phone).picture(picture).rating(rating).foursquareRating(foursquareRating)
                .latitude(Double.valueOf(latitude)).longitude(Double.valueOf(longitude)).tripadvisorRating(tripadvisorRating)
                .avgRating(avg_rating).build();
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
}