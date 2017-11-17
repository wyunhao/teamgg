package com.example.vince.eatwise.Utility;

import lombok.Getter;

@Getter

public class Restaurant {
    private String name;
    private String location;
    private Review[] reviews;
    private PreferenceData preferenceFeature;

    public Restaurant(String name, String location, String type, Double cost, Rating rating){
        this.name = name;
        this.location = location;
        this.preferenceFeature = new PreferenceData(type, cost,rating);
        this.reviews = new Review[10];
    }

    /**
     * given location of the restaurant, get the current location of the user and calculate distance, for navigation mode
     * @param location location of restaurant
     */
    public void setDistance(String location){
        //TODO:given location of the restaurant, get the current location of the user and calculate distance, for navigation mode
    }


    public void setDistance(Double distance){//for recording view based on search info
        this.preferenceFeature.setDistance(distance);
    }
}
