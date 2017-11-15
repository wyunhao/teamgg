package com.example.vince.eatwise;

/**
 * Created by wangjingtao on 11/14/17.
 */
enum Source{YELP, FOURSQUARE, TRIPADVISOR}
public class Review {
    public Source source;
    public Double ranking;
    public String featuredComments;
}
