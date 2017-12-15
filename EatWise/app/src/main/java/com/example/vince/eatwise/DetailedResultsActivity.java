package com.example.vince.eatwise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.Manifest;

import com.example.vince.eatwise.Utility.AsyncResponse;
import com.example.vince.eatwise.Utility.GetImage;
import com.example.vince.eatwise.Utility.RestaurantInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class DetailedResultsActivity extends AppCompatActivity implements AsyncResponse{

    private GetImage ImageGetter = new GetImage(this);
    private ImageView restaurant_image = null;
    private RatingBar ratingBar;
    private DatabaseReference mRootRef;

    /**
     * Initialize the values to be shown in each field
     * @param savedInstanceState Saved state from the last session
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_results);
        setTitle("Restaurant Info");
        Random rand = new Random();
        mRootRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent(); //get the corresponding entry of business class

        RestaurantInfo info = (RestaurantInfo) intent.getExtras().getSerializable("restaurantInfo");
        String r_name = info.getName();
        String r_addr = info.getAddr();
        String r_phone = info.getPhone();
        String pic_url = info.getPicture();
        String yelp_rating = info.getRating();
        String foursquare_rating = info.getFoursquareRating();
        Double foursquare_rating_d = Double.parseDouble(foursquare_rating) / 2;
        String tripadvisor_rating = info.getTripadvisorRating();
        Double tripadvisor_rating_d = Double.parseDouble(tripadvisor_rating) / 2;
        Double avg_rating_d = (Double.parseDouble(yelp_rating) + foursquare_rating_d + tripadvisor_rating_d) / 3;
        String avg_rating = String.format("%.2f", avg_rating_d);

        //getting image
        restaurant_image = findViewById(R.id.restaurant_image);
        Drawable d = null;
        try {
            d = this.ImageGetter.execute(pic_url).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        restaurant_image.setImageDrawable(d);

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
        textView.setText(foursquare_rating_d + "");
        setFontColor(foursquare_rating_d, textView);
        textView = findViewById(R.id.textView_tripadvisor);
        textView.setText(tripadvisor_rating_d + "");
        setFontColor(tripadvisor_rating_d, textView);
        textView = findViewById(R.id.textView_overall);
        textView.setText(avg_rating);
        setFontColor(Double.parseDouble(avg_rating), textView);

        // Set up rating bar
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // TODO: decide unique id for each restaurants. For now: name
                mRootRef.child("Restaurants").child(r_name).child("rating").setValue(ratingBar.getRating());
            }
        });

    }

    /**
     * Adjust the text color based on the rating being displayed
     * @param rating_d Double: The numerical rating to be shown
     * @param rating TextView: The text view that the the rating will be shown on
     */
    private void setFontColor(Double rating_d, TextView rating) {
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

    public void processFinish(String output){
        return;
    }
}

