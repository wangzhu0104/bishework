package com.example.myapplication;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*
使用说明：使用时只需实现RequestData.myCallback这个接口，并写下如下代码

        requestData = new RequestData(this);
        requestData.setUrl(Constant.urlstr+"hot_subjects.php");
        requestData.get();
 */
public class RequestData {

    myCallback mcallback;//回调接口
    String url = "";//接口路径


    public RequestData(myCallback listener) {
        this.mcallback = listener;
    }

    public void setUrl(String url1) {
        url = url1;
    }
    //定义回调接口
    public interface myCallback {
        void onSuccess(String response);

        void onFail(IOException e);
    }

    //使用GET方法
    public void get() {
        //连接网络要使用线程
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
                        if (null != mcallback)
                            mcallback.onFail(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获得返回数据
                        String res = response.body().string();
                        if (null != mcallback)
                            mcallback.onSuccess(res);
                    }
                });
            }
        }).start();
    }

    //使用POST方法
    public void post(final List<String> key1, final List<String> param) {
        //连接网络要使用线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        //连接超时
                        .connectTimeout(1000, TimeUnit.SECONDS)
                        //写入超时
                        .writeTimeout(20, TimeUnit.SECONDS)
                        //读取超时
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();
                //打包表单数据
                FormBody.Builder formBodyBuild = new FormBody.Builder();
                //key值根据需要自己定义

                for (int i =0;i<key1.size();i++){
                    formBodyBuild.add(key1.get(i),param.get(i));
                }
                //设置请求头
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBodyBuild.build())
                        .build();
                Call call = client.newCall(request);
                request.url();
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //失败做的一些处理，比如连接失败
                        if (null != mcallback)
                            mcallback.onFail(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获得返回数据
                        String res = response.body().string();
                        if (null != mcallback)
                            mcallback.onSuccess(res);
                        Data data = new Data();
                        data.setRsps();
                    }
                });
            }
        }).start();
    }

}
