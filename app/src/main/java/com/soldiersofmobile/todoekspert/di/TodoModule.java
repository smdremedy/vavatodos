package com.soldiersofmobile.todoekspert.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soldiersofmobile.todoekspert.BuildConfig;
import com.soldiersofmobile.todoekspert.TodoApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class TodoModule {

    private Context context;

    public TodoModule(Context context) {

        this.context = context;
    }

    @Named("endpoint")
    @Singleton
    @Provides
    String provideEndpoint() {
        return "https://api.parse.com/1";
    }

    @Singleton
    @Provides
    TodoApi provideTodoApi(@Named("endpoint") String endpoint) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(endpoint);
        builder.setConverter(new GsonConverter(gson));
        builder.setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL
                : RestAdapter.LogLevel.NONE);
        RestAdapter adapter = builder.build();
        TodoApi todoApi = adapter.create(TodoApi.class);
        return todoApi;
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }
}
