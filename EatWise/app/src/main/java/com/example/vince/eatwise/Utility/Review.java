package com.example.vince.eatwise.Utility;

public class Review {
    enum Source{YELP, FOURSQUARE, TRIPADVISOR}

    private Source source;
    private Double ranking;
    private String featuredComments;
}
