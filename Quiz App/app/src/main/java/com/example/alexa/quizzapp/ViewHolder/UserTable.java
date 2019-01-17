package com.example.alexa.quizzapp.ViewHolder;

import android.database.sqlite.SQLiteDatabase;

class UserTable {
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID= "id";
    public static final String COLUMN_NAME= "userName";
    public static final String COLUMN_EMAIL= "email";
    public static final String COLUMN_OCCUPATION= "occupation";
    public static final String COLUMN_PASSWORD= "password";

    private static final String DATABASE_CREATE = "create table " + TABLE_USERS
            + "("
            + COLUMN_ID + " integer primary key autoincrement ,"
            + COLUMN_NAME + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_OCCUPATION + "text not null, "
            + COLUMN_PASSWORD + "text not null"
            + ")";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS "  + TABLE_USERS);
        onCreate(database);
    }

}

