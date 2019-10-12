package chat.hala.hala.activity;

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
import com.blankj.utilcode.utils.RegexUtils;
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

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ToastUtils;
import chat.hala.hala.weixinqq.NetworkUtil;
import chat.hala.hala.wight.country.CountryActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_private_policy)
    TextView mTvPrivatePolicy;
    @BindView(R.id.tv_choose_country)
    TextView mTvChooseCountry;
    @BindView(R.id.et_phone_num)
    EditText mEtPhoneNum;
    @BindView(R.id.iv_line)
    ImageView mIvLine;
    @BindView(R.id.tv_get_sms)
    TextView mTvGetSms;

    @BindView(R.id.tv_wechat)
    TextView mTvWechat;
    @BindView(R.id.tv_qq)
    TextView mTvQq;

    private String mCountryCode = "+86";
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String user_openId;
    private IWXAPI api;
    public Tencent mTencent;
    private UserInfo mQQInfo;
    private MyHandler handler;
    private String openid;

    public static void startLogin(Context context) {
        context.startActivity(new Intent(context,LoginActivity.class));
    }

    private static class MyHandler extends Handler {
        private final WeakReference<LoginActivity> wxEntryActivityWeakReference;

        public MyHandler(LoginActivity wxEntryActivity) {
            wxEntryActivityWeakReference = new WeakReference<LoginActivity>(wxEntryActivity);
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
                        wxEntryActivityWeakReference.get().gotoLoginThird(wxEntryActivityWeakReference.get().openid, FillUserActivity.FROM_WE, "", "", 0, "");
                    }
                    break;
            }
        }
    }

    private void gotoLoginThird(final String openid, final String fromType, final String headurl, final String name, final int sex, final String city) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (FillUserActivity.FROM_WE.equals(fromType)) {
                jsonObject.put("wxOpenId", openid);
            } else {
                jsonObject.put("qqOpenId", openid);
            }
            RetrofitFactory.getInstance()
                    .loginThird(ProxyPostHttpRequest.getJsonInstance().loginThird(jsonObject.toString()))
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
                                FillUserActivity.startFillUser(LoginActivity.this, openid, fromType, headurl, name, sex, city);
                            } else if (Contact.SIGN_IN.equals(action)) {
                                AvchatInfo.saveBaseData(baseBean.getData().getMember(), LoginActivity.this, true);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    protected void beforeInitView() {


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


    @Override
    protected void initView() {
        initQiniu();
        mTvGetSms.setSelected(false);
        mTvGetSms.setEnabled(false);
        initWeixin();
        initQQ();
        mEtPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null & RegexUtils.isMobileSimple(s)) {
                    mTvGetSms.setSelected(true);
                    mTvGetSms.setEnabled(true);
                } else {
                    mTvGetSms.setSelected(false);
                    mTvGetSms.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

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

    @OnClick({R.id.tv_private_policy, R.id.tv_choose_country, R.id.tv_get_sms, R.id.tv_wechat, R.id.tv_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_private_policy:


                break;
            case R.id.tv_choose_country:
                Intent intent = new Intent(this, CountryActivity.class);
                intent.putExtra("type", CountryActivity.FROM_LOGIN_PHONE);
                startActivityForResult(intent, Contact.REQUEST_CHOOSE_COUNTRY);
                break;
            case R.id.tv_get_sms:
                CharSequence text = mEtPhoneNum.getText();
                Intent intent2 = new Intent(LoginActivity.this, LoginSmsActivity.class);
                intent2.putExtra("phoneNum", mCountryCode + text);
                startActivityForResult(intent2,Contact.REQUEST_SMS);
                break;
            case R.id.tv_wechat:
                loginWeixin();
                break;
            case R.id.tv_qq:
                loginQQ();
                break;
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
        }else{
            mTencent.logout(this);
            mTencent.login(this, "all", loginListener);
        }
    }

    IUiListener loginListener = new BaseUiListener() {
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
                        gotoLoginThird(mTencent.getOpenId(), FillUserActivity.FROM_QQ, avatarurl, userName, gender, "");
                    } catch (JSONException e) {
                        gotoLoginThird(mTencent.getOpenId(), FillUserActivity.FROM_QQ, "", "", 0, "");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == Contact.REQUEST_CHOOSE_COUNTRY) {
                mCountryCode = data.getStringExtra("countryCode");
                mTvChooseCountry.setText(mCountryCode);
            } else if (requestCode == Contact.REQUEST_WEIXIN_QQ) {
                String type = data.getStringExtra("type");
                String openId = data.getStringExtra("openId");
                gotoLoginThird(openId, type, "", "", 1, "");
            }else if(requestCode ==Contact.REQUEST_SMS){
                finish();
            }
        }

    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastUtils.showToast(LoginActivity.this, "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtils.showToast(LoginActivity.this, "登录失败");
                return;
            }
            ToastUtils.showToast(LoginActivity.this, "登录成功");
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            LogUtils.e(values.toString());
            ToastUtils.showToast(LoginActivity.this, "doComplete");
        }

        @Override
        public void onError(UiError e) {
            ToastUtils.showToast(LoginActivity.this, "登录失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.showToast(LoginActivity.this, "onCancel");
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
