package com.hala.activity;

import com.hala.avchat.AvchatInfo;
import com.hala.base.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hala.R;
import com.hala.base.App;
import com.hala.base.ChatManager;

import java.util.List;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmChannel;
import io.agora.rtm.RtmChannelListener;
import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmStatusCode;

public class OneToOneActivity extends BaseActivity implements ResultCallback<Void> {

    private static final String TAG = "OneToOneActivity";
    @butterknife.BindView(R.id.iv_head)
    android.widget.ImageView ivHead;
    @butterknife.BindView(R.id.tv_name)
    android.widget.TextView tvName;
    @butterknife.BindView(R.id.iv_hangup_prepare)
    android.widget.ImageView ivHangupPrepare;
    @butterknife.BindView(R.id.rl_prepare)
    android.widget.RelativeLayout rlPrepare;
    @butterknife.BindView(R.id.remote_video_view_container)
    FrameLayout remoteVideoViewContainer;
    @butterknife.BindView(R.id.local_video_view_container)
    FrameLayout localVideoViewContainer;
    @butterknife.BindView(R.id.iv_hangup)
    android.widget.ImageView ivHangup;
    @butterknife.BindView(R.id.iv_camera_off)
    android.widget.ImageView ivCameraOff;
    @butterknife.BindView(R.id.iv_camera_control)
    android.widget.ImageView ivCameraControl;
    @butterknife.BindView(R.id.rl_onshow)
    android.widget.RelativeLayout rlOnshow;
    private RtcEngine mRtcEngine;
    private ChatManager mChatManager;
    private RtmClient mRtmClient;
    private int mChannelMemberCount;
    private MyRtmClientListener mClientListener;
    private RtmChannel mRtmChannel;


    private int callId;
    private int receiveId;
    private int otherId;
    private int myId;

    private boolean doOutCall;


    private static final int RTM_HANG_UP=1;
    private static final int RTM_DO_CALL=2;




    public static void docallOneToOneActivity(Context context, int anchorId){
        Intent intent = new Intent(context,VideoCallActivity.class);
        intent.putExtra("anchorId",anchorId);
        intent.putExtra("outCall",true);

        context.startActivity(intent);
    }
    public static void doReceivveOneToOneActivity(Context context, int audienceId){
        Intent intent = new Intent(context,VideoCallActivity.class);
        intent.putExtra("audienceId",audienceId);
        intent.putExtra("outCall",false);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_one_to_one;
    }


    @Override
    protected void beforeInitView() {

    }
    @Override
    protected void initView() {
        initIntent();
        initChat();
        initAgoraEngineAndJoinChannel();
    }

    private void initIntent() {
        Intent intent = getIntent();
        int anchorId = intent.getIntExtra("anchorId", -1);
        int audienceId = intent.getIntExtra("audienceId", -1);
         doOutCall = intent.getBooleanExtra("outCall", false);
        myId= AvchatInfo.getAccount();
        if(doOutCall){      //打出去
            otherId=anchorId;
            callId=myId;
            receiveId=otherId;
        }else{
            otherId=audienceId;
            callId=otherId;
            receiveId=myId;
        }


    }


    private void initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine();
        setupVideoProfile();
        setupLocalVideo();
        joinChannel();
    }

    private void initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }


    private void setupVideoProfile() {
        mRtcEngine.enableVideo();

//      mRtcEngine.setVideoProfile(Constants.VIDEO_PROFILE_360P, false); // Earlier than 2.3.0
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360, VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }

    private void setupLocalVideo() {

        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        localVideoViewContainer.addView(surfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0));
    }

    private void joinChannel() {
        mRtcEngine.joinChannel(null, "demoChannel1", "Extra Optional Data", 0); // if you do not specify the uid, we will generate the uid for you
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setupRemoteVideo(uid);
                }
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserLeft();
                }
            });
        }

        @Override
        public void onUserMuteVideo(final int uid, final boolean muted) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRemoteUserVideoMuted(uid, muted);
                }
            });
        }
    };

    private void setupRemoteVideo(int uid) {


        if (remoteVideoViewContainer.getChildCount() >= 1) {
            return;
        }

        SurfaceView surfaceView = RtcEngine.CreateRendererView(getBaseContext());
        remoteVideoViewContainer.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        surfaceView.setTag(uid); // for mark purpose

    }

    private void leaveChannel() {
        mRtcEngine.leaveChannel();
    }

    private void onRemoteUserLeft() {
        remoteVideoViewContainer.removeAllViews();
    }

    private void onRemoteUserVideoMuted(int uid, boolean muted) {


        SurfaceView surfaceView = (SurfaceView) remoteVideoViewContainer.getChildAt(0);

        Object tag = surfaceView.getTag();
        if (tag != null && (Integer) tag == uid) {
            surfaceView.setVisibility(muted ? View.GONE : View.VISIBLE);
        }
    }


    private void initChat() {
        mChatManager = App.getChatManager();
        mRtmClient = mChatManager.getRtmClient();
        mClientListener = new MyRtmClientListener();
        mChatManager.registerListener(mClientListener);
        createAndJoinChannel();

    }

    @butterknife.OnClick({R.id.iv_hangup_prepare, R.id.iv_hangup, R.id.iv_camera_off, R.id.iv_camera_control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_hangup_prepare:
                hangup();
                break;
            case R.id.iv_hangup:
                break;
            case R.id.iv_camera_off:
                break;
            case R.id.iv_camera_control:
                break;
        }
    }

    private void hangup() {
        sendRtmpMessage(RTM_HANG_UP);
    }

    public void sendRtmpMessage(int rtmType) {
        RtmMessage message = mRtmClient.createMessage();
        switch (rtmType) {
            case RTM_HANG_UP:
                break;
            case  RTM_DO_CALL:
                message.setText("tel://call?"+otherId);
                break;
        }
        mChatManager.getRtmClient().sendMessageToPeer(otherId+"",message,this);

    }

    @Override
    public void onSuccess(Void aVoid) {

    }

    @Override
    public void onFailure(ErrorInfo errorInfo) {

    }






    class MyRtmClientListener implements RtmClientListener {

        @Override
        public void onConnectionStateChanged(final int state, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (state) {
                        case RtmStatusCode.ConnectionState.CONNECTION_STATE_RECONNECTING:
                            break;
                        case RtmStatusCode.ConnectionState.CONNECTION_STATE_ABORTED:
                            //断开连接
                            //showToast(getString(R.string.account_offline));
                            finish();
                            break;
                    }
                }
            });
        }

        @Override
        public void onMessageReceived(final RtmMessage message, final String peerId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String content = message.getText();

                }
            });
        }

        @Override
        public void onTokenExpired() {

        }
    }

    /**
     * API CALLBACK: rtm channel event listener
     */
    class MyChannelListener implements RtmChannelListener {

        @Override
        public void onMessageReceived(final RtmMessage message, final RtmChannelMember fromMember) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String account = fromMember.getUserId();
                    String msg = message.getText();
                }
            });
        }

        @Override
        public void onMemberJoined(RtmChannelMember member) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChannelMemberCount++;

                }
            });
        }

        @Override
        public void onMemberLeft(RtmChannelMember member) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChannelMemberCount--;

                }
            });
        }
    }

    private void createAndJoinChannel() {

        // step 1: create a channel instance
        mRtmChannel = mRtmClient.createChannel("10086", new MyChannelListener());
        if (mRtmChannel == null) {
            // showToast(getString(R.string.join_channel_failed));
            finish();
            return;
        }

        // step 2: join the channel
        mRtmChannel.join(new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.i(TAG, "join channel success");
                getChannelMemberList();
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //showToast(getString(R.string.join_channel_failed));
                        finish();
                    }
                });
            }
        });
    }

    private void showToast(final String text) {
        Toast.makeText(OneToOneActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void getChannelMemberList() {
        mRtmChannel.getMembers(new ResultCallback<List<RtmChannelMember>>() {
            @Override
            public void onSuccess(final List<RtmChannelMember> responseInfo) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChannelMemberCount = responseInfo.size();
                    }
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.e(TAG, "failed to get channel members, err: " + errorInfo.getErrorCode());
            }
        });
    }
}
