package com.example.alexa.quizzapp.ViewHolder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "Users.db";
    private static final int DATABASE_VERSION=1;

    public UserDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        UserTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        UserTable.onUpgrade(database, oldVersion, newVersion);


    }
}
