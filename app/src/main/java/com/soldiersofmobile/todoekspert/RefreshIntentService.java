package com.soldiersofmobile.todoekspert;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.soldiersofmobile.todoekspert.activities.TodoListActivity;
import com.soldiersofmobile.todoekspert.db.TodoDao;

import javax.inject.Inject;

import timber.log.Timber;

public class RefreshIntentService extends IntentService {

    public static final String ACTION = BuildConfig.APPLICATION_ID + ".REFRESH";
    private static final int NOTIFICATION_ID = 1;
    @Inject
    TodoDao todoDao;
    @Inject
    TodoApi todoApi;
    @Inject
    LoginManager loginManager;
    private NotificationManager mNotificationManager;


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
        sendTimelineNotification(10);


    }

    private void sendTimelineNotification(int timelineUpdateCount) {


        if (mNotificationManager == null) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }


        mNotificationManager.cancel(NOTIFICATION_ID);

        String notificationSummary = this.getString(
                R.string.notification_message, timelineUpdateCount);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());


        builder.setAutoCancel(true);
        builder.setContentTitle(getText(R.string.notification_title));
        builder.setContentText(notificationSummary);


        builder.setSmallIcon(R.drawable.ic_launcher);


        Intent backIntent = new Intent(this, TodoListActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, backIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder.addAction(R.drawable.ic_action_sort_by_size, "Sort", contentIntent);




        builder.setContentIntent(contentIntent);


        mNotificationManager.notify(NOTIFICATION_ID, builder.build());


    }
}
