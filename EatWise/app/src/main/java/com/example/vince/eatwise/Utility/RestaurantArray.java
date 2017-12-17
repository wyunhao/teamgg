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
     * @param type
     * @param cost
     * @param rstInfo
     */
    public void addRestaurant(RestaurantInfo rstInfo, String type, Double cost){
        //notice that rating here should naturally be double, not quantized; here it is only for convenience
        this.dataMember.add(new Restaurant(rstInfo,type,cost));
        this.size++;
    }

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

