package com.example.vince.eatwise;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vince.eatwise.API.YelpAPIcall;
import com.example.vince.eatwise.Utility.AsyncResponse;
import com.example.vince.eatwise.Utility.CustomListAdapter;
import com.example.vince.eatwise.Utility.RestaurantInfo;
import com.example.vince.eatwise.Utility.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONString;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/*
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
        // businesses_arr contains all the restaurants' metadata as elements
        if(!JsonStr.isEmpty()){
            results =  jsonObject.getAsJsonArray("businesses");
        }
        else{
            results = null;
        }

        //TODO:get user after login
        //producing mock recommendation
        User user = new User("username", "12345", "xxxx@gmail.com", "Larry", "David", new Date());
        user.show_recommendation();
        String[] rec_name_list = new String[]{
                user.getRecList().data_member.get(0).name,
                user.getRecList().data_member.get(1).name,
                user.getRecList().data_member.get(2).name,
                user.getRecList().data_member.get(3).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name,
                user.getRecList().data_member.get(4).name
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
                // TODO: build RestaurantInfo Object
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

                RestaurantInfo restaurantInfo = RestaurantInfo.builder().name(name).addr(addr).phone(phone).picture(picture).rating(rating).build();
                Intent intent = new Intent(getActivity(), DetailedResultsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("restaurantInfo", restaurantInfo);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        return myView;
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
        ListView list = (ListView) myView.findViewById(R.id.rec_list);
        list.setAdapter(adapter);

        return;
    }

    @Override
    public void processFinish(String output){
        this.JsonStr = output;
    }
}