package com.soldiersofmobile.todoekspert.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class DbHelper extends SQLiteOpenHelper {

    @Inject
    public DbHelper(Context context) {
        super(context, "todos.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = String.format("CREATE TABLE %s " +
                        "(%s TEXT PRIMARY KEY NOT NULL, %s TEXT, %s INT, %s INT, %s INT," +
                        " %s TEXT)", TodoDao.TABLE_NAME, TodoDao.C_ID,
                TodoDao.C_CONTENT, TodoDao.C_DONE, TodoDao.C_CREATED_AT,
                TodoDao.C_UPDATED_AT, TodoDao.C_USER_ID);
        Timber.d("onCreate sql:" + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TodoDao.TABLE_NAME));
        onCreate(db);

    }
}
