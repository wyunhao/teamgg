package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Viewed {
    private Date date;
    private Restaurant restaurant;
    private Boolean visit;

    public Viewed(final Restaurant restaurant){
        this.date = new Date(); //current date, can be formatted per need
        this.restaurant = restaurant;
        this.visit = false;
    }
}
