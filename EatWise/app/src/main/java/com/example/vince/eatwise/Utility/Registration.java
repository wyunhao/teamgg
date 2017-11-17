package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/*
* Last Modified 2017-11-15
* */

@Getter
@Setter
public class Registration {
    private String email;
    private String first;
    private String last;
    private Date dob;

    public Registration(String email, String first, String last, Date dob){
    //assuming input is sanitized
        this.email =email;
        this.first = first;
        this.last = last;
        this.dob = dob;
    }
//TODO: handle the string conversion

}

