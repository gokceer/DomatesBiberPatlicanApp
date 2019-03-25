package com.example.user.recipes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBobject {
    private static DatabaseRecorder dbHelper;
    private SQLiteDatabase db;

    public DBobject(Context context){
        dbHelper = new DatabaseRecorder(context);
        this.db = dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getDbConnection(){
        return this.db;
    }

    public void closeDbConnection() {
        if (this.db != null) {
            this.db.close();
        }
    }
}
