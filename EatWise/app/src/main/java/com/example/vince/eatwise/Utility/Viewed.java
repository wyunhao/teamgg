package com.example.vince.eatwise.Utility;

import java.util.Date;

/*
* Last update on 2017-11-15
* */

public class Viewed {
    public Date date;
    public Restaurant restaurant;
    public Boolean visit;

    public Viewed(Restaurant restaurant){
        this.date = new Date(); //current date, can be formatted per need
        this.restaurant = new Restaurant(restaurant.name, restaurant.location,
                                        restaurant.feature.type, restaurant.feature.cost,restaurant.feature.rating);
        this.restaurant.set_distance(restaurant.feature.distance);
        this.visit = false;
    }

    public void set_visit(){this.visit = true;}
}
