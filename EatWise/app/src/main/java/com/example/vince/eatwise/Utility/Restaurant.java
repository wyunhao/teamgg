package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant{
    private String name;
    private Double latitude;
    private Double longitude;
    private Review[] reviews;
    private PreferenceData preferenceFeature;

    public Restaurant(RestaurantInfo rstInfo, String type, Double cost){
        this.name = rstInfo.getName();
        this.latitude = rstInfo.getLatitude();
        this.longitude  = rstInfo.getLongitude();
        this.reviews = new Review[10];
        this.preferenceFeature = new PreferenceData(type, cost, rating_strTodouble(rstInfo.getAvgRating()));
    }

    public Double rating_strTodouble(String avgString){
        //TODO: convert some fields from RestaurantInfo to set the proper value of preferenceFeature
        return 4.0;
    }

    public Double getLinearDistance(Double user_lat, Double user_long){
        return Math.sqrt(Math.pow(user_lat - this.latitude, 2) + Math.pow(user_long - this.longitude, 2));
    }
}
