package com.example.vince.eatwise.Constants;

public enum Rating {
    ZERO(0.0),
    ONE(1.0),
    TWO(2.0),
    THREE(3.0),
    FOUR(4.0),
    FIVE(5.0);

    private double value;

    Rating(double value) {
        this.value = value;
    }

    public double toDouble() {
        return value;
    }
}
