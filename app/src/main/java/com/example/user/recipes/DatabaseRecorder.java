package com.example.user.recipes;
import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseRecorder extends SQLiteAssetHelper {

    private static final String DATABASE_NAMES = "app4.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseRecorder(Context context) {

        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
}