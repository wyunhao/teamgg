package com.example.vince.eatwise.Utility;

import java.util.Date;

/*
 * Created by wangjingtao on 11/14/17.
 * Last update on 11/15/17
 */

public class User {
    public String username;
    public Integer id;
    private String password;
    private History history;
    private Preference_Data preference;
    private Registration registerInfo;
    public RestaurantArray recList;

    public User(String username, String password, String email, String first, String last, Date dob){
        this.username = username;
        this.password = password;
        this.id = 1; //hash it or keep a global record, dc
        this.registerInfo = new Registration(email, first, last, dob);
        this.history = new History();
        this.preference = new Preference_Data(/*consider asking user for initial preference during sign up*/);
        this.recList = new RestaurantArray();
    }

    //account info. management methods
    public void change_username(String newUsername){this.username = newUsername;}
    public void change_password(String oldPassword, String newPassword){
        if(compare_password(oldPassword)){update_password(newPassword);}
    }
    public void change_email(String newEmail){update_email(newEmail);}
    public void change_first_name(String newFirst){update_first_name(newFirst);}
    public void change_last_name(String newLast){update_last_name(newLast);}
    public void change_birthdate(Date newDob){update_birthdate(newDob);}//ideally the string to date conversion should be handled during sanitizing input

    //grow history
    public void add_history_by_query(Query newQuery){update_history_by_query(newQuery);}
    public void add_history_by_viewed(Viewed newViewed){update_history_by_viewed(newViewed);}

    //provide access to preference date
    public Preference_Data show_preference(){return preference;};
    private void update_preference(){this.history.update_preference(this.preference);}

    //trivial access methods
    //private methods for passwords: check and update
    private Boolean compare_password(String inputPassword){return this.password.equals(inputPassword);}
    private void update_password(String newPassword){this.password = newPassword;}
    //private methods for registration info.
    private void update_email(String newEmail){this.registerInfo.set_email(newEmail);}
    private void update_first_name(String newFirst){this.registerInfo.set_first_name(newFirst);}
    private void update_last_name(String newLast){this.registerInfo.set_last_name(newLast);}
    private void update_birthdate(Date newDob){this.registerInfo.set_birthdate(newDob);}
    //private method for history
    private void update_history_by_query(Query newQuery){this.history.add_query(newQuery);}
    private void update_history_by_viewed(Viewed newViewed){this.history.add_viewed(newViewed);};

    //major calling methods
    //relate to search activity
    public RestaurantArray search_restaurants()
    {
        //TODO:may not be compatiable/necessary with current implementation for search
        return new RestaurantArray();
    }

    //relate to recommendation activity
    public void show_recommendation(){
        //this.update_preference(); //skipped for generating temp. recommendation page
        this.history.calculate_by_preference(this.preference, this.recList);
    }
}
