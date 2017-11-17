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
                                        restaurant.feature.getType(), restaurant.feature.getCost(),restaurant.feature.getRating());
        this.restaurant.set_distance(restaurant.feature.getDistance());
        this.visit = false;
    }

    public void set_visit(){this.visit = true;}
}
