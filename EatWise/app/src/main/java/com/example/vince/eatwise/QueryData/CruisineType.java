package com.example.vince.eatwise.QueryData;


public enum CruisineType {
    AMERICAN("American"),
    BREAKFAST("Breakfast & Brunch"),
    BURGERS("Bugers"),
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

    CruisineType(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return context;
    }
}
