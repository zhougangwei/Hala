package com.hala.http;

import android.content.Context;


import com.hala.base.Contact;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class RetrofitManager {

    public  static  Retrofit.Builder getUpRetofitBuilder(Context context){
        return new Retrofit.Builder()
                .baseUrl(Contact.UPLOAD_HOST)
                // 添加Gson转换器
                .addConverterFactory(JsonConverterFactory.create())
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(ClientManager.getUploadClient(context));
    }

    public  static  Retrofit.Builder getRetofitBuilder(Context context){
        return new Retrofit.Builder()
                .baseUrl(Contact.HOST)
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(ClientManager.getHttpClient(context));
    }


}
