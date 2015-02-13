package com.example.ypc.simpleweather.db;

/**
 * Created by ypc on 2015/1/31.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import com.example.ypc.simpleweather.R;
import com.example.ypc.simpleweather.model.City;
import com.example.ypc.simpleweather.model.Province;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库访问类
 */
public class SimpleWeatherDB {


    private static final String TAG = "SimpleWeatherDB";
    private static String DB_PATH = "/data/data/com.example.ypc.simpleweather/databases/";
    private static String DB_NAME = "simpleWeather";
    private static String PREFS_NAME = "config";
    private Context myContext;

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION = 1;

    private static SimpleWeatherDB instance;

    private SQLiteDatabase db;

    private SimpleWeatherDB() {}


    private SimpleWeatherDB(Context context) {
        myContext = context;
        SimpleWeatherOpenHelper openHelper = new SimpleWeatherOpenHelper(context, DB_NAME, null, DB_VERSION);
        db = openHelper.getWritableDatabase();

//        boolean dbExist = checkDataBase();
        if (checkFirst()) {
            try {
                insertFromFile(context, R.raw.init_db);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //do nothing - database already exist
        }
    }

    /**
     * 获取实例
     * @param context
     * @return
     */
    public synchronized static SimpleWeatherDB getInstance(Context context) {
        if (instance == null) {
            instance = new SimpleWeatherDB(context);
        }
        return instance;
    }


    /**
     * 加载全国所有的省
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> provinces = new ArrayList<Province>();
        Cursor cursor = db.query("province", null ,null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                provinces.add(province);
            } while (cursor.moveToNext());
        }
        return provinces;
    }

    /**
     * 加载某个省下的所有的市
     * @param provinceId
     * @return
     */
    public List<City> loadCitys(int provinceId) {
        List<City> citys = new ArrayList<City>();
        Cursor cursor = db.query("city", null, "province_id = ?", new String[] { String.valueOf(provinceId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(provinceId);
                citys.add(city);
            } while (cursor.moveToNext());
        }
        return citys;
    }


    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (SQLiteException e) {
            //database does't exist yet.
            e.printStackTrace();
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private boolean checkFirst() {
        SharedPreferences sp = myContext.getSharedPreferences(PREFS_NAME, myContext.MODE_PRIVATE);
        if (sp.getBoolean("my_first_time", true)) {
            sp.edit().putBoolean("my_first_time", false).commit();
            return true;
        } else {
            return false;
        }
    }


    private void copyDataBase()throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[]buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public int insertFromFile(Context context, int resourceId) throws IOException {
        // Reseting Counter
        int result = 0;

        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            if (!TextUtils.isEmpty(insertStmt)) {
                db.execSQL(insertStmt);
                result++;
//                Log.d(TAG, "execute:" + insertStmt);
            }
        }
        insertReader.close();

        // returning number of inserted rows
        return result;
    }


}
