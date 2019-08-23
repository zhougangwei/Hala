package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.utils.ToastUtils;
import chat.hala.hala.weixinqq.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginSmsActivity extends BaseActivity {

    @BindView(R.id.tv_phone_num)
    TextView mTvPhoneNum;
    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;
    @BindView(R.id.et_sms_num)
    EditText  mEtSmsNum;
    @BindView(R.id.iv_line)
    ImageView mIvLine;
    @BindView(R.id.tv_next)
    TextView  mTvNext;

    @BindView(R.id.tv_wechat)
    TextView mTvWechat;
    @BindView(R.id.tv_qq)
    TextView mTvQq;
    private String mobileNumber;
    private int countDown=60;
    private Disposable mCountDownSubscribe;
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String user_openId;
    private IWXAPI api;
    public Tencent mTencent;
    private UserInfo mQQInfo;
    private MyHandler handler;
    private String openid;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_sms;
    }


    @Override
    protected void beforeInitView() {

    }

    private static class MyHandler extends Handler {
        private final WeakReference<LoginSmsActivity> wxEntryActivityWeakReference;

        public MyHandler(LoginSmsActivity wxEntryActivity) {
            wxEntryActivityWeakReference = new WeakReference<LoginSmsActivity>(wxEntryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            int tag = msg.what;
            Bundle data = msg.getData();
            switch (tag) {
                case NetworkUtil.GET_INFO:
                    try {
                        JSONObject json = new JSONObject(data.getString("result"));
                        final String nickname;
                        final String city;
                        final String headimgurl;
                        int sex;
                        wxEntryActivityWeakReference.get().openid = getcode(json.getString("openid"));
                        headimgurl = json.getString("headimgurl");
                        String encode;
                        encode = getcode(json.getString("nickname"));
                        nickname = new String(json.getString("nickname").getBytes(encode), "utf-8");
                        sex = json.getInt("sex");
                        city = json.getString("city");
                        wxEntryActivityWeakReference.get().gotoLoginThird(wxEntryActivityWeakReference.get().openid, FillUserActivity.FROM_WE, headimgurl, nickname, sex == 1 ? 0 : 1, city);
                    } catch (Exception e) {
                        e.printStackTrace();
                        wxEntryActivityWeakReference.get().gotoLoginThird( wxEntryActivityWeakReference.get().openid, FillUserActivity.FROM_WE, "", "", 0, "");
                    }
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e("onNewIntent" + user_openId);
        setIntent(intent);
        user_openId = intent.getStringExtra("openId");
        accessToken = intent.getStringExtra("accessToken");
        refreshToken = intent.getStringExtra("refreshToken");
        scope = intent.getStringExtra("scope");
        getWeixinData();
    }
    private void getWeixinData() {
        handler = new MyHandler(this);
        NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=%s&openid=%s", accessToken, user_openId), NetworkUtil.GET_INFO);
    }


    private void gotoLoginThird(final String openid, final String fromType, final String headurl, final String name, final int sex, final String city) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (FillUserActivity.FROM_WE.equals(fromType)){
                jsonObject.put("wxOpenId", openid);
            }else{
                jsonObject.put("qqOpenId", openid);
            }
            RetrofitFactory.getInstance()
                    .loginThird(ProxyPostHttpRequest.getInstance().loginThird(jsonObject.toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseCosumer<LoginBean>() {
                        @Override
                        public void onGetData(LoginBean baseBean) {
                            if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                                return;
                            }
                            String action = baseBean.getData().getAction();
                            if (Contact.SIGN_UP.equals(action)) {
                                FillUserActivity.startFillUser(LoginSmsActivity.this,openid,fromType,headurl,name,sex,city);
                            } else if (Contact.SIGN_IN.equals(action)) {
                                AvchatInfo.saveBaseData(baseBean.getData().getMember(),LoginSmsActivity.this,true);
                                Intent intent = new Intent(LoginSmsActivity.this,MainActivity.class);
                                startActivity(intent);
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("phoneNum");
        mTvPhoneNum.setText(mobileNumber);
        mTvNext.setSelected(false);
        mTvNext.setEnabled(false);
        mEtSmsNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null&&s.length()>5) {
                    mTvNext.setSelected(true);
                    mTvNext.setEnabled(true);
                } else {
                    mTvNext.setSelected(false);
                    mTvNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        sendSms();
        initWeixin();
        initQQ();

    }

    private void sendSms() {
        mEtSmsNum.setText("151439");
        startCountDown();
        RetrofitFactory.getInstance().sendSms().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void startCountDown() {
        mTvCountDown.setVisibility(View.VISIBLE);
        mCountDownSubscribe = Observable.interval(1, TimeUnit.SECONDS)
               .compose(this.<Long>bindToLifecycle())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Consumer<Long>() {
                   @Override
                   public void accept(Long aLong) throws Exception {
                       countDown--;
                       mTvCountDown.setText(String.format(getString(R.string.count_down_sms), countDown + ""));
                       if (countDown == 0) {
                           mCountDownSubscribe.dispose();
                           countDown = 60;
                       }
                   }
               });
    }


    @OnClick({R.id.tv_next, R.id.tv_wechat, R.id.tv_qq,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                startLogin();
                break;
            case R.id.tv_wechat:
                loginWeixin();
                break;
            case R.id.tv_qq:
                loginQQ();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void startLogin() {

        final String code = mEtSmsNum.getText().toString();
        RetrofitFactory.getInstance()
                .login(ProxyPostHttpRequest.getInstance().login(code, mobileNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<LoginBean>() {
                    @Override
                    public void onGetData(LoginBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            return;
                        }
                        String action = baseBean.getData().getAction();
                        if (Contact.SIGN_UP.equals(action)) {
                            Intent intent = new Intent(LoginSmsActivity.this, FillUserActivity.class);
                            intent.putExtra("mobileNumber", mobileNumber);
                            intent.putExtra("code", code);
                            intent.putExtra("type", FillUserActivity.FROM_PHONE);
                            startActivityForResult(intent,Contact.REQUEST_PHONE);
                        } else if (Contact.SIGN_IN.equals(action)) {
                            AvchatInfo.saveBaseData(baseBean.getData().getMember(),LoginSmsActivity.this,true);
                            Intent intent = new Intent(LoginSmsActivity.this,MainActivity.class);
                            startActivity(intent);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
            return;
        }
        if (resultCode==RESULT_OK){
            if(requestCode==Contact.REQUEST_PHONE){
                startLogin();
            }else if(requestCode==Contact.REQUEST_WEIXIN_QQ){
                String type = data.getStringExtra("type");
                String openId = data.getStringExtra("openId");
                gotoLoginThird(openId, type, "", "", 1, "");
            }
        }
    }

    private void initQQ() {
        mTencent = Tencent.createInstance(Contact.QQ_APP_ID, this.getApplicationContext());
    }

    private void initWeixin() {

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Contact.WEIXIN_APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(Contact.WEIXIN_APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(Contact.WEIXIN_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }



    /* -----------------------QQ登录-----------------------------*/

    private void loginQQ() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }

    IUiListener loginListener = new LoginSmsActivity.BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                }

                @Override
                public void onComplete(final Object response) {
                    try {
                        JSONObject json = (JSONObject) response;
                        String avatarurl = "";
                        String userName = "";
                        int gender = 0;
                        if (json.has("figureurl_qq_1")) {
                            avatarurl = json.getString("figureurl_qq_1");
                        }
                        userName = json.getString("nickname");
                        gender = "男".equals(json.getString("gender")) ? 0 : 1;
                        gotoLoginThird  (mTencent.getOpenId(), FillUserActivity.FROM_QQ, avatarurl, userName, gender, "");
                    } catch (JSONException e) {
                        gotoLoginThird  (mTencent.getOpenId(), FillUserActivity.FROM_QQ, "", "", 0, "");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancel() {
                }
            };
            mQQInfo = new UserInfo(this, mTencent.getQQToken());
            mQQInfo.getUserInfo(listener);

        } else {

        }
    }


    private void loginWeixin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);

    }




    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.showToast(LoginSmsActivity.this, "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtils.showToast(LoginSmsActivity.this, "登录失败");
                return;
            }
            ToastUtils.showToast(LoginSmsActivity.this, "登录成功");
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            LogUtils.e(values.toString());
            ToastUtils.showToast(LoginSmsActivity.this, "doComplete");
        }

        @Override
        public void onError(UiError e) {
            ToastUtils.showToast(LoginSmsActivity.this, "登录失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.showToast(LoginSmsActivity.this, "onCancel");
        }
    }

    private static String getcode(String str) {
        String[] encodelist = {"GB2312", "ISO-8859-1", "UTF-8", "GBK", "Big5", "UTF-16LE", "Shift_JIS", "EUC-JP"};
        for (int i = 0; i < encodelist.length; i++) {
            try {
                if (str.equals(new String(str.getBytes(encodelist[i]), encodelist[i]))) {
                    return encodelist[i];
                }
            } catch (Exception e) {

            } finally {

            }
        }
        return "";
    }


}
