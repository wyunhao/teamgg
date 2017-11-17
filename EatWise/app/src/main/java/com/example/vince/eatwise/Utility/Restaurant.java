package com.example.vince.eatwise.Utility;

/*
* Last modified on 2017-11-16
* */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant {
    public String name;
    public String location;
    public Review[] reviews;
    public Preference_Data feature;

    public Restaurant(String name, String location, String type, Double cost, Rating rating){
        this.name = name;
        this.location = location;
        this.feature = new Preference_Data(type, cost,rating);
        this.reviews = new Review[10];
    }

    /**
     * given location of the restaurant, get the current location of the user and calculate distance, for navigation mode
     * @param location location of restaurant
     */


    public void setDistance(String location){
        //TODO:given location of the restaurant, get the current location of the user and calculate distance, for navigation mode
    }


    public void set_distance(Double distance){//for recording view based on search info
        this.feature.setDistance(distance);
    }
}
