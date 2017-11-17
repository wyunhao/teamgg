package com.example.vince.eatwise.Utility;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vince.eatwise.R;

public class CustomListAdapter extends ArrayAdapter<String> implements AsyncResponse {

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

    public View getView(int position, View view, ViewGroup parent) {
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
