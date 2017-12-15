package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreferenceData {//kept by user for recommendation
    private String type; //see enum CuisineType in QueryData
    private Double cost; //if ranged, consider enum, array, etc; else introduce two variables; current treat as +- $2 range
    private Rating rating; //name change; type changed: quantized to 0,1,2,3,4,5; no less than input by default
    private Double distance;
    private Double freqByType, freqByCost, freqByRating, freqByDistance; //added attr. for calculating recommendation; rather awkward

    /**
     * Constructor that takes four parameters
     * @param type String
     * @param cost Double
     * @param rating Rating
     * @param distance Double
     */
    public PreferenceData(final String type, final Double cost, final Rating rating, final Double distance) {
        this.type = type;
        this.cost = cost;
        this.rating = rating;
        this.distance = distance;
        this.freqByType = this.freqByCost = this.freqByRating = this.freqByDistance = 0.0;
    }

    /**
     * Constructor that takes three parameters
     * @param type String
     * @param cost Double
     * @param rating Rating
     */
    public PreferenceData(final String type, final Double cost, final Rating rating) {
        this(type, cost, rating, 0.0);
    }

    /**
     * Constructor that takes two parameters
     * @param type String
     * @param cost Double
     */
    public PreferenceData(final String type, final Double cost) {
        this(type, cost, Rating.ZERO, 0.0);
    }

    /**
     * Constructor that takes one parameter
     * @param type String
     */
    public PreferenceData(final String type){
        this(type, 0.0, Rating.ZERO, 0.0);
    }

    /**
     * Default constructor
     */
    public PreferenceData() {
        this("", 0.0, Rating.ZERO, 0.0); //the default restaurant type can either be null or empty
    }

    /**
     * Setter function to set the preference
     * @param type String
     * @param cost Double
     * @param rating Rating
     * @param distance Double
     * @param freqByType Double
     * @param freqByCost Double
     * @param freqByRating Double
     * @param freqByDistance Double
     */

    // update preference when required and new history is generated
    // This method should be deprecated
    /**
     * TODO: need further consideration on this update, we should insert the lastest 100 query / viewed into DB
     * and then each time we need to show the recommendation, retrieve all 100 data from DB and run the algorithm
     * Therefore, we actually do not need to maintain a so-called "PreferenceData" at all
     */
    //
    public void updatePreference(final String type, final Double cost, final Rating rating, final Double distance,
                                  Double freqByType, Double freqByCost, final Double freqByRating, final Double freqByDistance){
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
