package com.example.vince.eatwise.Utility;

/**
 * Created by wangjingtao on 11/14/17.
 */

public class User {
    public String name;
    public Integer id;
    protected String password;
    private History history;
    private Preference_Data preference;
    private Registration registerInfo;
    public RestaurantArray search_restaurants()
    {
        //TODO
        return new RestaurantArray();
    }
}
