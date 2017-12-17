package com.example.vince.eatwise.Constants;

/**
 * Created by 84399_000 on 12/14/2017.
 */

public enum Distance {
    WALKING(1000.0), //in [m], read as Walking distance is defined by 0.0 to 2.0 km
    BIKE(5000.0),
    CAR(20000.0),
    LONGTRIP(40000.0);

    private Double context;

    Distance(Double context) {
        this.context = context;
    }

    public Double toDouble() {
        return context;
    }
}
