package com.example.ypc.simpleweather.db;

/**
 * Created by ypc on 2015/1/31.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ypc.simpleweather.model.City;
import com.example.ypc.simpleweather.model.County;
import com.example.ypc.simpleweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库访问类
 */
public class SimpleWeatherDB {

    /**
     * 数据库名称
     */
    public static final String DB_NAME = "simple_weather";

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION = 1;

    private static SimpleWeatherDB instance;

    private SQLiteDatabase db;

    private SimpleWeatherDB() {}

    private SimpleWeatherDB(Context context) {
        SimpleWeatherOpenHelper openHelper = new SimpleWeatherOpenHelper(context, DB_NAME, null, DB_VERSION);
        db = openHelper.getWritableDatabase();
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
     * 保存一个新的省
     * @param province
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    /**
     * 添加一个新的市
     * @param city
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    /**
     * 添加一个新的县
     * @param county
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id", county.getCityId());
            db.insert("County", null, values);
        }
    }

    /**
     * 加载全国所有的省
     * @return
     */
    public List<Province> loadProvinces() {
        List<Province> provinces = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null ,null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
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
        Cursor cursor = db.query("City", null, "province_id = ?", new String[] { String.valueOf(provinceId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                citys.add(city);
            } while (cursor.moveToNext());
        }
        return citys;
    }

    /**
     * 加载一个市下的所有县
     * @param cityId
     * @return
     */
    public List<County> loadCounty(int cityId) {
        List<County> counties = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[] {String.valueOf(cityId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                counties.add(county);
            } while (cursor.moveToNext());
        }
        return counties;
    }


}
