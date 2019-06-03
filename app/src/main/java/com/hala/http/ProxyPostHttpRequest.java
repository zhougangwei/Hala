package com.hala.http;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Query;



public class ProxyPostHttpRequest {

    private static volatile HttpRequest postHttpRequest;

    public static HttpRequest getInstance() {
        if (postHttpRequest == null) {
            synchronized (ProxyPostHttpRequest.class) {
                if (postHttpRequest == null) {
                    postHttpRequest = create(HttpRequest.class);
                }
            }
        }
        return postHttpRequest;
    }


    public static <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args) {
                        Annotation[][] annotations = method.getParameterAnnotations();
                        int len = annotations.length;
                        JSONObject jObject = new JSONObject();
                        for (int i = 0; i < len; i++) {
                            try {
                                jObject.put(((Query) annotations[i][0]).value(), args[i]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jObject.toString());
                    }
                });
    }
}
