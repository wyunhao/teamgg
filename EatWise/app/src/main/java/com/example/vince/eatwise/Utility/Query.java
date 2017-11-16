package com.example.vince.eatwise.Utility;

import java.util.Date;

/*
* Last Modified by on 2017-11-15
* TODO:merge with QueryFilter
* */

public class Query extends Preference_Data{//passed by user into search
    //for enum from preference, consider asking "search restaurant with over ? average ranking score"
    public String restaurant_name;
    public Date query_time;

    public Query(String type, Double cost, Rating rating, Double distance, String restaurant_name){
        super(type, cost, rating, distance);
        this.restaurant_name = restaurant_name;
        this.query_time = new Date();
    }
    public Query(String type, Double cost, Rating rating, Double distance){
        this(type, cost, rating, distance, "");
    }
    public Query(String type, Double cost, Rating rating){
        super(type, cost, rating);
        this.restaurant_name = "";
        this.query_time = new Date();
    }
    public Query(String type, Double cost){
        super(type, cost);
        this.restaurant_name = "";
        this.query_time = new Date();
    }
    public Query(String type){
        super(type);
        this.restaurant_name = "";
        this.query_time = new Date();
    }
    public Query(){//ideally this empty search case should be avoided by checking input
        super();
        this.restaurant_name = "";
        this.query_time = new Date();
    }

    public API_Input sanitizeInput(){
        //TODO: format user input for Yelp API
        return new API_Input();
    }
}
