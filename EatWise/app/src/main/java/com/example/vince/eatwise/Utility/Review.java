package com.example.vince.eatwise.Utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    enum Source{YELP, FOURSQUARE, TRIPADVISOR}

    private Source source;
    private Double ranking;
    private String featuredComments;
}
