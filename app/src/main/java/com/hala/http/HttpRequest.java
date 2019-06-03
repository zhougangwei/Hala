package com.hala.http;


import com.hala.bean.BaseBean;
import com.hala.bean.LoginBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kiddo on 2017/11/29.
 */

public interface HttpRequest {

    @GET("/anchor/{user}")
    Observable<BaseBean> getAnchorData(@Path("user") int user
    );

    @POST("/account/signin_or_signup")
    Observable<BaseBean<LoginBean>> login(@Body RequestBody requestBody
    );
    RequestBody login(@Query("code") int code, @Query("mobileNumber")int mobileNumber
    );

}
