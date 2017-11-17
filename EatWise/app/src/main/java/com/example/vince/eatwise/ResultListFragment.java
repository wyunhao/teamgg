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
import com.example.vince.eatwise.Utility.GetImage;
import com.example.vince.eatwise.Utility.RestaurantInfo;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.view.Gravity.CENTER;

/**
 * This fragment displays the list of restaurants with names and images.
 */

public class ResultListFragment extends Fragment {
    private View myView;
    private JsonArray results;

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
                Snackbar.make(view, "Switch to Map view", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        for (int i = 0; i < results.size(); i++) {
            itemName[i] = results.get(i).getAsJsonObject().get("name").getAsString();
            itemRating[i] = results.get(i).getAsJsonObject().get("rating").getAsString();
            imageURL[i] = results.get(i).getAsJsonObject().get("image_url").getAsString();
        }
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, itemRating, imageURL);
        ListView list = (ListView) myView.findViewById(R.id.list);
        list.setAdapter(adapter);

        return;
    }
}

class CustomListAdapter extends ArrayAdapter<String>  implements AsyncResponse {

    private final Activity contextActivity;
    private final String[] itemName;
    private final String[] itemRating;
    private final String[] imageURL;

    public CustomListAdapter(Activity contextActivity, String[] itemName, String[] itemRating, String[] imageURL) {
        super(contextActivity, R.layout.list_row, itemName);
        // TODO Auto-generated constructor stub

        this.contextActivity=contextActivity;
        this.itemName = itemName;
        this.itemRating = itemRating;
        this.imageURL = imageURL;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=contextActivity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        TextView title = (TextView) rowView.findViewById(R.id.item);
        TextView rating = (TextView) rowView.findViewById(R.id.item_rating);
//        ImageView restaurant_image = rowView.findViewById(R.id.icon);

//        GetImage imageGetter = new GetImage(this);
//        Drawable d = null;
//        try {
//            d = imageGetter.execute(this.imageURL[position]).get();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        restaurant_image.setImageDrawable(d);

        title.setText(this.itemName[position]);
        rating.setText(this.itemRating[position] + "/5.0");
        double rating_d = Double.parseDouble(itemRating[position]);

        // red4-5, orange3-4, lightorange2-3, yellow1-2, grey0-1
        if (rating_d >= 4 && rating_d <= 5) {
            rating.setTextColor(Color.parseColor("#ff067c"));
        } else if (rating_d >= 3 && rating_d < 4) {
            rating.setTextColor(Color.parseColor("#e9743f"));
        } else if (rating_d >= 2 && rating_d < 3) {
            rating.setTextColor(Color.parseColor("#ff9e00"));
        } else if (rating_d >= 1 && rating_d < 2) {
            rating.setTextColor(Color.parseColor("#fede86"));
        } else {
            rating.setTextColor(Color.parseColor("#d8d8d0"));
        }



        return rowView;

    }

    public void processFinish(String output){
        return;
    }

}
