package com.example.vince.eatwise.Utility;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginInfo implements Serializable {
    private String email;
    private String name;
}
