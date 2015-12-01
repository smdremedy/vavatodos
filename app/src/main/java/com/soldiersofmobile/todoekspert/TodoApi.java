package com.soldiersofmobile.todoekspert;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by Sylwester on 2015-12-01.
 */
public interface TodoApi {

    @Headers({
            "X-Parse-Application-Id: EhZ7ps1nVRbYCz4d1IkLlOLUAMkuzaA6NGS89hDX",
            "X-Parse-REST-API-Key: 0cc1iqhHHEg3j8do8b6DNkjC0nmnNNMKVJ11blov",
            "X-Parse-Revocable-Session: 1"
    })
    @GET("/login")
    LoginResponse login(@Query("username") String username,
                        @Query("password") String password);

}
