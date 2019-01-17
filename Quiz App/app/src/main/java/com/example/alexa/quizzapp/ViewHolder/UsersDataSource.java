package com.example.alexa.quizzapp.ViewHolder;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.alexa.quizzapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersDataSource {

    private SQLiteDatabase database; //database reference
    private UserDatabaseHelper dbHelper; //databaseHelperReference
    private String[] allColumns ={ UserTable.COLUMN_ID,
        UserTable.COLUMN_NAME, UserTable.COLUMN_EMAIL,
        UserTable.COLUMN_PASSWORD, UserTable.COLUMN_OCCUPATION};

    //class constructor
    public UsersDataSource(Context context){
        dbHelper = new UserDatabaseHelper(context);
    }


    //opens database connection
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    //closes the database connection
    public void close(){
        dbHelper.close();
    }

    //create a new User record
    public void deleteUser(User user){
        long id = user.getId();
        database.delete(UserTable.TABLE_USERS, UserTable.COLUMN_ID + " = " + id, null);

    }

    //queries the database and retutsn all users
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();

        Cursor cursor  = database.query(UserTable.TABLE_USERS, allColumns, null, null,null,null,null);

        //check the cursor for any results
        if(cursor != null && cursor.getCount() >0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                User user =  cursorToUser(cursor);
                users.add(user);
                cursor.moveToNext();
            }
            cursor.close();
            return users;
        }
        else{
            return null;
        }
    }

    //queries the database for a particular id

    public User getUser(long id){
        final String whereClause = UserTable.COLUMN_ID + " = " +id;
        Cursor cursor = database.query(UserTable.TABLE_USERS, allColumns, whereClause, null,null,null,null);

        //check the resulr
        if(cursor.getCount()==1){
            cursor.moveToFirst();
            User newUser = cursorToUser(cursor);
            cursor.close();
            return newUser;
        }
        else{
            return null;
        }
    }

    //creates a User obj based on the current record

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setUserName(cursor.getString(1));
        user.setEmail(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        user.setOccupation(cursor.getString(4));
        return user;
    }
}



