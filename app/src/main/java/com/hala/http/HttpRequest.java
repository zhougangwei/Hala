package com.hala.http;


import com.hala.bean.AnchorBean;
import com.hala.bean.BaseBean;
import com.hala.bean.LoginBean;
import com.hala.bean.LoginBean2;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kiddo on 2017/11/29.
 */

public interface HttpRequest {

    @GET("/anchor/{user}")
    Observable<AnchorBean> getAnchorData(@Path("user") int user
    );



    @POST("/account/signin_or_signup")
    Observable<LoginBean> login(@Body RequestBody requestBody
    );
    RequestBody login(@Query("code") String code, @Query("mobileNumber")String mobileNumber
    );

    @POST("/account/fb_signin_or_signup")
    Observable<LoginBean> loginFacebook(@Body RequestBody requestBody
    );
    RequestBody loginFacebook(@Query("fbToken") String fbToken
    );


    @POST("/account/mobile_signup")
    Observable<BaseBean> regist(@Body RequestBody requestBody
    );
    RequestBody regist(@Query("avatarUrl")String avatarUrl, @Query("username")String username,
                       @Query("gender")String gender, @Query("birthDate")String birthDate, @Query("facebookId")String facebookId
    );
    RequestBody regist(@Query("code") String code, @Query("avatarUrl")String avatarUrl, @Query("username")String username,
                       @Query("gender")String gender, @Query("birthDate")String birthDate, @Query("mobileNumber")String mobileNumber
    );

}
