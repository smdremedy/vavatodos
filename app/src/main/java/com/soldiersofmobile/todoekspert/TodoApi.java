package com.soldiersofmobile.todoekspert;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Path;
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

    @Headers({
            "X-Parse-Application-Id: EhZ7ps1nVRbYCz4d1IkLlOLUAMkuzaA6NGS89hDX",
            "X-Parse-REST-API-Key: 0cc1iqhHHEg3j8do8b6DNkjC0nmnNNMKVJ11blov",
            "X-Parse-Revocable-Session: 1"
    })
    @GET("/classes/Todo")
    TodosResponse getTodos(@Header("X-Parse-Session-Token") String token);

    @Headers({
            "X-Parse-Application-Id: EhZ7ps1nVRbYCz4d1IkLlOLUAMkuzaA6NGS89hDX",
            "X-Parse-REST-API-Key: 0cc1iqhHHEg3j8do8b6DNkjC0nmnNNMKVJ11blov",
            "X-Parse-Revocable-Session: 1"
    })
    @GET("/classes/Todo")
    void getTodos(@Header("X-Parse-Session-Token") String token,
                           Callback<TodosResponse> callback);

}
