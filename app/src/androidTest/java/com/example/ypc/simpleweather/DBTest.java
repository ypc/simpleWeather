package com.example.ypc.simpleweather;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.ypc.simpleweather.db.SimpleWeatherDB;
import com.example.ypc.simpleweather.model.Province;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by ypc on 2015/1/31.
 */
public class DBTest extends AndroidTestCase {


    private static final String TAG = "DBTest";

    public void testAddProvince() {
        Province p = new Province();
        p.setProvinceName("河南省");
        p.setProvinceCode("01");
        SimpleWeatherDB.getInstance(getContext()).saveProvince(p);
    }

    public void testLoadProvince() {
        List<Province> ps = SimpleWeatherDB.getInstance(getContext()).loadProvinces();
        for(Province p : ps) {
            Log.d(TAG, p.getProvinceName());
        }
    }
}
