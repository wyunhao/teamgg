package com.example.vince.eatwise.QueryData;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QueryFilter implements Serializable{
    private String location;
    private CruisineType category;
    private Long distance;
    private Double price;
    private String[] keyword;
}
