package com.soldiersofmobile.todoekspert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

@Singleton
public class LoginManager {

    public static final String USER_ID = "userId";
    public static final String TOKEN = "token";

    private String userId;
    private String token;
    private SharedPreferences preferences;
    private TodoApi todoApi;

    private LoginCallback loginCallback;
    private AsyncTask<String, Integer, LoginResponse> asyncTask;

    @Inject
    public LoginManager(SharedPreferences preferences) {
        this.preferences = preferences;

        userId = preferences.getString(USER_ID, null);
        token = preferences.getString(TOKEN, null);
    }


    @Inject
    public void setTodoApi(TodoApi todoApi) {
        this.todoApi = todoApi;
    }

    public boolean hasToLogin() {
        return TextUtils.isEmpty(token) || TextUtils.isEmpty(userId);
    }

    public void logout() {
        userId = null;
        token = null;
        preferences.edit().clear().apply();
    }

    public void setLoginCallback(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public void login(String username, String password) {
        if (asyncTask == null) {
            asyncTask = new AsyncTask<String, Integer, LoginResponse>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    if (loginCallback != null) {
                        loginCallback.beforeLogin();
                    }
                }

                @Override
                protected LoginResponse doInBackground(String... params) {


                    try {
                        return todoApi.login(params[0], params[1]);
                    } catch (RetrofitError error) {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(LoginResponse result) {
                    super.onPostExecute(result);
                    asyncTask = null;
                    if (result != null) {
                        saveUser(result.getObjectId(), result.getSessionToken());
                        if (loginCallback != null) {
                            loginCallback.loginDone();
                        }
                    } else {
                        if (loginCallback != null) {
                            loginCallback.loginError("Login error");
                        }
                    }
                }
            };

            asyncTask.execute(username, password);
        }
    }

    private void saveUser(String userId, String token) {
        this.userId = userId;
        this.token = token;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, userId);
        editor.putString(TOKEN, token);
        editor.apply();

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public interface LoginCallback {
        void loginDone();

        void loginError(String message);

        void beforeLogin();
    }
}
