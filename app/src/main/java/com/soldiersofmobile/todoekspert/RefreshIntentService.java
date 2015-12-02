package com.soldiersofmobile.todoekspert;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.soldiersofmobile.todoekspert.db.TodoDao;

import javax.inject.Inject;

import timber.log.Timber;

public class RefreshIntentService extends IntentService {

    public static final String ACTION = BuildConfig.APPLICATION_ID + ".REFRESH";
    @Inject
    TodoDao todoDao;
    @Inject
    TodoApi todoApi;
    @Inject
    LoginManager loginManager;


    public RefreshIntentService() {
        super(RefreshIntentService.class.getSimpleName());
        App.getTodoComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("onHandleIntent");

        TodosResponse todosResponse = todoApi.getTodos(loginManager.getToken());
        for (Todo todo : todosResponse.results) {
            todoDao.insertOrUpdate(todo);
            Timber.d("Added:" + todo);
        }

        Intent broadcast = new Intent(ACTION);
        sendBroadcast(broadcast);


    }
}
