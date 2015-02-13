package com.example.ypc.simpleweather.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ypc.simpleweather.R;
import com.example.ypc.simpleweather.db.SimpleWeatherDB;
import com.example.ypc.simpleweather.model.City;
import com.example.ypc.simpleweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypc on 2015/2/1.
 */

public class ChooseAreaActivity extends Activity {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final String TAG = "ChooseAreaActivity";

    private ListView listView;
    private TextView titleText;
    private ArrayAdapter<String> adapter;
    private SimpleWeatherDB simpleWeatherDB;
    private List<String> dataList = new ArrayList<String>();
    private List<Province> provinces = null;
    private List<City> citys = null;

    /**
     * 当前所处的级别
     */
    private int currentLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        simpleWeatherDB = SimpleWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    Province p = provinces.get(position);
                    Log.d(TAG,p.getProvinceName());
                    queryCity(p);

                } else if (currentLevel == LEVEL_CITY) {
                    City c = citys.get(position);
                    Log.d(TAG, c.getCityName());
                }
            }
        });

        queryProvinces();

    }


    /**
     * 重写返回键方法，在市一级的时候向上返回不退出
     */
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_CITY) {
            queryProvinces();
            return;
        }
        super.onBackPressed();
    }

    /**
     * 查询省
     */
    private void queryProvinces() {
        provinces = simpleWeatherDB.loadProvinces();
        if (provinces.size() > 0) {
            dataList.clear();
            for (Province p : provinces) {
                dataList.add(p.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }
    }

    /**
     * 查询市
     * @param province
     */
    private void queryCity(Province province) {
        citys = simpleWeatherDB.loadCitys(province.getId());
        if (citys.size() > 0) {
            dataList.clear();
            for (City c : citys) {
                dataList.add(c.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(province.getProvinceName());
            currentLevel = LEVEL_CITY;
        }
    }
}
