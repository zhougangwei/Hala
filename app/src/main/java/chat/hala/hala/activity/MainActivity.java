package chat.hala.hala.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RtmTokenBean;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.wight.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.LocalInvitation;
import io.agora.rtm.RemoteInvitation;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements AGEventHandler {

    private static final String TAG = "MainActivity";
    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    private long oldTime;
    private TabAdapter mTabAdapter;

    List<View> viewList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        ((App) getApplication()).initWorkerThread();
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        vp.setAdapter(mTabAdapter);
        vp.setOffscreenPageLimit(5);
        vp.setCurrentItem(0, false);
        viewList.add(ivHome);
        viewList.add(ivMsg);
        viewList.add(ivMy);
        initQiniuData();
        initVideoCall();
        initChat();


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
                    public void onNext(RtmTokenBean rtmTokenBean) {
                        if (rtmTokenBean.getCode()!= Contact.REPONSE_CODE_SUCCESS) {
                            return;
                        }
                        String token = rtmTokenBean.getData().getAgora_rtm_token();
                        AvchatInfo.setRTMToken(token);
                        worker().connectToRtmService(AvchatInfo.getAccount()+"",token);
                    }
                });
    }

    private void initQiniuData() {
        RetrofitFactory
                .getInstance()
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<QiNiuToken>() {
                    @Override
                    public void onNext(QiNiuToken baseBean) {
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


    @Override
    public void onBackPressed() {
        long newTime = System.currentTimeMillis();
        if (newTime - oldTime < 3000) {
            finish();
        } else {
            new CommonDialog(this)
                    .setMsg(getString(R.string.want_to_log_out))
                    .setListener(new CommonDialog.OnClickListener() {
                        @Override
                        public void onClickConfirm() {
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


    @OnClick({R.id.iv_home, R.id.iv_msg, R.id.iv_my})
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
        }
    }

    private void setClicked(ImageView clickView) {
        for (int i = 0; i < viewList.size(); i++) {
            View view = viewList.get(i);
            if (view != clickView) {
                view.setSelected(false);
            } else {
                view.setSelected(true);
                vp.setCurrentItem(i, false);
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
        this.event().removeEventHandler(this);
        worker().disconnectFromRtmService();
        super.onDestroy();
    }

    @Override
    public void onLoginSuccess(String uid) {
        Log.e(TAG, "onLoginSuccess: ");
    }

    @Override
    public void onLoginFailed(String uid, ErrorInfo error) {
        Log.e(TAG, "onLoginFailed: " );
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
    public void onInvitationReceived(RemoteInvitation invitation) {
        if (AvchatInfo.isIsInCall()){
            invitation.setResponse("{\"status\":1}"); // Busy, already in call invitation.setResponse("{\"status\":1}"); // Busy, already in call
            worker().hangupTheCall(invitation);
        }else {
            config().mRemoteInvitation = invitation;
            OneToOneActivity.doReceivveOneToOneActivity(this, invitation.getContent(),Integer.parseInt(invitation.getCallerId()));
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
}


