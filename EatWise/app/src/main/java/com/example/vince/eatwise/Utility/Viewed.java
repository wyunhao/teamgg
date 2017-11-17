package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.Getter;

@Getter
public class Viewed {
    public Date date;
    public Restaurant restaurant;
    public Boolean visit;

    public Viewed(final Restaurant restaurant){
        this.date = new Date(); //current date, can be formatted per need
        this.restaurant = restaurant;
        this.restaurant.setDistance(restaurant.getPreferenceFeature().getDistance());
        this.visit = false;
    }

    public void set_visit(){this.visit = true;}
}
