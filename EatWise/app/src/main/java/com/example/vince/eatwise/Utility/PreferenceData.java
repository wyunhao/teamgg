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
    private Double rating; //name change; type changed: quantized to 0,1,2,3,4,5; no less than input by default
    private Double distance;
    private Double freqByType, freqByCost, freqByRating, freqByDistance; //added attr. for calculating recommendation; rather awkward

    /**
     * Constructor with 3 input, setting initial PreferenceData for new Restaurant object.
     * @param type type of the created restaurant entry
     * @param rating average rating
     * @param cost price level by numerical value
     */
    public PreferenceData(String type, Double cost, Double rating) {
        this.type = type;
        this.cost = cost;
        this.rating = rating;
        this.distance = 1000.0;
        this.freqByType = this.freqByCost = this.freqByRating = this.freqByDistance = 0.0;
    }

    /**
     * Constructor with no input, setting default initial PreferenceData for new User object.
     */
    public PreferenceData() {
        this.type = "food";
        this.cost = 10.0;
        this.rating = 3.0;
        this.distance = 1000.0;
        this.freqByType = this.freqByCost = this.freqByRating = this.freqByDistance = 0.0;
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

    // update preference when required and new history is generated
    // This method should be deprecated
    /**
     * TODO: need further consideration on this update, we should insert the lastest 100 query / viewed into DB
     * and then each time we need to show the recommendation, retrieve all 100 data from DB and run the algorithm
     * Therefore, we actually do not need to maintain a so-called "PreferenceData" at all
     */
    //
    public void updatePreference(final String type, final Double cost, final Double rating, final Double distance,
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
