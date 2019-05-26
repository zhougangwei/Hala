package com.hala.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 *
 * Created by oneki on 2017/8/29.
 */

public class ClientManager {

    private static final long TIMEOUT = 10;
    private static final long UPLOAD_TIMEOUT = 100;
    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置

    public static OkHttpClient getHttpClient(Context context){
        return  new OkHttpClient.Builder()
                .addInterceptor(new AddInfoInterceptor(context))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public  static OkHttpClient getUploadClient(Context context){
        return new OkHttpClient.Builder()
                .addInterceptor(new AddInfoInterceptor(context))
                .connectTimeout(UPLOAD_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(UPLOAD_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(UPLOAD_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

}
