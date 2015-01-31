package com.example.ypc.simpleweather.util;

/**
 * Created by ypc on 2015/2/1.
 */

import android.text.TextUtils;

import com.example.ypc.simpleweather.db.SimpleWeatherDB;
import com.example.ypc.simpleweather.model.City;
import com.example.ypc.simpleweather.model.County;
import com.example.ypc.simpleweather.model.Province;

/**
 * 解析返回回来的省市县数据
 */
public class Parser {

    /**
     * 解析并保存服务器返回的省的数据
     * @param simpleWeatherDB
     * @param response
     * @return
     */
    public synchronized static boolean handlerProvinceResponse(SimpleWeatherDB simpleWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[0]);
                    province.setProvinceCode(array[1]);
                    simpleWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }


    /**
     * 解析并保存服务器返回的市的数据
     * @param simpleWeatherDB
     * @param response
     * @param provinceId
     * @return
     */
    public synchronized static boolean handlerCityResponse(SimpleWeatherDB simpleWeatherDB, String response,
                                                           int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCitys = response.split(",");
            if (allCitys != null && allCitys.length > 0) {
                for (String c : allCitys) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityName(array[0]);
                    city.setCityCode(array[1]);
                    city.setProvinceId(provinceId);
                    simpleWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }


    /**
     * 解析并保存服务器返回的县的数据
     * @param simpleWeatherDB
     * @param response
     * @param cityId
     * @return
     */
    public synchronized static boolean handlerCountyResponse(SimpleWeatherDB simpleWeatherDB, String response,
                                                             int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyName(array[0]);
                    county.setCountyCode(array[1]);
                    county.setCityId(cityId);
                    simpleWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
