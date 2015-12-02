package com.soldiersofmobile.todoekspert.di;

import android.app.Activity;

import com.soldiersofmobile.todoekspert.LoginManager;
import com.soldiersofmobile.todoekspert.RefreshIntentService;
import com.soldiersofmobile.todoekspert.activities.LoginActivity;
import com.soldiersofmobile.todoekspert.activities.TodoListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TodoModule.class})

public interface TodoComponent {


    void inject(LoginActivity activity);

    void inject(TodoListActivity todoListActivity);

    LoginManager getLoginManager();

    void inject(RefreshIntentService refreshIntentService);
}
