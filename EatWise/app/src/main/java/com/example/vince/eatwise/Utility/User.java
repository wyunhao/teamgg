package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private Integer id;
    private String password;
    private History history;
    private PreferenceData preference;
    private Registration registerInfo;
    private RestaurantArray restList;

    public User(final String username, final String password, final String email, final String first, final String last, final Date dob){
        this.username = username;
        this.password = password;
        this.id = 1; //hash it or keep a global record, dc
        this.registerInfo = new Registration(email, first, last, dob);
        this.history = new History();
        this.preference = new PreferenceData(/*consider asking user for initial preference during sign up*/);
        this.restList = new RestaurantArray();
    }

    //account info. management methods

    public void updateUsername(final String username) {
        this.username = username;
    }

    public void updatePassword(final String oldPassword, final String newPassword){
        //call equals method on this.password to avoid nullptr exception
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
        }
    }

    public void updateEmail(final String newEmail) {
        this.registerInfo.setEmail(newEmail);
    }

    public void updateFirstName(final String newFirst) {
        this.registerInfo.setFirst(newFirst);
    }

    public void updateLastName(final String newLast) {
        this.registerInfo.setLast(newLast);
    }

    public void updateBirthDate(final Date newDob) {
        //TODO: handle the string conversion for dob
        this.registerInfo.setDob(newDob);
    }

    //grow history
    public void addHistoryByQuery(final Query newQuery) {
        this.history.addQuery(newQuery);
    }

    public void addHistoryByViewed(final Viewed newViewed) {
        this.history.addViewed(newViewed);
    }

    public void updatePreference() {
        this.history.updatePreference(this.preference);
    }


    //TODO: need to add more private method to help those above public functions interacting with DB / other classes

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
    //TODO: this recommendation generation should not be inside User class, move to Recommendation Activity
    public void showRecommendation(){
        //this.updatePreference(); //skipped for generating temp. recommendation page
        this.history.calculateByPreference(this.preference, this.restList);
    }
}
