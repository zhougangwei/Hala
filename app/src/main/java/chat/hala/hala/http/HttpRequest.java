package chat.hala.hala.http;


import chat.hala.hala.bean.AdBean;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.AnchorStateBean;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.BeAnchorBean;
import chat.hala.hala.bean.CallBean;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.bean.CallStateBean;
import chat.hala.hala.bean.CoinBriefBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.bean.FeedBackBean;
import chat.hala.hala.bean.HeartBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.MediaToken;
import chat.hala.hala.bean.MessageUnreadBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RegistBean;
import chat.hala.hala.bean.ReverseBean;
import chat.hala.hala.bean.RtmTokenBean;
import chat.hala.hala.bean.RuleBean;
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
    Observable<RegistBean> regist(@Body RequestBody requestBody
    );
    RequestBody regist(@Query("avatarUrl")String avatarUrl, @Query("username")String username,
                       @Query("gender")String gender, @Query("birthDate")String birthDate, @Query("facebookId")String facebookId
    );
    RequestBody regist(@Query("code") String code, @Query("avatarUrl")String avatarUrl, @Query("username")String username,
                       @Query("gender")String gender, @Query("birthDate")String birthDate, @Query("mobileNumber")String mobileNumber
    );
    RequestBody changeUserInfo(@Query("avatarUrl")String avatarUrl, @Query("username")String username,
                       @Query("gender")String gender, @Query("birthDate")String birthDate, @Query("mobileNumber")String mobileNumber
    );

    @POST("/member")
    Observable<RegistBean> changeUserInfo(@Body RequestBody requestBody
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
     * 消息已读回调 call和coin
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

    /*
     * 获取收入列表
     * */
    @GET("coin/income")
    Observable<CoinListBean> getCoinInComeList(@Query("page") int page, @Query("size") int size);
    /*
     * 获取花费列表
     * */
    @GET("coin/cost")
    Observable<CoinListBean> getCoinCostList(@Query("page") int page, @Query("size") int size);


    @GET("/anchor/hot")
    Observable<OneToOneListBean> getHotOneToOneList(@Query("page") int page,@Query("size") int size);

    @GET("/anchor/new")
    Observable<OneToOneListBean> getNewOneToOneList(@Query("page") int page, @Query("size") int size);


    @GET("/anchor/rand")
    Observable<OneToOneListBean> getRandOneToOneList( @Query("size") int size);


    @POST("/call/{user}/keep")
    Observable<HeartBean> keepBeatHeart(@Path("user") int user);

    @GET("/member/agora/rtm/token")
    Observable<RtmTokenBean> getRtmToken();

    @GET("call/agora/media/token")
    Observable<MediaToken> getMediaToken(@Query("channel")String channel);



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


    @POST ("/call/anchor/{user}/reserve")
    Observable<ReverseBean> reserveAnchor(@Path("user") int user);


    @POST("/call/anchor/{user}")
    Observable<CallBean> callAnchor(@Path("user") int user);

    /*
    * 获取钱的价格
    * */
    @GET("/general/rule")
    Observable<RuleBean> getRuleList();

    @GET("/coin/brief")
    Observable<CoinBriefBean> getCoinBrief();


    @POST("/call/{callId}/state")
    Observable<CallStateBean> changeCallState(@Body RequestBody requestBody,@Path("callId") int callId
    );
    RequestBody changeCallState(@Query("state")String state,@Query("durationSeconds")int durationSeconds
    );

    @POST("/member/feedback")
    Observable<FeedBackBean> feedBack(@Body RequestBody requestBody
    );
    RequestBody feedBack(@Query("description")String description,@Query("mediaUrl")String mediaUrl
            ,@Query("category")String category
    );

    @GET("/general/ad?locate=banner")
    Observable<AdBean> getAd();

    @POST("member/online")
    Observable<BaseBean>  online();

    @POST("member/offline")
    Observable<BaseBean>  offline();

}
