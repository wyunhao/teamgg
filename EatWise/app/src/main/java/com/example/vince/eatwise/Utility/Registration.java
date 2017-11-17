package com.example.vince.eatwise.Utility;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Registration {
    private String email;
    private String first;
    private String last;
    private Date dob;

//TODO: handle the string conversion

}

