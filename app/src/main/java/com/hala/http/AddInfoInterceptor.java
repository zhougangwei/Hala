package com.hala.http;

import android.content.Context;

import com.hala.base.Contact;
import com.hala.utils.SPUtil;

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
        builder.addHeader("Content-Type", "application/json; charset=utf-8");
        builder.addHeader("Authorization", SPUtil.getInstance(context).getString(Contact.TOKEN,""));

        return chain.proceed(builder.build());
    }
}