package com.hala.http;


import com.hala.bean.AnchorBean;
import com.hala.bean.AnchorStateBean;
import com.hala.bean.AnchorTagBean;
import com.hala.bean.BaseBean;
import com.hala.bean.BeAnchorBean;
import com.hala.bean.CallListBean;
import com.hala.bean.CoinListBean;
import com.hala.bean.LoginBean;
import com.hala.bean.MessageUnreadBean;
import com.hala.bean.OneToOneListBean;
import com.hala.bean.QiNiuToken;
import com.hala.bean.ReverseBean;
import com.hala.bean.RtmTokenBean;

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

    @POST("/anchor/apply")
    Observable<BeAnchorBean> applyAnchor(@Body RequestBody requestBody
    );
    RequestBody applyAnchor(
            @JsonQuery String dataJson
    );

    @POST("/call")
    Observable<BaseBean> startCall(@Body RequestBody requestBody
    );
    RequestBody startCall(@Query("memberId")String memberId, @Query("anchorId")String anchorId,
                       @Query("anchorMemberId")String anchorMemberId, @Query("anchorInitiate")String anchorInitiate
    );

    /*
     * 消息已读回调
     * */
    @POST("/message/read/{type}")
    Observable<BaseBean> readMessage(
            @Path("type")String type);





    @GET("/general/qtoken")
    Observable<QiNiuToken> getQiNiuToken();

    @GET("/message/unread")
    Observable<MessageUnreadBean> getMessageUnread();

    /*
    * 获取通话列表
    * */
    @GET("/call")
    Observable<CallListBean> getCallList(@Query("page") int page, @Query("size") int size);

    /*
    * 获取预约列表
    * */
    @GET("/call/reservation")
    Observable<CallListBean> getReservationList(@Query("page") int page,@Query("size") int size);



    /*
     * 获取花费列表
     * */
    @GET("coin")
    Observable<CoinListBean> getCoinList(@Query("page") int page, @Query("size") int size);



    @GET("/anchor/hot")
    Observable<OneToOneListBean> getHotOneToOneList(@Query("page") int page,@Query("size") int size);

    @GET("/anchor/new")
    Observable<OneToOneListBean> getNewOneToOneList(@Query("page") int page, @Query("size") int size);


    @GET("/anchor/rand")
    Observable<OneToOneListBean> getRandOneToOneList( @Query("size") int size);


    @POST("/call/{user}/keep")
    Observable<OneToOneListBean> keepBeatHeart(@Path("user") int user);

    @GET("/member/agora/rtm/token")
    Observable<RtmTokenBean> getRtmToken();

    @GET("/anchor/tag")
    Observable<AnchorTagBean> getAnchorTag();


    @GET("/anchor/{user}")
    Observable<AnchorBean> getAnchorData(@Path("user") int user
    );


    /*
    * 获取当前主播状态
    * */
    @GET("/call/anchor/{user}/state")
    Observable<AnchorStateBean> getAnchorState(@Path("user") int user);


    @GET("/call/anchor/{user}/reserve")
    Observable<ReverseBean> reserveAnchor(@Path("user") int user);






}
