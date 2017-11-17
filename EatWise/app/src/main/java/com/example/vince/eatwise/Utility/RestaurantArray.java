package com.example.vince.eatwise.Utility;

import java.util.ArrayList;
import java.util.List;

/*
* Last update on 2017-11-15
* */

public class RestaurantArray {
    public Integer size;
    public List<Restaurant> data_member;

    public RestaurantArray(){
        this.data_member = new ArrayList<Restaurant>();
        this.size = 0;
    }

    /**
     * Add a new restaurant initialized with features to the restaurant list
     * @param name
     * @param location
     * @param type
     * @param cost
     * @param rating
     * @param distance
     */
    public void add_restaurant(String name, String location, String type, Double cost,
                               Preference_Data.Rating rating, Double distance){//notice that rating here should naturally be double, not quantized; here it is only for convenience
        this.data_member.add(new Restaurant(name, location, type, cost, rating));
        this.data_member.get(this.size).set_distance(distance);
        this.size++;
    }
    /*
    public void add_restaurant(String name, String location, String type, Double cost,
                               Preference_Data.Rating rating){
        this.data_member.add(new Restaurant(name, location, type, cost, rating));
        this.data_member.get(this.size).set_distance(location); //can't really think of any case this one will be used; commented out so that missing distance gives a compile error
        this.size++;
    }
    */

    /**
     * Clear the restaurant list
     */
    public void reset(){
        for(Integer i = this.size-1; i >= 0; i--){
            this.data_member.remove(i);
        }
        this.size = 0;
    }
}

