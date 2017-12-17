package com.example.vince.eatwise.Constants;

/**
 * Created by 84399_000 on 12/14/2017.
 */

public enum Price {
    INEXPENSIVE(10.0),
    MODERATE(30.0),
    PRICY(60.0),
    HIGHENG(61.0);
    //see yelp for the value range

    private Double context;

    Price(Double context) {
        this.context = context;
    }

    public Double toDouble() {
        return context;
    }
}
