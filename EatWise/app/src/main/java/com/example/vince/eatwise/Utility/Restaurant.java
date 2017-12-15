package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant {
    private String name;
    private String location;
    private Review[] reviews;
    private PreferenceData preferenceFeature;

    public Restaurant(final String name, final String location, final String type, final Double cost, final Rating rating){
        this.name = name;
        this.location = location;
        this.preferenceFeature = new PreferenceData(type, cost,rating);
        this.reviews = new Review[10];
    }

    /**
     * given location of the restaurant, get the current location of the user and calculate distance, for navigation mode
     * @param location String: location of restaurant
     */

    /*
     * set the attribute of PreferenceData inside class Restaurant does not make sense
     * should calculate the distance somewhere else and build up PreferenceFeature object
     * and set that PreferenceFeature as the attribute of Restaurant
     */
    /*
    public void setDistance(String location){
        //TODO:given location of the restaurant, get the current location of the user and calculate distance, for navigation mode
    }


    public void setDistance(Double distance){//for recording view based on search info
        this.preferenceFeature.setDistance(distance);
    }
    */
}
