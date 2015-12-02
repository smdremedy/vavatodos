package com.soldiersofmobile.todoekspert.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.soldiersofmobile.todoekspert.Todo;

/**
 * Created by Sylwester on 2015-12-02.
 */
public class TodoDao {

    /**
     * Nazwy kolumn w DB.
     */
    public static final String C_ID = "_id";
    public static final String C_CONTENT = "content";
    public static final String C_DONE = "done";
    public static final String C_USER_ID = "user_id";
    public static final String C_CREATED_AT = "created_at";
    public static final String C_UPDATED_AT = "updated_at";


    /**
     * Nazwa tabeli, w której przechowywane będa obiekty
     */
    public static final String TABLE_NAME = "todos";

    private final DbHelper dbHelper;

    public TodoDao(DbHelper dbHelper) {

        this.dbHelper = dbHelper;
    }


    public void insertOrUpdate(Todo todo) {

        //pobranie bazy danych do zapisu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //utworzenie słownika nazwa kolumny -> wartość
        ContentValues values = new ContentValues();
        values.put(C_ID, todo.objectId);
        values.put(C_CONTENT, todo.getContent());
        values.put(C_DONE, todo.isDone());
//        values.put(C_CREATED_AT, todo.createdAt.getTime());
//        values.put(C_UPDATED_AT, todo.updatedAt.getTime());
        values.put(C_USER_ID, todo.user.objectId);

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);


    }
}
