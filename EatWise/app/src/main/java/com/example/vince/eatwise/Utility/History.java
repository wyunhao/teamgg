package com.example.vince.eatwise.Utility;

import java.util.ArrayList;
import java.util.List;

/*
* Last update on 2017-11-16
*/

public class History {
    private static final Integer historyLength = 100;
    private static final Integer numRecommended = 10;

    private static final Double weightView = 1.0;
    private static final Double weightVisit = 20.0;

    private Integer queryLength = 0;
    private Integer viewLength = 0;

    public List<Query> past_query;
    public List<Viewed> past_viewing;

    public History(){
        this.past_query = new ArrayList<Query>();
        this.past_viewing = new ArrayList<Viewed>();
    }

    //grow history

    /**
     * Adding a new query to the history
     * @param newQuery The query to be added
     */
    public void addQuery(Query newQuery){
        past_query.set(queryLength%historyLength,
                    new Query(newQuery.getType(), newQuery.getCost(), newQuery.getRating(), newQuery.getDistance(), newQuery.restaurant_name)); //TODO: find a way to acconnt for direct search by name
        queryLength++;
    }

    /**
     * Adding a restaurant to the viewed list if it is viewed by the user
     * @param newViewed The viewed restaurant tio be added
     */
    public void addViewed(Viewed newViewed){
        past_viewing.set(viewLength%historyLength, new Viewed(newViewed.getRestaurant()));
        viewLength++;
    };

    /**
     * Modify the calculated preference by frequency of the keys of user's query
     * Store the preference of the user
     * Still in progress
     * @param current_preference User class would pass in its own preference to get an updated preference
     */
    //calculate preference
    public void updatePreference(Preference_Data current_preference){

        Integer q_length = (queryLength > historyLength) ? historyLength : queryLength;
        Integer v_length = (viewLength > historyLength) ? historyLength : viewLength;

        //for query, get key frequency
        Double num_type, num_cost, num_rating, num_distance; num_type = num_cost = num_rating = num_distance = 0.0;
        Double fre_type, fre_cost, fre_rating, fre_distance;
        for(Integer i = 0; i <q_length; i++){
            final Query query = past_query.get(i);
            if(!query.getType().equals("")) num_type++;
            if(query.getCost() != 0.0) num_cost++;
            if(query.getRating() != Rating.ZERO) num_rating++;
            if(query.getDistance() != 0.0) num_distance++;
        }
        fre_type = num_type / q_length; fre_cost = num_cost / q_length; fre_rating = num_rating / q_length; fre_distance = num_distance / q_length;

        //for viewed, get most viewed type, cost, rating, distance
        //TODO:quantize each feature and count their appearance by range

        current_preference.update_preference("Mexican", 5.0, Rating.FOUR, 1.0, fre_type, fre_cost, fre_rating, fre_distance);
    }

    /**
     * Assign weight to viewed and visited restaurants from history to calculate recommendation
     * Still in progress
     * @param currentPreference  User class would pass in its own preference to get an updated preference
     * @param userRecList   User class would pass in its stored list of recommended restaurants
     */
    //get recommended restaurants by preference from historically viewed
    public void calculateByPreference(Preference_Data currentPreference, RestaurantArray userRecList){//avoid return/copying RestaurantArray
        //!this method may be discarded entirely: by our definition of recommendation(as opposed to favorites), it performs search using preference data
        userRecList.reset();

        //calculate recommendation score for each restaurant in historical view
        List<Double> topTenId = new ArrayList<Double>();
        List<Double> recScore = new ArrayList<Double>();

        for(Integer i = 0; i < ((viewLength < historyLength) ? viewLength : historyLength); i++){
            Double score = 0.0;
            Double weight = this.past_viewing.get(i).getVisit() ? weightVisit : weightView;//TODO:consider how/when to use set_visit()

            if(this.past_viewing.get(i).getRestaurant().feature.getType().equals(currentPreference.getType())){
                score += weight * currentPreference.getFreqByType();
            }
            if(this.past_viewing.get(i).getRestaurant().feature.getCost().equals(currentPreference.getCost())){
                score += weight * currentPreference.getFreqByCost();
            }
            if(this.past_viewing.get(i).getRestaurant().feature.getRating().equals(currentPreference.getRating())){
                score += weight * currentPreference.getFreqByRating();
            }
            if(this.past_viewing.get(i).getRestaurant().feature.getDistance().equals(currentPreference.getDistance())){
                score += weight * currentPreference.getFreqByDistance();
            }

            recScore.set(i, score);
        }

        //TODO:pick num_recommend highest score from recScore and add corresponding past_viewing index to topTenId; add restaurants from viewed to userRecList by topTenId

        /*general mock data to display*/
        userRecList.addRestaurant("R1", "pos(1,1)", "type1", 5.0, Rating.ONE, 1.0);
        userRecList.addRestaurant("R2", "pos(2,2)", "type2", 6.0, Rating.TWO, 2.0);
        userRecList.addRestaurant("R3", "pos(3,3)", "type3", 7.0, Rating.THREE, 3.0);
        userRecList.addRestaurant("R4", "pos(4,4)", "type4", 8.0, Rating.FOUR, 4.0);
        userRecList.addRestaurant("R5", "pos(4,4)", "type5", 9.0, Rating.FIVE, 5.0);
    }

}
