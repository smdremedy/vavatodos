package com.soldiersofmobile.todoekspert.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.soldiersofmobile.todoekspert.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void shouldClick() throws Exception {
        //given
        //when
        //then

        onView(withId(R.id.usernameEditText))
                .perform(typeText("tet"));
        onView(withId(R.id.passwordEditText))
                .perform(typeText("tet"));
        onView(withId(R.id.loginButton))
                .perform(click());


    }
}