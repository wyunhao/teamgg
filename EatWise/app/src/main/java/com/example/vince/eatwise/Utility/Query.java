package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class Query extends PreferenceData {//passed by user into search
    //for enum from preference, consider asking "search restaurant with over ? average ranking score"
    private String restaurantName;
    private Date queryTime;

    /**
     * Constructor for generic query initialization
     */
    public Query(){
        super();
        this.restaurantName = "any";
        this.queryTime = new Date();
    }
}
