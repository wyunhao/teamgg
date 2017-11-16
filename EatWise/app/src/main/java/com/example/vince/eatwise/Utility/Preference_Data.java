package com.example.vince.eatwise.Utility;

import java.util.Date;

/*
 * Last update on 2017-11-15
 */



public class Preference_Data {//kept by user for recommendation
    public enum Rating{ZERO, ONE, TWO, THREE, FOUR, FIVE}//the user input should be converted to this enum type
    public String type; //see enum CuisineType in QueryData
    public Double cost; //if ranged, consider enum, array, etc; else introduce two variables; current treat as +- $2 range
    public Rating rating; //name change; type changed: quantized to 0,1,2,3,4,5; no less than input by default
    public Double distance;
    public Double freqByType, freqByCost, freqByRating, freqByDistance; //added attr. for calculating recommendation; rather awkward

    public Preference_Data(String type, Double cost, Rating rating, Double distance){
        this.type = type;
        this.cost = cost;
        this.rating = rating;
        this.distance = distance;
        this.freqByType = this.freqByCost = this.freqByRating = this.freqByDistance = 0.0;
    }
    public Preference_Data(String type, Double cost, Rating rating){
        this(type, cost, rating, 0.0);
    }
    public Preference_Data(String type, Double cost){
        this(type, cost, Rating.ZERO, 0.0);
    }
    public Preference_Data(String type){
        this(type, 0.0, Rating.ZERO, 0.0);
    }
    public Preference_Data(){
        this("", 0.0, Rating.ZERO, 0.0); //the default restaurant type can either be null or empty
    }

    //update preference when required and new history is generated
    public void update_preference(String type, Double cost, Rating rating, Double distance,
                                  Double freqByType, Double freqByCost, Double freqByRating, Double freqByDistance){
        this.type = type;
        this.cost = cost;
        this.rating = rating;
        this.distance = distance;
        this.freqByType = freqByType;
        this.freqByCost = freqByCost;
        this.freqByRating = freqByRating;
        this.freqByDistance = freqByDistance;
    }
}
