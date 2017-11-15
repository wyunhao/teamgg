package com.example.vince.eatwise;

import java.util.Date;

/**
 * Created by wangjingtao on 11/14/17.
 */

public class History {
    private class Viewed{
        public Date date;
        public Restaurant restaurant;
        public Boolean visit;
    }
    public Query past_query;
    public Viewed past_viewing;
    public RestaurantArray calculate_preference(){
        //TODO
        return new RestaurantArray();
    }
}
