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
    public void add_query(Query newQuery){
        past_query.set(queryLength%historyLength,
                    new Query(newQuery.type, newQuery.cost, newQuery.rating, newQuery.distance, newQuery.restaurant_name)); //TODO: find a way to acconnt for direct search by name
        queryLength++;
    }
    public void add_viewed(Viewed newViewed){
        past_viewing.set(viewLength%historyLength, new Viewed(newViewed.restaurant));
        viewLength++;
    };

    //calculate preference
    public void update_preference(Preference_Data current_preference){

        Integer q_length = (queryLength > historyLength) ? historyLength : queryLength;
        Integer v_length = (viewLength > historyLength) ? historyLength : viewLength;

        //for query, get key frequency
        Double num_type, num_cost, num_rating, num_distance; num_type = num_cost = num_rating = num_distance = 0.0;
        Double fre_type, fre_cost, fre_rating, fre_distance;
        for(Integer i = 0; i <q_length; i++){
            if(!past_query.get(i).type.equals("")) num_type++;
            if(past_query.get(i).cost != 0.0) num_cost++;
            if(past_query.get(i).rating != Query.Rating.ZERO) num_rating++;
            if(past_query.get(i).distance != 0.0) num_distance++;
        }
        fre_type = num_type / q_length; fre_cost = num_cost / q_length; fre_rating = num_rating / q_length; fre_distance = num_distance / q_length;

        //for viewed, get most viewed type, cost, rating, distance
        //TODO:quantize each feature and count their appearance by range

        current_preference.update_preference("Mexican", 5.0, Preference_Data.Rating.FOUR, 1.0, fre_type, fre_cost, fre_rating, fre_distance);
    }

    //get recommended restaurants by preference from historically viewed
    public void calculate_by_preference(Preference_Data currentPreference, RestaurantArray userRecList){//avoid return/copying RestaurantArray
        //!this method may be discarded entirely: by our definition of recommendation(as opposed to favorites), it performs search using preference data
        userRecList.reset();

        //calculate recommendation score for each restaurant in historical view
        List<Double> topTenId = new ArrayList<Double>();
        List<Double> recScore = new ArrayList<Double>();

        for(Integer i = 0; i < ((viewLength < historyLength) ? viewLength : historyLength); i++){
            Double score = 0.0;
            Double weight = this.past_viewing.get(i).visit ? weightVisit : weightView;//TODO:consider how/when to use set_visit()

            if(this.past_viewing.get(i).restaurant.feature.type.equals(currentPreference.type)){
                score += weight * currentPreference.freqByType;
            }
            if(this.past_viewing.get(i).restaurant.feature.cost.equals(currentPreference.cost)){
                score += weight * currentPreference.freqByCost;
            }
            if(this.past_viewing.get(i).restaurant.feature.rating.equals(currentPreference.rating)){
                score += weight * currentPreference.freqByRating;
            }
            if(this.past_viewing.get(i).restaurant.feature.distance.equals(currentPreference.distance)){
                score += weight * currentPreference.freqByDistance;
            }

            recScore.set(i, score);
        }

        //TODO:pick num_recommend highest score from recScore and add corresponding past_viewing index to topTenId; add restaurants from viewed to userRecList by topTenId

        /*general mock data to display*/
        userRecList.add_restaurant("R1", "pos(1,1)", "type1", 5.0, Preference_Data.Rating.ONE, 1.0);
        userRecList.add_restaurant("R2", "pos(2,2)", "type2", 6.0, Preference_Data.Rating.TWO, 2.0);
        userRecList.add_restaurant("R3", "pos(3,3)", "type3", 7.0, Preference_Data.Rating.THREE, 3.0);
        userRecList.add_restaurant("R4", "pos(4,4)", "type4", 8.0, Preference_Data.Rating.FOUR, 4.0);
        userRecList.add_restaurant("R5", "pos(4,4)", "type5", 9.0, Preference_Data.Rating.FIVE, 5.0);
    }

}
