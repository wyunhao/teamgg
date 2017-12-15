package com.example.vince.eatwise.Utility;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

/**
 * Stores information of a restaurant from Json Metadata
 * Passed between result fragment and DetailedActivity
 */

@Builder
@Getter
public class RestaurantInfo implements Serializable {
    private String name;
    private String addr;
    private String phone;
    private String picture;
    private String rating;
    private String foursquareRating;
    private Double latitude;
    private Double longitude;
    private String tripadvisorRating;
    private String avgRating;
}
