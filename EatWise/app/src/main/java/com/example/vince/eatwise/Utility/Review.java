package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Source;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private Source source;
    private Double ranking;
    private String featuredComments;
}
