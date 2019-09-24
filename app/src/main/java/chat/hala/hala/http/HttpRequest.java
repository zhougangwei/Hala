package chat.hala.hala.http;


import chat.hala.hala.bean.AdBean;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.AnchorStateBean;
import chat.hala.hala.bean.ApplyAnchorBean;
import chat.hala.hala.bean.ApplyListBean;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.BeAnchorBean;
import chat.hala.hala.bean.BeStarResultBean;
import chat.hala.hala.bean.CallBean;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.bean.CallStateBean;
import chat.hala.hala.bean.CoinBriefBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.bean.FamilyAnchorBean;
import chat.hala.hala.bean.FamilyAnchorDetailBean;
import chat.hala.hala.bean.FamilyBeanA;
import chat.hala.hala.bean.FamilyBeanB;
import chat.hala.hala.bean.FansBean;
import chat.hala.hala.bean.FeedBackBean;
import chat.hala.hala.bean.HeartBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.MediaToken;
import chat.hala.hala.bean.MessageUnreadBean;
import chat.hala.hala.bean.MinuteBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RegistBean;
import chat.hala.hala.bean.ReportBean;
import chat.hala.hala.bean.ReverseBean;
import chat.hala.hala.bean.ReverseListBean;
import chat.hala.hala.bean.RongToken;
import chat.hala.hala.bean.RtmTokenBean;
import chat.hala.hala.bean.RuleBean;
import chat.hala.hala.bean.TagBean;
import chat.hala.hala.bean.VersionBean;
import io.reactivex.Completable;
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


    @POST("/account/third_party_signin_or_signup")
    Observable<LoginBean> loginThird(@Body RequestBody requestBody);

    RequestBody loginThird(@JsonQuery String dataJson
    );



    @POST("/account/fb_signin_or_signup")
    Observable<LoginBean> loginFacebook(@Body RequestBody requestBody
    );
    RequestBody loginFacebook(@Query("fbToken") String fbToken
    );


    @POST("/account/mobile_signup")
    Observable<RegistBean> regist(@Body RequestBody requestBody
    );


    @POST("/account/third_party_signup")
    Observable<RegistBean> registThirdParty(@Body RequestBody requestBody
    );

    RequestBody regist(@JsonQuery String dataJson
    );

    RequestBody regist(@Query("avatarUrl")String avatarUrl, @Query("username")String username,
                       @Query("gender")String gender, @Query("birthDate")String birthDate, @Query("facebookId")String facebookId
    );

    RequestBody changeUserInfo(@JsonQuery String dataJson);

    @POST("/{type}")
    Observable<RegistBean> changeUserInfo(@Body RequestBody requestBody,@Path("type")String type
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



    @GET("/member/rong_cloud/token")
    Observable<RongToken> getRongToken();

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
    Observable<ReverseListBean> getReservationList(@Query("page") int page, @Query("size") int size);

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


    @GET("/anchor/recommend")
    Observable<OneToOneListBean> getRecommendList(@Query("page") int page, @Query("size") int size);

    @GET("/anchor/rand")
    Observable<OneToOneListBean> getRandOneToOneList( @Query("size") int size);


    @POST("/call/{callId}/keep")
    Observable<HeartBean> keepBeatHeart(@Body RequestBody requestBody,@Path("callId") int callId);
    RequestBody keepBeatHeart(@Query("lootId")Integer lootId,@Query("durationSeconds")Integer durationSeconds
    );



    @GET("/member/agora/rtm/token")
    Observable<RtmTokenBean> getRtmToken();

    @GET("call/agora/media/token")
    Observable<MediaToken> getMediaToken(@Query("channel")String channel);

    @GET("/anchor/tag")
    Observable<TagBean> getAnchorTag();

    @GET("/{type}/{user}")
    Observable<AnchorBean> getAnchorData(@Path("type") String type,@Path("user") int user
    );

    /*
    * 获取当前主播状态
    * */
    @GET("/call/anchor/{user}/state")
    Observable<AnchorStateBean> getAnchorState(@Path("user") int user);


    @POST ("/call/anchor/{user}/reserve/{type}")
    Observable<ReverseBean> reserveAnchor(@Path("user") int user,@Path("type") String type);


    @POST("/call/anchor/{anchorId}/{memberId}/{type}")
    Observable<CallBean> callAnchor(@Path("anchorId") int anchor,@Path("memberId") int memberId,@Path("type") String type);


    /*
    * 获取钱的价格
    * */
    @GET("/general/rule")
    Observable<RuleBean> getRuleList();

    @GET("/coin/brief")
    Observable<CoinBriefBean> getCoinBrief();


    @POST("/call/{callId}/state/{lootId}")
    Observable<CallStateBean> changeCallState(@Body RequestBody requestBody,@Path("callId") int callId,@Path("lootId") Integer lootId
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

    @GET("/account/sms/send")
    Observable<BaseBean> sendSms();

    @GET("/anchor/application/result")
    Observable<BeStarResultBean> getBeStarState();

    @POST("/relationship/{type}/member/{memberId}")
    Observable<BaseBean> addFollow(@Path("type")String type,@Path("memberId")int memberId);

    /*
    * 拉黑
    * */
    @POST("/relationship/{type}/member/{memberId}")
    Observable<BaseBean> addBlock(@Path("type")String type,@Path("memberId")int memberId);

    @POST("/{type}/setting")
    Observable<BaseBean> chatSetting(@Path("type")String type,@Body RequestBody requestBody);

    RequestBody chatSetting(@JsonQuery String dataJson
    );

    @GET("/relationship/{type}")
    Observable<FansBean> getFansNum(@Path("type")String type,@Query("page") int page,@Query("size") int size);


    @GET("/member/report/reason")
    Observable<ReportBean> getReportList();

    RequestBody report(
            @Query("category")String category,@Query("reasonType")int reasonType,@Query("toMemberId")int toMemberId
    );

    @GET("/general/version")
    Observable<VersionBean> getVersion();


    @POST("/chat/to/{anchorId}/charge")
    Observable<MinuteBean> minuteCharge(@Path("anchorId")int anchorId, @Body RequestBody requestBody );

    RequestBody minuteCharge(@Query("category") String category
    );

    @POST("/chat/to/progressChatQueue")
    Observable<BaseBean> startQiuliao(@Body RequestBody requestBody );

    RequestBody startQiuliao(@Query("category") String category,@Query("coin") int coin
    );

    @GET("/chat/to/getList")
    Observable<ApplyListBean> getApplyList(@Query("page") int page, @Query("size") int size);

    @GET("/chat/to/clickLoot")
    Observable<BaseBean> startApply(@Query("lootChatId")int lootChatId);

    @POST("/call/reservation/{reservationId}/reply")
    Observable<CallBean> replyMember(@Path("reservationId")int reservationId);

    @GET("/family/getAnchorManage")
    Observable<FamilyAnchorBean> getFamilyAnchor(@Query("page") int page, @Query("size") int size);

    @GET("/family/getAnchorDetails")
    Observable<FamilyAnchorDetailBean>getFamilyAnchorDetail(@Query("memberId") int memberId, @Query("startedAt") String startedAt
    , @Query("endedAt") String endedAt
    );

    @GET("/family/getFamilyManageA")
    Observable<FamilyBeanA> getFamilyManageA();


    @GET("/family/getFamilyManageB")
    Observable<FamilyBeanB> getFamilyManageB(@Query("startedAt") String startedAt
            , @Query("endedAt") String endedAt);
}
