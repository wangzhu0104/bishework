package com.example.myapplication;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Data {
    String rsps;

    public String setRsps() {
        final String url = "http://124.220.159.4/query.php";
        new Thread(new Runnable() {
            @Override
            public void run() {
                //GET方法要在路径上写好数据
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        //连接超时
                        .connectTimeout(1000, TimeUnit.SECONDS)
                        //写入超时
                        .writeTimeout(20, TimeUnit.SECONDS)
                        //读取超时
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //失败做的一些处理

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获得返回数据
                        String res = response.body().string();
                        Log.e("res",res);
                    }
                });
            }
        }).start();

        return "abcdefg";
    }

    public String getRsps() {
        return rsps;
    }
}
