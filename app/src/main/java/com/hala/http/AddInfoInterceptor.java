package com.hala.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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
        builder.addHeader("Content-Type", "application/json");
        String token = SPUtil.getInstance(context).getString(Contact.TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization","Bearer "+ token);
        }
        return chain.proceed(builder.build());
    }
}