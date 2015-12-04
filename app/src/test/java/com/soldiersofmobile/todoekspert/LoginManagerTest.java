package com.soldiersofmobile.todoekspert;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoginManagerTest {

    @Test
    public void shouldInjectProperties() throws Exception {

        //given
        SharedPreferences preferences = mock(SharedPreferences.class);
        TodoApi todoApi = mock(TodoApi.class);
        String expected = "test";
        when(preferences.getString(eq(LoginManager.TOKEN), isNull(String.class))).thenReturn(expected);

        //when
        LoginManager loginManager = new LoginManager(preferences, todoApi);

        //then
        assertEquals(expected, loginManager.getToken());

    }

    @Test
    public void shouldBeLogged() throws Exception {
        //given
        SharedPreferences preferences = mock(SharedPreferences.class);

        TodoApi todoApi = mock(TodoApi.class);

        //when
        LoginManager loginManager = new LoginManager(preferences, todoApi);

        //then
        assertTrue(loginManager.hasToLogin());



    }
}