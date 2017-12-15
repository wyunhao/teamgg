package com.example.vince.eatwise;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.vince.eatwise.Utility.AsyncResponse;
import com.example.vince.eatwise.Utility.CustomListAdapter;
import com.example.vince.eatwise.Utility.GetImage;
import com.example.vince.eatwise.Utility.RestaurantInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static android.view.Gravity.CENTER;

/**
 * This fragment displays the list of restaurants with names and images.
 */

public class ResultListFragment extends Fragment {
    private View myView;
    private JsonArray results;
    private List<Location> locations = new ArrayList<Location>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_result_list, container, false);
        getActivity().setTitle(R.string.title_result_list);
        results = ((SearchResultActivity)getActivity()).getResults();

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MapsActivity.class);

                Bundle b = new Bundle();
                b.putSerializable("coordinates", (Serializable) locations);
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
        List<List<Double>> c = new ArrayList<>();


        for (int i = 0; i < results.size(); i++) {
            itemName[i] = results.get(i).getAsJsonObject().get("name").getAsString();
            itemRating[i] = results.get(i).getAsJsonObject().get("rating").getAsString();
            imageURL[i] = results.get(i).getAsJsonObject().get("image_url").getAsString();
            JsonObject coordinates = results.get(i).getAsJsonObject().get("coordinates").getAsJsonObject();
            final Double la = coordinates.get("latitude").getAsDouble();
            final Double lo = coordinates.get("longitude").getAsDouble();
            List<Double> eachCod = new ArrayList<>();
            eachCod.add(la);
            eachCod.add(lo);
            c.add(eachCod);
        }
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, itemRating, imageURL);
        ListView list = myView.findViewById(R.id.list);
        list.setAdapter(adapter);

        return;
    }

    @Setter
    @Getter
    public class Location implements Serializable {
        private List<List<Double>> coordinate;
    }
}