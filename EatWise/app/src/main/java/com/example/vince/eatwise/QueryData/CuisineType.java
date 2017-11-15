package com.example.vince.eatwise.QueryData;


public enum CuisineType {
    AMERICAN("American"),
    BREAKFAST("Breakfast & Brunch"),
    BURGERS("Burgers"),
    CHINESE("Chinese"),
    INDIAN("Indian"),
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    MEXICAN("Mexican"),
    PIZZA("Pizza"),
    SANDWICHES("Sandwiches"),
    SEAFOOD("Seafood"),
    STEAKHOUSES("Steakhouse"),
    SUSHI("Sushi"),
    THAI("Thai"),
    VIETNAMESE("Vietnamese");

    private String context;

    CuisineType(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return context;
    }
}
