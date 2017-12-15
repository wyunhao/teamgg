package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class RestaurantArray {
    private Integer size;
    private List<Restaurant> dataMember;

    public RestaurantArray() {
        this.size = 0;
        this.dataMember = new ArrayList<>();
    }

    /**
     * Add a new restaurant initialized with features to the restaurant list
     * @param name String
     * @param location String
     * @param type String
     * @param cost Double
     * @param rating Rating
     * @param distance Double
     */
    public void addRestaurant(final String name, final String location, final String type, final Double cost,
                              final Rating rating, final Double distance){
        //notice that rating here should naturally be double, not quantized; here it is only for convenience
        this.dataMember.add(new Restaurant(name, location, type, cost, rating));
        //TODO: should not setDistance through Restaurant object
        //this.dataMember.get(this.size).setDistance(distance);
        this.size++;
    }

    //can't really think of any case this one will be used; commented out so that missing distance gives a compile error
    /*
    public void addRestaurant(String name, String location, String type, Double cost,
                               PreferenceData.Rating rating){
        this.dataMember.add(new Restaurant(name, location, type, cost, rating));
        this.dataMember.get(this.size).set_distance(location);
        this.size++;
    }
    */

    /**
     * Clear the restaurant list
     */
    public void resetRestaurants(){
        if (this.size != 0) {
            for (Restaurant data : this.dataMember) {
                this.dataMember.remove(data);
            }
        }

        this.size = 0;
    }
}

