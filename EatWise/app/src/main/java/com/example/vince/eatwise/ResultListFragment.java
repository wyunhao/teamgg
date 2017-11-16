package com.example.vince.eatwise;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.ArrayList;

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

        if (results.size() != 0) {
            TextView textView = myView.findViewById(R.id.error_msg);
            textView.setVisibility(View.GONE);
            populateResults();
        } else {
            ListView listView = myView.findViewById(R.id.list);
            listView.setVisibility(View.GONE);
        }


        // TODO: send to DetailedActivity: RestaurantInfo
        return myView;
    }

    private void populateResults() {
        String[] itemName = new String[results.size()];
        String[] itemRating = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            itemName[i] = results.get(i).getAsJsonObject().get("name").getAsString();
            itemRating[i] = results.get(i).getAsJsonObject().get("rating").getAsString();
        }
            // TODO: this is temporary, future: support images
        Integer imageID[] = new Integer[20];
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemName, itemRating, imageID);
        ListView list = (ListView) myView.findViewById(R.id.list);
        list.setAdapter(adapter);

        return;


    }
}

class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity contextActivity;
    private final String[] itemName;
    private final String[] itemRating;


    public CustomListAdapter(Activity contextActivity, String[] itemName, String[] itemRating, Integer[] imageID) {
        super(contextActivity, R.layout.list_row, itemName);
        // TODO Auto-generated constructor stub

        this.contextActivity=contextActivity;
        this.itemName = itemName;
        this.itemRating = itemRating;
    }

    public View getView(int position,View view,ViewGroup parent) {
        // TODO: support restaurant icons, and addresses
        LayoutInflater inflater=contextActivity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        TextView title = (TextView) rowView.findViewById(R.id.item);
        TextView rating = (TextView) rowView.findViewById(R.id.item_rating);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

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

    };
}
