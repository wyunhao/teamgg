package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import static com.example.vince.eatwise.Constants.Constant.HISTORY_LENGTH;
import static com.example.vince.eatwise.Constants.Constant.VIEW_WEIGHT;
import static com.example.vince.eatwise.Constants.Constant.VISIT_WEIGHT;

@Getter
@Setter
public class History {
    private Integer queryLength;
    private Integer viewLength;

    private List<Query> pastQuery;
    private List<Viewed> pastViewing;

    public History() {
        this.queryLength = 0;
        this.viewLength = 0;
        this.pastQuery = new ArrayList<>();
        this.pastViewing = new ArrayList<>();
    }

    /**
     * Adding a new query to the history
     * @param newQuery The query to be added
     */
    public void addQuery(final Query newQuery){
        if(queryLength < HISTORY_LENGTH){
            pastQuery.add(newQuery);
        }
        else {
            pastQuery.set(queryLength % HISTORY_LENGTH, newQuery);
            //TODO: find a way to acconnt for direct search by name
        }

        queryLength++;
    }

    /**
     * Adding a restaurant to the viewed list if it is viewed by the user
     * @param newViewed The viewed restaurant tio be added
     */
    public void addViewed(final Viewed newViewed){
        if (viewLength < HISTORY_LENGTH){
            pastViewing.add(newViewed);
        }
        else {
            pastViewing.set(viewLength % HISTORY_LENGTH, newViewed);
        }

        viewLength++;
    };

    /**
     * Modify the calculated preference by frequency of the keys of user's query
     * Store the preference of the user
     * Still in progress
     * @param currentPreference User class would pass in its own preference to get an updated preference
     */
    //calculate preference
    public void updatePreference(final PreferenceData currentPreference){
        final Integer qLen = (queryLength > HISTORY_LENGTH) ? HISTORY_LENGTH : queryLength;
        final Integer vLen = (viewLength > HISTORY_LENGTH) ? HISTORY_LENGTH : viewLength;

        //for query, get key frequency
        Double numType, numCost, numRating, numDistance;
        numType = numCost = numRating = numDistance = 0.0;
        Double freType, freCost, freRating, freDistance;

        for(final Query query : pastQuery){
            if (!query.getType().equals("")) {
                numType++;
            }
            if (query.getCost() != 0.0) {
                numCost++;
            }
            if (!Rating.ZERO.equals(query.getRating())) {
                numRating++;
            }
            if (query.getDistance() != 0.0) {
                numDistance++;
            }
        }
        freType = numType / qLen;
        freCost = numCost / qLen;
        freRating = numRating / qLen;
        freDistance = numDistance / qLen;

        //for viewed, get most viewed type, cost, rating, distance
        //TODO:quantize each feature and count their appearance by range

        currentPreference.updatePreference("Mexican", 5.0, Rating.FOUR, 1.0, freType, freCost, freRating, freDistance);
    }

    /**
     * Assign weight to viewed and visited restaurants from history to calculate recommendation
     * Still in progress
     * @param currentPreference  User class would pass in its own preference to get an updated preference
     * @param userRestList   User class would pass in its stored list of recommended restaurants
     */
    //get recommended restaurants by preference from historically viewed
    public void calculateByPreference(final PreferenceData currentPreference, final RestaurantArray userRestList){//avoid return/copying RestaurantArray
        //!this method may be discarded entirely: by our definition of recommendation(as opposed to favorites), it performs search using preference data
        userRestList.resetRestaurants();

        //calculate recommendation score for each restaurant in historical view
        List<Double> topTenId = new ArrayList<>();
        List<Double> restScore = new ArrayList<>();

        for(Integer i = 0; i < ((viewLength < HISTORY_LENGTH) ? viewLength : HISTORY_LENGTH); i++){
            final Double weight = this.pastViewing.get(i).getVisit() ? VISIT_WEIGHT : VIEW_WEIGHT;//TODO:consider how/when to use set_visit()
            Double score = 0.0;

            final PreferenceData preferenceData = this.pastViewing.get(i).getRestaurant().getPreferenceFeature();
            if (preferenceData.getType().equals(currentPreference.getType())) {
                score += weight * currentPreference.getFreqByType();
            }
            if (preferenceData.getCost().equals(currentPreference.getCost())) {
                score += weight * currentPreference.getFreqByCost();
            }
            if (preferenceData.getRating().equals(currentPreference.getRating())) {
                score += weight * currentPreference.getFreqByRating();
            }
            if (preferenceData.getDistance().equals(currentPreference.getDistance())) {
                score += weight * currentPreference.getFreqByDistance();
            }
            restScore.set(i, score);
        }

        //TODO:pick num_recommend highest score from recScore and add corresponding past_viewing index to topTenId; add restaurants from viewed to userRecList by topTenId

        /*general mock data to display*/
        userRestList.addRestaurant("R1", "pos(1,1)", "type1", 5.0, Rating.ONE, 1.0);
        userRestList.addRestaurant("R2", "pos(2,2)", "type2", 6.0, Rating.TWO, 2.0);
        userRestList.addRestaurant("R3", "pos(3,3)", "type3", 7.0, Rating.THREE, 3.0);
        userRestList.addRestaurant("R4", "pos(4,4)", "type4", 8.0, Rating.FOUR, 4.0);
        userRestList.addRestaurant("R5", "pos(4,4)", "type5", 9.0, Rating.FIVE, 5.0);
    }

}
