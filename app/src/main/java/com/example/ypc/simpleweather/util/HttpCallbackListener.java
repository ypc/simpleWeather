package com.example.ypc.simpleweather.util;

/**
 * Created by ypc on 2015/2/1.
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception ex);
}
