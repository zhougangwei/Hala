package com.hala.http;

import com.hala.base.App;


/**
 *
 * Created by oneki on 2017/8/29.
 */

public class RetrofitFactory {

    private static HttpRequest retrofitService = RetrofitManager
            .getRetofitBuilder(App.sContext)
            .build()
            .create(HttpRequest.class);

    //上传接口
    private static HttpRequest uploadService =RetrofitManager.getUpRetofitBuilder(App.sContext)
            .build()
            .create(HttpRequest.class);



    public static HttpRequest getInstance() {
        return retrofitService;
    }

    public static HttpRequest getUploadInstance() {
        return uploadService;
    }



}
