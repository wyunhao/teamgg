package com.example.vince.eatwise.QueryData;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QueryFilter implements Serializable {
    private String location;
    private String category;
    private Integer distance;
    private Double price;
    private String keyword;
}
