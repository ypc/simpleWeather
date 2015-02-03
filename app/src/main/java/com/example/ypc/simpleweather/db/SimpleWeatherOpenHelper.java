package com.example.ypc.simpleweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ypc on 2015/1/31.
 */
public class SimpleWeatherOpenHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.ypc.simpleweather/databases/";
    private static String DB_NAME = "simpleWeather";
    private final Context myContext;



    public SimpleWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
