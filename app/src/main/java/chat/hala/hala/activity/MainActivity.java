package chat.hala.hala.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.igexin.sdk.PushManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.TabAdapter;
import chat.hala.hala.avchat.AGEventHandler;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.EngineConfig;
import chat.hala.hala.avchat.MyEngineEventHandler;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.avchat.WorkerThread;
import chat.hala.hala.base.App;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RongToken;
import chat.hala.hala.bean.RtmCallBean;
import chat.hala.hala.bean.RtmTokenBean;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.SPUtil;
import chat.hala.hala.wight.NoScrollViewPager;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseActivity implements AGEventHandler {


    private static final String TAG = "MainActivity";
    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.iv_home)
    TextView ivHome;
    @BindView(R.id.iv_msg)
    TextView ivMsg;
    @BindView(R.id.iv_my)
    TextView ivMy;

    @BindView(R.id.iv_follow)
    TextView ivFollow;
    @BindView(R.id.iv_qiuliao)
    TextView ivQiuliao;



    private long oldTime;
    private TabAdapter mTabAdapter;

    List<TextView> viewList = new ArrayList<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInitView() {

    }

    private void initRongIm() {
        RetrofitFactory.getInstance().getRongToken()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BaseCosumer<RongToken>() {
                    @Override
                    public void onGetData(RongToken rongToken) {
                        if (ResultUtils.cheekSuccess(rongToken)) {
                            RongIM.connect(rongToken.getData().getRongCloudToken(), new RongIMClient.ConnectCallback() {
                                @Override
                                public void onSuccess(String s) {
                                    if (AvchatInfo.getMemberId() == -1) {
                                        return;
                                    }
                                    UserInfo userInfo = new UserInfo(AvchatInfo.getMemberId() + "", AvchatInfo.getName(), Uri.parse(AvchatInfo.getAvatarUrl()));
                                    userInfo.setExtra(AvchatInfo.getAnchorId()+"");

                                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                                    LogUtils.e(TAG, "onSuccess: " + s);
                                }
                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    LogUtils.e(TAG, "onError: " + errorCode);
                                }
                                @Override
                                public void onTokenIncorrect() {
                                    LogUtils.e(TAG, "onTokenIncorrect: ");
                                }
                            });
                        }
                    }
                });


    }

    @Override
    protected void initView() {
        int userId = SPUtil.getInstance(this).getUserId();
        if (userId == 0) {
        } else {
            String memberJson = SPUtil.getInstance(this).getMemberJson();
            if (TextUtils.isEmpty(memberJson)) {
            } else {
                LoginBean.DataBean.MemberBean memberBean = GsonUtil.parseJsonToBean(memberJson, LoginBean.DataBean.MemberBean.class);
                AvchatInfo.saveBaseData(memberBean, this, false);
                initBase();
            }
        }

        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        vp.setAdapter(mTabAdapter);
        vp.setOffscreenPageLimit(5);
        vp.setCurrentItem(0, false);
        viewList.add(ivHome);
        viewList.add(ivFollow);
        viewList.add(ivQiuliao);
        viewList.add(ivMsg);
        viewList.add(ivMy);
        setClicked(ivHome);


    }

    private void initBase() {
        ((App) getApplication()).initWorkerThread();
        initRongIm();
        initQiniu();
        initVideoCall();
        initChat();
        boolean b = PushManager.getInstance().bindAlias(this, AvchatInfo.getMemberId() + "");
        System.out.println(b+"");
    }

    /*
     * 七牛初始化
     * */
    private void initQiniu() {
        RetrofitFactory
                .getInstance()
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<QiNiuToken>() {
                    @Override
                    public void onGetData(QiNiuToken baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            return;
                        }
                        QiNiuToken.DataBean.StarchatanchorBean starchatanchor = baseBean.getData().getStarchatanchor();
                        QiNiuToken.DataBean.StarchatfeedbackBean starchatfeedback = baseBean.getData().getStarchatfeedback();
                        QiNiuToken.DataBean.StarchatmemberBean starchatmember = baseBean.getData().getStarchatmember();
                        QiniuInfo.setmStarchatanchorBean(starchatanchor);
                        QiniuInfo.setmStarchatfeedbackBean(starchatfeedback);
                        QiniuInfo.setmStarchatmemberBean(starchatmember);
                    }
                });
    }

    private void initVideoCall() {
        this.event().addEventHandler(this);
    }

    private void initChat() {
        RetrofitFactory.getInstance()
                .getRtmToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RtmTokenBean>() {
                    @Override
                    public void onGetData(RtmTokenBean rtmTokenBean) {
                        if (rtmTokenBean.getCode() != Contact.REPONSE_CODE_SUCCESS) {
                            return;
                        }
                        String token = rtmTokenBean.getData().getAgora_rtm_token();
                        AvchatInfo.setRTMToken(token);
                        worker().connectToRtmService(AvchatInfo.getAccount() + "", token);
                    }
                });
    }


    @Override
    public void onBackPressed() {
        long newTime = System.currentTimeMillis();
        if (newTime - oldTime < 3000) {
            RongIM.getInstance().disconnect();
            finish();
        } else {
            new CommonDialog(this)
                    .setMsg(getString(R.string.want_to_log_out))
                    .setListener(new CommonDialog.OnClickListener() {
                        @Override
                        public void onClickConfirm() {
                            RongIM.getInstance().disconnect();
                            finish();
                        }

                        @Override
                        public void onClickCancel() {
                        }
                    })
                    .show();
        }
        oldTime = newTime;

    }


    @OnClick({R.id.iv_home, R.id.iv_msg, R.id.iv_my,R.id.iv_qiuliao,R.id.iv_follow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                setClicked(ivHome);
                break;
            case R.id.iv_msg:
                setClicked(ivMsg);
                break;
            case R.id.iv_my:
                setClicked(ivMy);
                break;
            case R.id.iv_qiuliao:
                setClicked(ivQiuliao);
                break;
            case R.id.iv_follow:
                setClicked(ivFollow);
                break;

        }
    }

    private void setClicked(TextView clickView) {
        for (int i = 0; i < viewList.size(); i++) {
            TextView view = viewList.get(i);
            if (view != clickView) {
                view.setSelected(false);
                view.setTextColor(Color.parseColor("#595D71"));
            } else {
                view.setSelected(true);
                view.setTextColor(Color.parseColor("#FE4164"));
                if(AvchatInfo.isLogin()){
                    vp.setCurrentItem(i, false);
                }else{
                    if(clickView != ivHome ){
                        LoginActivityNew.startLogin(this);
                    }

                }
            }
        }
    }


    /*通话的*/
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
    protected void onDestroy() {
        if (worker() != null) {
            if (event() != null) {
                event().removeEventHandler(this);
            }
            worker().disconnectFromRtmService();
        }


        super.onDestroy();
    }

    @Override
    public void onLoginSuccess(String uid) {
        LogUtils.e(TAG, "onLoginSuccess: ");
    }

    @Override
    public void onLoginFailed(String uid, ErrorInfo error) {
        LogUtils.e(TAG, "onLoginFailed: ");
    }

    @Override
    public void onPeerOnlineStatusQueried(String uid, boolean online) {

    }

    @Override
    public void onInvitationReceivedByPeer(LocalInvitation invitation) {

    }

    @Override
    public void onLocalInvitationAccepted(LocalInvitation invitation, String response) {

    }

    @Override
    public void onLocalInvitationRefused(LocalInvitation invitation, String response) {

    }

    @Override
    public void onLocalInvitationCanceled(LocalInvitation invitation) {

    }

    @Override
    public void onInvitationReceived(final RemoteInvitation invitation) {
        if (AvchatInfo.isIsInCall()) {
            invitation.setResponse("{\"status\":1}"); // Busy, already in call invitation.setResponse("{\"status\":1}"); // Busy, already in call
            worker().hangupTheCall(invitation);
        } else {

            Observable.just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            final RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                            rxPermissions.setLogging(true);
                            rxPermissions.request(Manifest.permission.CAMERA, Manifest
                                    .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                config().mRemoteInvitation = invitation;
                                                String content = invitation.getContent();
                                                RtmCallBean rtmCallBean = GsonUtil.parseJsonToBean(content, RtmCallBean.class);
                                                OneToOneActivity.doReceivveOneToOneActivity(MainActivity.this, rtmCallBean.getChannelId(), Integer.parseInt(invitation.getCallerId())
                                                        , rtmCallBean.getImageUrl(), rtmCallBean.getMessage(), rtmCallBean.getName(),rtmCallBean.getCallId(),rtmCallBean.getLootId(),rtmCallBean.isEnableVideo()

                                                );
                                            }
                                        }
                                    });
                        }
                    });
        }
    }

    @Override
    public void onInvitationRefused(RemoteInvitation invitation) {

    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {

    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {

    }

    @Override
    public void onUserOffline(int uid, int reason) {

    }

    @Override
    public void onExtraCallback(int type, Object... data) {

    }

    @Override
    public void onLastmileQuality(int quality) {

    }

    @Override
    public void onLastmileProbeResult(IRtcEngineEventHandler.LastmileProbeResult result) {

    }

    @Override
    public void onReceiveMessage(String text) {
        AvchatInfo.setCallText(text);
        LogUtils.e(TAG, "onReceiveMessage: " + text);
    }

    @Override
    public void onRemoteInvitationCanceled(RemoteInvitation remoteInvitation) {

    }

    @Override
    protected void onResume() {
        super.onResume();


    }


}


