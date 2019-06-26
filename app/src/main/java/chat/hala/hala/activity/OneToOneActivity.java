package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import chat.hala.hala.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.avchat.AGEventHandler;
import chat.hala.hala.avchat.AVChatSoundPlayer;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.EngineConfig;
import chat.hala.hala.avchat.MyEngineEventHandler;
import chat.hala.hala.avchat.WorkerThread;
import chat.hala.hala.base.App;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.HeartBean;
import chat.hala.hala.bean.MediaToken;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
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
    TextView  tvName;

    @BindView(R.id.tv_minute_cost)
    TextView       tvMinuteCost;
    @BindView(R.id.iv_hangup_prepare_audience)
    ImageView      ivHangupPrepareAudience;
    @BindView(R.id.rl_prepare)
    RelativeLayout rl_prepare;


    @BindView(R.id.remote_video_view_container)
    FrameLayout    remoteVideoViewContainer;
    @BindView(R.id.local_video_view_container)
    FrameLayout    localVideoViewContainer;
    @BindView(R.id.iv_hangup)
    ImageView      ivHangup;
    @BindView(R.id.iv_camera_off)
    ImageView      ivCameraOff;
    @BindView(R.id.iv_camera_control)
    ImageView      ivCameraControl;
    @BindView(R.id.rl_onshow)
    RelativeLayout rlOnshow;
    @BindView(R.id.iv_anchor_answer)
    ImageView      ivAnchorAnswer;
    @BindView(R.id.iv_hangup_prepare_anchor)
    ImageView      ivHangupPrepareAnchor;


    private int callUserId;
    private int receiveUserId;

    private int otherId;
    private int myId;

    private boolean doOutCall;


    private static final int RTM_HANG_UP = 1;
    private static final int RTM_DO_CALL = 2;
    private static final int RTM_ANSWER  = 3;
    private boolean    mIsCallInRefuse;
    private String     channelId;       //渠道Id
    private int        callId;       //服务端通话Id
    private Disposable mSubscribe;
    private int        mAnchorId;

    /**
     * @param context
     * @param anchorId       主播Id
     * @param anchorMemberId
     * @param channelId
     * @param callId
     */
    public static void docallOneToOneActivity(Context context, int anchorId, int anchorMemberId, String channelId, int callId) {
        Intent intent = new Intent(context, OneToOneActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("anchorMemberId", anchorMemberId);
        intent.putExtra("outCall", true);
        intent.putExtra("channelId", channelId);
        intent.putExtra("callId", callId);
        context.startActivity(intent);
    }

    public static void doReceivveOneToOneActivity(Context context, String channelId, int audienceId) {
        Intent intent = new Intent(context, OneToOneActivity.class);
        intent.putExtra("audienceId", audienceId);
        intent.putExtra("outCall", false);
        intent.putExtra("channelId", channelId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private void initIntent() {

        Intent intent = getIntent();
        //主播AnchorId很有用
        mAnchorId = intent.getIntExtra("anchorId", -1);
        int anchorMemberId = intent.getIntExtra("anchorMemberId", -1);
        int audienceId = intent.getIntExtra("audienceId", -1);
        doOutCall = intent.getBooleanExtra("outCall", false);
        channelId = intent.getStringExtra("channelId");
        callId = intent.getIntExtra("callId", -1);
        myId = AvchatInfo.getAccount();
        if (doOutCall) {      //打出去
            otherId = anchorMemberId;
            callUserId = myId;
            receiveUserId = otherId;
            mIsCallInRefuse = false;
        } else {
            otherId = audienceId;
            callUserId = otherId;
            receiveUserId = myId;
            mIsCallInRefuse = true;
        }
        Log.e(TAG, "channelId: " + channelId + " callId :" + callId + " otherId :" + otherId + " myId:" + myId);


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
            ivHangupPrepareAnchor.setVisibility(View.GONE);
            ivHangupPrepareAudience.setVisibility(View.VISIBLE);
        } else {
            ivAnchorAnswer.setVisibility(View.VISIBLE);
            ivHangupPrepareAnchor.setVisibility(View.VISIBLE);
            ivHangupPrepareAudience.setVisibility(View.GONE);
        }
        RetrofitFactory.getInstance().getAnchorData(mAnchorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AnchorBean>() {
                    @Override
                    public void onNext(AnchorBean anchorBean) {
                        tvName.setText(anchorBean.getData().getNickname());
                        Glide.with(OneToOneActivity.this)
                                .load(anchorBean.getData().getAvatarUrl())
                                .into(ivHead);
                        tvMinuteCost.setText(String.format(getString(R.string.charged_coins_per_min), anchorBean.getData().getCpm() + ""));
                    }
                });

    }

    private void initAgoraEngineAndJoinChannel() {
        this.event().addEventHandler(this);
        setupLocalVideo();
        RetrofitFactory.getInstance().getMediaToken(channelId).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<MediaToken>() {
                    @Override
                    public void onNext(MediaToken mediaToken) {
                        if (ResultUtils.cheekSuccess(mediaToken)) {
                            Log.e(TAG, "mediaToken" + mediaToken.getData().getAgora_media_token());
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
            finish();
        }
    }

    private void onRemoteUserVideoMuted(int uid, boolean muted) {


        SurfaceView surfaceView = (SurfaceView) remoteVideoViewContainer.getChildAt(0);

        Object tag = surfaceView.getTag();
        if (tag != null && (Integer) tag == uid) {
            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        }
    }


    @OnClick({R.id.iv_hangup_prepare_audience, R.id.iv_hangup_prepare_anchor, R.id.iv_hangup, R.id.iv_camera_off, R.id.iv_camera_control, R.id.iv_anchor_answer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_hangup_prepare_audience:
                callOutHangup();
                break;
            case R.id.iv_hangup_prepare_anchor:
                callInRefuse();
                break;
            case R.id.iv_anchor_answer:
                answer();
                break;
            case R.id.iv_hangup:
                callOutHangup();
                break;
            case R.id.iv_camera_off:

                rtcEngine().muteLocalVideoStream(true);
                break;
            case R.id.iv_camera_control:
                rtcEngine().switchCamera();
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

                    }
                });
    }

    public void gotoVideoFinsh() {
       Intent intent = new Intent(OneToOneActivity.this, VideoFinishActivity.class);
       intent.putExtra("name");
       intent.putExtra("time");
       intent.putExtra("cost");
       intent.putExtra("starLevel", 0);
       startActivity(intent);
       finish();
    }


    private void callInRefuse() {
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
        AVChatSoundPlayer.instance().stop();
        mIsCallInRefuse = false;
        joinChannel();
        worker().answerTheCall(config().mRemoteInvitation);
        showOnshow();
        RetrofitFactory.getInstance().


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
        Log.e(TAG, "onPeerOnlineStatusQueried: ");
        if (online) {
            joinChannel();
            worker().makeACall(otherId + "", channelId);
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

        AVChatSoundPlayer.instance().stop();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startHeartBeat();
                showOnshow();
            }
        });

    }

    /*
     * 开始心跳
     * */
    private void startHeartBeat() {
        mSubscribe = Observable.interval(15, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<HeartBean>>() {
                    @Override
                    public ObservableSource<HeartBean> apply(Long aLong) throws Exception {
                        return RetrofitFactory.getInstance().keepBeatHeart(myId).subscribeOn(Schedulers.io());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HeartBean>() {
                    @Override
                    public void accept(HeartBean heartBean) throws Exception {
                        Log.e(TAG, "accept: " + GsonUtil.parseObjectToJson(heartBean) + "");
                        if (ResultUtils.cheekSuccess(heartBean)) {
                            // TODO: 2019/6/24 0024 剩余多少时间
                            int restSeconds = heartBean.getData().getRestSeconds();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
        Log.e(TAG, "onLocalInvitationRefused " + invitation + " " + invitation.getResponse() + " " + response);
        AVChatSoundPlayer.instance().stop();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.contains("status") && response.contains("1")) {
                    // TODO: 2019/6/23 0023 对方忙线
                } else {
                    // TODO: 2019/6/23 0023 对方拒绝
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
        Log.e(TAG, "onLocalInvitationCanceled: ");

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
        Log.e(TAG, "onInvitationRefused " + invitation + " " + invitation.getResponse() + " " + channel);
        if (channel == null) {
            return;
        }
        // TODO RTM 1.0 will support getChannelId()
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

        Log.e(TAG, "onFirstRemoteVideoDecoded: " + uid + " otherId" + otherId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupRemoteVideo(uid);
            }
        });
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        Log.e(TAG, "onJoinChannelSuccess: ");
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        onRemoteUserLeft(uid);
    }

    @Override
    public void onExtraCallback(int type, Object... data) {
        //Log.e(TAG, "onExtraCallback: "+type+"" );
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

}
