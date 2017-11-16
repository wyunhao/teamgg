package com.example.vince.eatwise.Utility;

import java.util.Date;

/*
* Last Modified 2017-11-15
* */

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

    //retriving registration info
    public String get_email(){return this.email;}
    public String get_first_name(){return this.first;}
    public String get_last_name(){return this.last;}
    public Date get_birthDate(){return this.dob;}

    //update registration info
    public void set_email(String newEmail){this.email = newEmail;}
    public void set_first_name(String newFirst){this.first = newFirst;}
    public void set_last_name(String newLast){this.last = newLast;}
    public void set_birthdate(Date newDob){this.dob = newDob;} //ideally the string to date conversion should be handled during sanitizing input
}

