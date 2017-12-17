package com.example.vince.eatwise.Utility;

import android.location.Location;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.example.vince.eatwise.Constants.Constant;
import com.google.gson.JsonArray;

@Getter
@Setter
public class User {
    private History history;
    private PreferenceData preference;
    private RestaurantArray restList;
    private String uid;
    private FirebaseUser user;

    public User(){
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.uid = user.getUid();
        this.preference = new PreferenceData();
        this.history = new History();
        this.restList = new RestaurantArray();
    }

    //account info. management to account management

    //TODO: get history from database

    //grow history
    public void addHistoryByQuery(final Query newQuery) {
        this.history.addQuery(newQuery);
    }
    public void addHistoryByViewed(final Viewed newViewed) {
        this.history.addViewed(newViewed);
    }

    /**
     * Update preferences and recommendations
     */
    public JsonArray showRecommendation(JsonArray results, Double longitude, Double latitude){
        //this.history.updatePreference(this.preference); //skipped for refactoring history
        return this.history.calculateByPreference(this.preference, results, longitude, latitude );
    }
}