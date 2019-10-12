package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AGEventHandler;
import chat.hala.hala.avchat.AVChatSoundPlayer;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.EngineConfig;
import chat.hala.hala.avchat.MyEngineEventHandler;
import chat.hala.hala.avchat.WorkerThread;
import chat.hala.hala.base.App;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.CallStateBean;
import chat.hala.hala.bean.HeartBean;
import chat.hala.hala.bean.MediaToken;
import chat.hala.hala.bean.MessageBean;
import chat.hala.hala.bean.RtmCallBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.agora.rtm.ResultCallback;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class OneToOneActivity extends BaseActivity implements AGEventHandler {

    private final static Logger log = LoggerFactory.getLogger(OneToOneActivity.class);
    private static final String TAG = "OneToOneActivity";
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_minute_cost)
    TextView tvMinuteCost;

    @BindView(R.id.tv_call_duration)
    TextView tvCallDuration;
    @BindView(R.id.iv_hangup_prepare_audience)
    ImageView ivHangupPrepareAudience;
    @BindView(R.id.rl_prepare)
    RelativeLayout rl_prepare;


    @BindView(R.id.remote_video_view_container)
    FrameLayout remoteVideoViewContainer;
    @BindView(R.id.local_video_view_container)
    FrameLayout localVideoViewContainer;
    @BindView(R.id.iv_hangup)
    ImageView ivHangup;
    @BindView(R.id.iv_camera_off)
    ImageView ivCameraOff;
    @BindView(R.id.iv_camera_control)
    ImageView ivCameraControl;
    @BindView(R.id.rl_onshow)
    RelativeLayout rlOnshow;
    @BindView(R.id.iv_anchor_answer)
    ImageView ivAnchorAnswer;
    @BindView(R.id.iv_hangup_prepare_anchor)
    ImageView ivHangupPrepareAnchor;

    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv5)
    TextView tv5;

    @BindView(R.id.tv_charge)
    TextView tvCharge;

    @BindView(R.id.tv_message)
    TextView tv_message;

    private final AtomicBoolean startTimer = new AtomicBoolean(true);


    private int otherId;
    private int myId;

    private boolean doOutCall;


    private static final int RTM_HANG_UP = 1;
    private static final int RTM_DO_CALL = 2;
    private static final int RTM_ANSWER = 3;
    private boolean mIsCallInRefuse;
    private String channelId;       //渠道Id
    private String imageUrl;       //渠道Id
    private String message;       //渠道Id
    private String name;       //渠道Id
    private Integer callId;       //服务端通话Id
    private Disposable mSubscribe;
    private int mAnchorId;

    private String callstate;


    public static final String Call_CALLING = "calling"; //发起通话时预制状态，为拨打中
    public static final String Call_CONNECTED = "connected"; //表明已接通，开始通话
    public static final String Call_SUCCEED_HUNG_UP = "succeed_hung_up"; //表明接通成功后，通话结束挂断，当传入此状态时带上参数durationSeconds表明通话持续秒数
    public static final String Call_NO_ANSWERED = "no_answered"; //即对方无应答，类似打电话对方无人接但是一直不挂直到通讯公司告知无人接听给挂了
    public static final String Call_SELF_HUNG_UP = "self_hung_up"; //即拨打后没到对方无应答的状态自己给挂了，类似打电话响了两声就挂了。
    private int callTime = 0;            //通话时间 单位是秒
    private String anchorName = "";         //主播名字
    private String anchorUrl = "";         //主播名字
    private Integer lootId;
    private boolean enableVideo;
    private boolean muteCamera;


    /**
     * @param context
     * @param anchorId       主播Id
     * @param channelId
     * @param callId
     * @param lootId
     */
    public static void docallOneToOneActivity(Context context, int anchorId, int otherId, String channelId, int callId, int lootId,boolean enableVideo) {
        Intent intent = new Intent(context, OneToOneActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("otherId", otherId);
        intent.putExtra("outCall", true);
        intent.putExtra("channelId", channelId);
        intent.putExtra("callId", callId);
        intent.putExtra("lootId", lootId);
        intent.putExtra("enableVideo", enableVideo);        //声音还是
        context.startActivity(intent);
    }

    public static void doReceivveOneToOneActivity(Context context, String channelId, int otherId, String imageUrl, String message, String name, int callId,Integer lootId, boolean enableVideo) {
        Intent intent = new Intent(context, OneToOneActivity.class);
        intent.putExtra("otherId", otherId);
        intent.putExtra("outCall", false);
        intent.putExtra("channelId", channelId);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("message", message);
        intent.putExtra("name", name);
        intent.putExtra("callId", callId);
        intent.putExtra("lootId", lootId);
        intent.putExtra("enableVideo", enableVideo);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private void initIntent() {


        startTimerCount();
        Intent intent = getIntent();
        //主播AnchorId很有用
        mAnchorId = intent.getIntExtra("anchorId", -1);
        otherId = intent.getIntExtra("otherId", -1);

        doOutCall = intent.getBooleanExtra("outCall", false);
        channelId = intent.getStringExtra("channelId");
        imageUrl = intent.getStringExtra("imageUrl");
        message = intent.getStringExtra("message");
        name = intent.getStringExtra("name");
        lootId = intent.getIntExtra("lootId", 0);
        callId = intent.getIntExtra("callId", 0);
        enableVideo = intent.getBooleanExtra("enableVideo", true);


        myId = AvchatInfo.getAccount();
        if (doOutCall) {      //打出去
            mIsCallInRefuse = false;
        } else {
            mIsCallInRefuse = true;
        }
        LogUtils.e(TAG, "channelId: " + channelId + " callId :" + callId + " otherId :" + otherId + " myId:" + myId);


    }

    @SuppressLint("CheckResult")
    private void startTimerCount() {
        Observable.timer(60, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        // TODO: 2019/6/30 0030 关闭
                        if (   startTimer.get()) {
                            callOutHangup();
                            changeCallState(Call_NO_ANSWERED);
                        }

                    }
                });

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_one_to_one;
    }


    @Override
    protected void beforeInitView() {
        initIntent();
    }

    @Override
    protected void initView() {
        AvchatInfo.setIsInCall(true);
        showPreView();
        initAgoraEngineAndJoinChannel();
    }

    private void showPreView() {
        rl_prepare.setVisibility(View.VISIBLE);
        rlOnshow.setVisibility(View.GONE);
        AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
        if (doOutCall) {
            ivAnchorAnswer.setVisibility(View.GONE);
            tv7.setVisibility(View.GONE);
            ivHangupPrepareAnchor.setVisibility(View.GONE);
            tv6.setVisibility(View.GONE);
            ivHangupPrepareAudience.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            getAnchorData();
            tvName.setText(name);
        } else {
            ivAnchorAnswer.setVisibility(View.VISIBLE);
            tv7.setVisibility(View.VISIBLE);
            ivHangupPrepareAnchor.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            ivHangupPrepareAudience.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);
            tvName.setText(name);
            Glide.with(OneToOneActivity.this)
                    .load(imageUrl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(ivHead.getDrawable()))
                    .into(ivHead);
            tvMinuteCost.setVisibility(View.INVISIBLE);
            tv_message.setText(message);
        }

    }

    private void getAnchorData() {
        RetrofitFactory.getInstance().getAnchorData("anchor", mAnchorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AnchorBean>() {
                    @Override
                    public void onGetData(AnchorBean anchorBean) {
                        if (ResultUtils.cheekSuccess(anchorBean)) {
                            anchorName = anchorBean.getData().getUsername();
                            try {
                                anchorUrl = anchorBean.getData().getAlbum().get(0).getMediaUrl();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tvName.setText(anchorName);
                            tvMinuteCost.setText(String.format(getString(R.string.charged_coins_per_min), anchorBean.getData().getSetting().getVideoCpm() + ""));
                        }
                    }
                });
    }

    private void initAgoraEngineAndJoinChannel() {
        this.event().addEventHandler(this);
        if(enableVideo){
            rtcEngine().enableLocalVideo(true);
        }else{
            rtcEngine().enableLocalVideo(false);

        }
        setupLocalVideo();
        RetrofitFactory.getInstance().getMediaToken(channelId).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<MediaToken>() {
                    @Override
                    public void onGetData(MediaToken mediaToken) {
                        if (ResultUtils.cheekSuccess(mediaToken)) {
                            AvchatInfo.setMediaToken(mediaToken.getData().getAgora_media_token());
                            if (doOutCall) {
                                worker().queryPeersOnlineStatus(otherId + "");
                                // if you do not specify the uid, we will generate the uid for you
                            }
                        }

                    }
                });
    }


    private void setupLocalVideo() {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        localVideoViewContainer.addView(surfaceView);
        worker().preview(true, surfaceView, 0);

    }

    private void joinChannel() {
        worker().joinChannel(AvchatInfo.getMediaToken(), channelId, myId);
    }


    private void setupRemoteVideo(int uid) {

        if (remoteVideoViewContainer.getChildCount() >= 1) {
            return;
        }
        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());

        remoteVideoViewContainer.addView(surfaceView);
        rtcEngine().setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        surfaceView.setTag(uid); // for mark purpose


    }


    private void onRemoteUserLeft(int uid) {
        if (uid == otherId) {
            changeCallState(Call_SUCCEED_HUNG_UP);
            onEncCallClicked();
        }
    }

    private void onRemoteUserVideoMuted(int uid, boolean muted) {
        //SurfaceView surfaceView = (SurfaceView) remoteVideoViewContainer.getChildAt(0);
        //Object tag = surfaceView.getTag();
        //if (tag != null && (Integer) tag == uid) {
        //    surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        //}
    }


    @OnClick({R.id.tv_charge, R.id.iv_hangup_prepare_audience, R.id.iv_hangup_prepare_anchor, R.id.iv_hangup, R.id.iv_camera_off, R.id.iv_camera_control, R.id.iv_anchor_answer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_hangup_prepare_audience:
                callOutHangup();
                changeCallState(Call_SELF_HUNG_UP);
                break;
            case R.id.iv_hangup_prepare_anchor:
                callInRefuse();
                break;
            case R.id.iv_anchor_answer:
                answer();
                break;
            case R.id.iv_hangup:
                callOutHangup();
                changeCallState(Call_SUCCEED_HUNG_UP);
                break;
            case R.id.iv_camera_off:
                // TODO: 2019/6/30 0030 ga
                muteCamera=!muteCamera;
                rtcEngine().muteLocalVideoStream(muteCamera);
                break;
            case R.id.iv_camera_control:
                rtcEngine().switchCamera();
                break;
            case R.id.tv_charge:
                startActivity(new Intent(this, ChargeActivity.class));
                break;

        }
    }


    @SuppressLint("CheckResult")
    public void onEncCallClicked() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        finish();
                    }
                });
    }

    // TODO: 2019/9/20/020 别人挂断也要弹
    public void gotoVideoFinsh(String name, int time, String cost, String anchorUrl) {
        Intent intent = new Intent(OneToOneActivity.this, VideoFinishActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("time", time);
        intent.putExtra("cost", cost);
        intent.putExtra("anchorUrl", anchorUrl);
        intent.putExtra("starLevel", 4);
        startActivity(intent);
    }


    private void callInRefuse() {
        startTimer.compareAndSet(true, false);
        onEncCallClicked();
        // "status": 0 // Default
        // "status": 1 // Busy
        config().mRemoteInvitation.setResponse("{\"status\":0}");

        worker().hangupTheCall(config().mRemoteInvitation);
    }

    private void callOutHangup() {
        AVChatSoundPlayer.instance().stop();
        worker().hangupTheCall(null);
        onEncCallClicked();
        //不发心跳包了
        if (mSubscribe != null && mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }

    private void answer() {
        startTimer.compareAndSet(true, false);
        AVChatSoundPlayer.instance().stop();
        mIsCallInRefuse = false;
        joinChannel();
        worker().answerTheCall(config().mRemoteInvitation);
        showOnshow();
        startChargeTimerCount();
        //changeCallState(Call_CONNECTED);

    }

    private void changeCallState(final String mcallstate) {
        startTimer.compareAndSet(true, false);
        LogUtils.e(TAG, "callId:" + callId);
        callstate = mcallstate;
        RetrofitFactory.getInstance()
                .changeCallState(ProxyPostHttpRequest.getInstance().changeCallState(callstate, callTime), callId,lootId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CallStateBean>() {
                    @Override
                    public void onGetData(CallStateBean callStateBean) {
                        LogUtils.e(TAG, "onGetData: " + GsonUtil.parseObjectToJson(callStateBean));
                        if (ResultUtils.cheekSuccess(callStateBean)) {
                            if (Call_SUCCEED_HUNG_UP.equals(mcallstate)) {
                                if (!AvchatInfo.isAnchor()) {
                                    gotoVideoFinsh(anchorName, callStateBean.getData().getDurationSeconds(), callStateBean.getData().getWorth() + "", anchorUrl);
                                    finish();
                                }
                            }
                        }
                    }
                });
    }

    private void showOnshow() {
        rl_prepare.setVisibility(View.GONE);
        rlOnshow.setVisibility(View.VISIBLE);
    }


    private void showToast(final String text) {
        Toast.makeText(OneToOneActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && mSubscribe.isDisposed()) {
            mSubscribe.dispose();

        }
        AvchatInfo.setIsInCall(false);
        worker().leaveChannel(channelId);
        this.event().removeEventHandler(this);
        AVChatSoundPlayer.instance().stop();
    }

    @Override
    public void onBackPressed() {
        if (!doOutCall && mIsCallInRefuse) {
            callInRefuse();
        } else {
            callOutHangup();
        }
        super.onBackPressed();
    }


    protected RtcEngine rtcEngine() {
        return ((App) getApplication()).getWorkerThread().getRtcEngine();
    }

    protected final WorkerThread worker() {
        return ((App) getApplication()).getWorkerThread();
    }

    protected final EngineConfig config() {
        return ((App) getApplication()).getWorkerThread().getEngineConfig();
    }

    protected final MyEngineEventHandler event() {
        return ((App) getApplication()).getWorkerThread().eventHandler();
    }

    @Override
    public void onLoginSuccess(String uid) {

    }

    @Override
    public void onLoginFailed(String uid, ErrorInfo error) {

    }

    @Override
    public void onPeerOnlineStatusQueried(String uid, boolean online) {
        LogUtils.e(TAG, "onPeerOnlineStatusQueried: ");
        if (online) {
            joinChannel();
            worker().sendMessage(new MessageBean(otherId + "", Contact.RTM_DO_CALL_STRING + mAnchorId), new ResultCallback() {
                @Override
                public void onSuccess(Object o) {
                    LogUtils.e(TAG, "onSuccess:0 " + channelId);
                    final RtmCallBean rtmCallBean = new RtmCallBean();
                    rtmCallBean.setMessage("make your time");
                    rtmCallBean.setName(AvchatInfo.getName());
                    rtmCallBean.setImageUrl(AvchatInfo.getAvatarUrl());
                    rtmCallBean.setChannelId(channelId);
                    rtmCallBean.setCallId(callId);
                    if(lootId!=null&&lootId!=0){
                        rtmCallBean.setLootId(lootId);
                    }
                    rtmCallBean.setEnableVideo(enableVideo);
                    worker().makeACall(otherId + "", channelId, GsonUtil.parseObjectToJson(rtmCallBean));
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                }
            });
           /* Observable.timer(2000, TimeUnit.MILLISECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                        }
                    });*/
        } else {
            // TODO: 2019/6/23 0023 对方不在线 把他下线
        }
    }

    /*
     * 被叫收到了呼叫
     * */
    @Override
    public void onInvitationReceivedByPeer(LocalInvitation invitation) {

    }


    /*
     *
     *被叫接受了呼叫
     * */
    @Override
    public void onLocalInvitationAccepted(LocalInvitation invitation, String response) {
        LogUtils.e(TAG, "onLocalInvitationAccepted: ");
        AVChatSoundPlayer.instance().stop();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG, "run: " + "onLocalInvitationAccepted");
                startHeartBeat();
                showOnshow();
                changeCallState(Call_CONNECTED);
                startChargeTimerCount();
            }
        });

    }

    @SuppressLint("CheckResult")
    private void startChargeTimerCount() {
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        callTime = callTime + 1;
                        String stringForTime = TimeUtil.stringForTime(callTime * 1000);
                        tvCallDuration.setText(stringForTime);
                    }
                });
    }

    /*
     * 开始心跳
     * */
    private void startHeartBeat() {
        mSubscribe = Observable.interval(15, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .flatMap(new Function<Long, ObservableSource<HeartBean>>() {
                    @Override
                    public ObservableSource<HeartBean> apply(Long aLong) throws Exception {
                        if (lootId==null||lootId == 0) {
                            lootId = null;
                        }
                        return RetrofitFactory.getInstance().keepBeatHeart(ProxyPostHttpRequest.getInstance().keepBeatHeart(lootId, callTime), callId).subscribeOn(Schedulers.io());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HeartBean>() {
                    @Override
                    public void accept(HeartBean heartBean) throws Exception {
                        LogUtils.e(TAG, "accept: " + GsonUtil.parseObjectToJson(heartBean) + "");
                        if (ResultUtils.cheekSuccess(heartBean)) {
                            if (HeartBean.DataBean.CallInfoState.countdown.equals(heartBean.getData().getCallInfoState())) {
                                // TODO: 2019/9/18/018 没钱了 可以倒计时了
                            }if(HeartBean.DataBean.CallInfoState.hangup.equals(heartBean.getData().getCallInfoState())){
                                callOutHangup();
                                changeCallState(Call_SUCCEED_HUNG_UP);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        startHeartBeat();
                        throwable.printStackTrace();
                    }
                });


    }

    /*
     * 打电话过去被拒绝了
     * */

    @Override
    public void onLocalInvitationRefused(LocalInvitation invitation, final String response) {
        log.debug("onLocalInvitationRefused " + invitation + " " + invitation.getResponse() + " " + response);
        LogUtils.e(TAG, "onLocalInvitationRefused " + invitation + " " + invitation.getResponse() + " " + response);
        AVChatSoundPlayer.instance().stop();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.contains("status") && response.contains("1")) {
                    // TODO: 2019/6/23 0023 对方忙线
                } else {
                    // TODO: 2019/6/23 0023 对方拒绝
                    changeCallState(Call_NO_ANSWERED);
                }
                onEncCallClicked();
            }
        });
    }

    /*
     * 取消了呼叫
     * */
    @Override
    public void onLocalInvitationCanceled(LocalInvitation invitation) {
        LogUtils.e(TAG, "onLocalInvitationCanceled: ");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onEncCallClicked();
            }
        });
    }

    /*
     *
     * 收到邀请
     * */
    @Override
    public void onInvitationReceived(RemoteInvitation invitation) {
        // TODO: 2019/6/23 0023 已经在通话中 就要把人家拒绝掉
        invitation.setResponse("{\"status\":1}"); // Busy, already in call invitation.setResponse("{\"status\":1}"); // Busy, already in call
        worker().hangupTheCall(invitation);

    }

    @Override
    public void onInvitationRefused(RemoteInvitation invitation) {
        String channel = config().mChannel;
        LogUtils.e(TAG, "onInvitationRefused " + invitation + " " + invitation.getResponse() + " " + channel);
        if (channel == null) {
            return;
        }

        if (TextUtils.equals(channel, invitation.getChannelId()) || TextUtils.equals(channel, invitation.getContent())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onEncCallClicked();
                }
            });
        }
    }

    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {

        LogUtils.e(TAG, "onFirstRemoteVideoDecoded: " + uid + " otherId" + otherId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupRemoteVideo(uid);
            }
        });
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        LogUtils.e(TAG, "onJoinChannelSuccess: ");
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        onRemoteUserLeft(uid);
    }

    @Override
    public void onExtraCallback(int type, Object... data) {
        //LogUtils.e(TAG, "onExtraCallback: "+type+"" );
        if (EVENT_TYPE_ON_USER_VIDEO_MUTED == type) {
            onRemoteUserVideoMuted((int) data[0], (boolean) data[1]);
        }
    }

    @Override
    public void onLastmileQuality(int quality) {

    }

    @Override
    public void onLastmileProbeResult(IRtcEngineEventHandler.LastmileProbeResult result) {

    }

    @Override
    public void onReceiveMessage(String text) {
        LogUtils.e(TAG, "onReceiveMessage: " + text);
        try {
            /*mAnchorId = Integer.parseInt(text.split("\\?")[1]);
            getAnchorData();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemoteInvitationCanceled(RemoteInvitation remoteInvitation) {
        if ((otherId + "").equals(remoteInvitation.getCallerId())) {
            finish();
        }
    }

}
