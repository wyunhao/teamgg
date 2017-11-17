package com.example.vince.eatwise;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vince.eatwise.Utility.RestaurantInfo;

import java.util.Random;

public class DetailedResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_results);
        Random rand = new Random();

        Intent intent = getIntent(); //get the corresponding entry of business class

        RestaurantInfo info = (RestaurantInfo) intent.getExtras().getSerializable("restaurantInfo");
        String r_name = info.getName();
        String r_addr = info.getAddr();
        String r_phone = info.getPhone();
        String pic_url = info.getPicture();
        String yelp_rating = info.getRating() + "";
        // TODO: ACTUALLY GET THESE RATING FROM API CALLS
        String foursquare_rating = rand.nextInt(5) + 1 + "";
        String tripadvisor_rating = rand.nextInt(5) + 1 + "";
        Double avg_rating_d = (Double.parseDouble(yelp_rating) + Double.parseDouble(foursquare_rating) + Double.parseDouble(tripadvisor_rating))/3;
        String avg_rating = avg_rating_d + "";

        // set general information
        TextView textView_name = findViewById(R.id.textView_m_name);
        textView_name.setText(r_name);
        TextView textView_addr = findViewById(R.id.textView_addr);
        textView_addr.setText(r_addr);
        TextView textView_phone = findViewById(R.id.textView_phone);
        textView_phone.setText(r_phone);

        // set ratings
        TextView textView = findViewById(R.id.textView_yelp);
        textView.setText(yelp_rating);
        setFontColor(Double.parseDouble(yelp_rating), textView);
        textView = findViewById(R.id.textView_foursquare);
        textView.setText(foursquare_rating);
        setFontColor(Double.parseDouble(foursquare_rating), textView);
        textView = findViewById(R.id.textView_tripadvisor);
        textView.setText(tripadvisor_rating);
        setFontColor(Double.parseDouble(tripadvisor_rating), textView);
        textView = findViewById(R.id.textView_overall);
        textView.setText(avg_rating);
        setFontColor(Double.parseDouble(avg_rating), textView);
    }

    private void setFontColor(Double rating_d, TextView rating){
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
    }
}
