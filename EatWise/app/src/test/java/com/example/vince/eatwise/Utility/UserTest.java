package com.example.vince.eatwise.Utility;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

public class UserTest {
    private static final String USERNAME = "username";
    private static final Integer ID = new Integer("123");
    private static final String PASSWORD = "pwd";
    private static final String EMAIL = "email";
    private static final String FIRSTNAME = "first";
    private static final String LASTNAME = "last";
    private static final Date DATE = new Date();

    private User user;

    @Before
    public void setUp() {
        user = new User(USERNAME, PASSWORD, EMAIL, FIRSTNAME, LASTNAME, DATE);
    }

    @Test
    public void validateConstructor() {
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());

        final Registration reg = user.getRegisterInfo();
        assertEquals(EMAIL, reg.getEmail());
        assertEquals(DATE, reg.getDob());
        assertEquals(FIRSTNAME, reg.getFirst());
        assertEquals(LASTNAME, reg.getLast());
    }

    @Test
    public void changeUsernameSucceed() {
        user.change_username("changed");
        assertEquals("changed", user.getUsername());

    }

    @Test
    public void changePasswordSucceed() {
        user.change_password("pwd", "567");
        assertEquals("567", user.getPassword());
    }

    @Test
    public void changePasswordFailed() {
        user.change_password("wrong", "123");
        assertEquals("pwd", user.getPassword());
    }

}