package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.Getter;

@Getter
public class User {
    private String username;
    private Integer id;
    private String password;
    private History history;
    private PreferenceData preference;
    private Registration registerInfo;
    private RestaurantArray recList;

    public User(String username, String password, String email, String first, String last, Date dob){
        this.username = username;
        this.password = password;
        this.id = 1; //hash it or keep a global record, dc
        this.registerInfo = new Registration(email, first, last, dob);
        this.history = new History();
        this.preference = new PreferenceData(/*consider asking user for initial preference during sign up*/);
        this.recList = new RestaurantArray();
    }

    //account info. management methods
    public void changeUsername(String newUsername){this.username = newUsername;}

    public void changePassword(String oldPassword, String newPassword){
        if(comparePassword(oldPassword)){updatePassword(newPassword);}
    }
    public void changeEmail(String newEmail) {
        updateEmail(newEmail);
    }

    public void changeFirstName(String newFirst){
        updateFirstName(newFirst);
    }

    public void changeLastName(String newLast){updateLastName(newLast);}
    public void changeBirthDate(Date newDob){updateBirthDate(newDob);}//ideally the string to date conversion should be handled during sanitizing input

    //grow history
    public void addHistoryByQuery(Query newQuery){updateHistoryByQuery(newQuery);}
    public void addHistoryByViewed(Viewed newViewed){updateHistoryByViewed(newViewed);}

    //provide access to preference date
    public PreferenceData showPreference(){return preference;};
    private void updatePreference(){this.history.updatePreference(this.preference);}

    //trivial access methods
    //private methods for passwords: check and update
    private Boolean comparePassword(String inputPassword){return this.password.equals(inputPassword);}
    private void updatePassword(String newPassword){this.password = newPassword;}
    //private methods for registration info.
    private void updateEmail(String newEmail){this.registerInfo.setEmail(newEmail);}
    private void updateFirstName(String newFirst){this.registerInfo.setFirst(newFirst);}
    private void updateLastName(String newLast){this.registerInfo.setLast(newLast);}
    private void updateBirthDate(Date newDob){this.registerInfo.setDob(newDob);}
    //private method for history
    private void updateHistoryByQuery(Query newQuery){this.history.addQuery(newQuery);}
    private void updateHistoryByViewed(Viewed newViewed){this.history.addViewed(newViewed);};

    //major calling methods
    //relate to search activity

    /**
     * Search for restaurants from the restaurant list that match the preferences
     * @return restaurants that match the preferences
     */
    public RestaurantArray searchRestaurants()
    {
        //TODO:may not be compatiable/necessary with current implementation for search
        return new RestaurantArray();
    }

    /**
     * Update preferences and recommendations
     */
    //relate to recommendation activity
    public void showRecommendation(){
        //this.updatePreference(); //skipped for generating temp. recommendation page
        this.history.calculateByPreference(this.preference, this.recList);
    }
}
