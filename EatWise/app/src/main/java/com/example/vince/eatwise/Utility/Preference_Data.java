package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/*
 * Last update on 2017-11-15
 */


@Getter
@Setter
public class Preference_Data {//kept by user for recommendation
    private String type; //see enum CuisineType in QueryData
    private Double cost; //if ranged, consider enum, array, etc; else introduce two variables; current treat as +- $2 range
    private Rating rating; //name change; type changed: quantized to 0,1,2,3,4,5; no less than input by default
    private Double distance;
    private Double freqByType, freqByCost, freqByRating, freqByDistance; //added attr. for calculating recommendation; rather awkward

    /**
     * Constructor that takes four parameters
     * @param type
     * @param cost
     * @param rating
     * @param distance
     */
    public Preference_Data(String type, Double cost, Rating rating, Double distance){
        this.type = type;
        this.cost = cost;
        this.rating = rating;
        this.distance = distance;
        this.freqByType = this.freqByCost = this.freqByRating = this.freqByDistance = 0.0;
    }

    /**
     * Constructor that takes three parameters
     * @param type
     * @param cost
     * @param rating
     */
    public Preference_Data(String type, Double cost, Rating rating){
        this(type, cost, rating, 0.0);
    }

    /**
     * Constructor that takes two parameters
     * @param type
     * @param cost
     */
    public Preference_Data(String type, Double cost){
        this(type, cost, Rating.ZERO, 0.0);
    }

    /**
     * Constructor that takes one parameter
     * @param type
     */
    public Preference_Data(String type){
        this(type, 0.0, Rating.ZERO, 0.0);
    }

    /**
     * Default constructor
     */
    public Preference_Data(){
        this("", 0.0, Rating.ZERO, 0.0); //the default restaurant type can either be null or empty
    }

    /**
     * Setter function to set the preference
     * @param type
     * @param cost
     * @param rating
     * @param distance
     * @param freqByType
     * @param freqByCost
     * @param freqByRating
     * @param freqByDistance
     */

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
