package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* TODO:merge with QueryFilter
* */

@Setter
@Getter
@NoArgsConstructor
public class Query extends PreferenceData {//passed by user into search
    //for enum from preference, consider asking "search restaurant with over ? average ranking score"
    private String restaurantName;
    private Date queryTime;

    /**
     * Constructor that takes five parameters
     * @param type
     * @param cost
     * @param rating
     * @param distance
     * @param restaurantName
     */
    public Query(final String type, final Double cost, final Rating rating, final Double distance, final String restaurantName){
        super(type, cost, rating, distance);
        this.restaurantName = restaurantName;
        this.queryTime = new Date();
    }

    /**
     * Contructor that takes four parameters
     * @param type
     * @param cost
     * @param rating
     * @param distance
     */
    public Query(final String type, final Double cost, final Rating rating, final Double distance){
        this(type, cost, rating, distance, "");
    }

    /**
     * Constructor that takes three parameters
     * @param type
     * @param cost
     * @param rating
     */
    public Query(final String type, final Double cost, final Rating rating){
        super(type, cost, rating);
        this.restaurantName = "";
        this.queryTime = new Date();
    }

    /**
     * Constructor that takes two parameters
     * @param type
     * @param cost
     */
    public Query(final String type, final Double cost){
        super(type, cost);
        this.restaurantName = "";
        this.queryTime = new Date();
    }

    /**
     * Constructor that takes one parameter
     * @param type
     */
    public Query(final String type){
        super(type);
        this.restaurantName = "";
        this.queryTime = new Date();
    }
}
