package com.example.ypc.simpleweather.model;

/**
 * Created by ypc on 2015/1/31.
 */

/**
 * 市
 */
public class City {
    private int id;
    private String cityName;
    private int ProvinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }
}
