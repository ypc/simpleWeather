package com.example.ypc.simpleweather.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by ypc on 2015/2/1.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String url, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = null;
                try {
                    client = new DefaultHttpClient();
                    HttpGet get = new HttpGet(url);
                    HttpResponse response = client.execute(get);
                    if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 200) {
                        String responseUrl = EntityUtils.toString(response.getEntity());
                        listener.onFinish(responseUrl);
                    }
                } catch (IOException e) {
                    listener.onError(e);
                } finally {
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                        client = null;
                    }
                }
            }
        }).start();
    }
}
