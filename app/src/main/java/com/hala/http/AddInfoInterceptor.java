package com.hala.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddInfoInterceptor implements Interceptor {
    private Context context;



    public AddInfoInterceptor(Context context) {
        super();
        this.context = context;
    }



    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("devicetype", "android");

         return chain.proceed(builder.build());


    }
}