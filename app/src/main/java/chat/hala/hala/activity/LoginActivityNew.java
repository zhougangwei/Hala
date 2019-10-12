package chat.hala.hala.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.reflect.TypeToken;
import com.tencent.connect.UserInfo;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.EditUserBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RandomNameBean;
import chat.hala.hala.bean.RegistBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.AssetUtils;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.RandomUtils;
import chat.hala.hala.utils.ToastUtils;
import chat.hala.hala.weixinqq.NetworkUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivityNew extends BaseActivity {


    @BindView(R.id.tv_private_policy)
    TextView mTvPrivatePolicy;
    @BindView(R.id.tv_wechat)
    TextView mTvWechat;

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.iv_bg)
    ImageView ivBg;


    private String accessToken;


    private String user_openId;
    private IWXAPI api;
    public Tencent mTencent;
    private UserInfo mQQInfo;
    private MyHandler handler;
    private String openid;

    private final Handler mHandler = new Handler();




    public static void startLogin(Context context) {
        context.startActivity(new Intent(context,LoginActivityNew.class));
    }
    private static class MyHandler extends Handler {
        private final WeakReference<LoginActivityNew> wxEntryActivityWeakReference;

        public MyHandler(LoginActivityNew wxEntryActivity) {
            wxEntryActivityWeakReference = new WeakReference<LoginActivityNew>(wxEntryActivity);
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
                        //wxEntryActivityWeakReference.get().gotoLoginThird(wxEntryActivityWeakReference.get().openid, FillUserActivity.FROM_WE, "", "", 0, "");
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
                                startConfirm(openid, headurl, name, sex, city);
                            } else if (Contact.SIGN_IN.equals(action)) {
                                AvchatInfo.saveBaseData(baseBean.getData().getMember(), LoginActivityNew.this, true);
                                Intent intent = new Intent(LoginActivityNew.this, MainActivity.class);
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
        return R.layout.activity_login_new;
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
        getWeixinData();

    }

    private void getWeixinData() {
        handler = new MyHandler(this);
        NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=%s&openid=%s", accessToken, user_openId), NetworkUtil.GET_INFO);
    }


    @Override
    protected void initView() {
        scrollView.setEnabled(false);
        initWeixin();
        initQiniu();
        startScroll();
    }

    private void startScroll() {
        mHandler.post(ScrollRunnable);
    }

    private Runnable ScrollRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            int off = ivBg.getMeasuredHeight() - scrollView.getHeight();// 判断高度
            if (off > 0) {
                scrollView.scrollBy(0, 1);
                if (scrollView.getScrollY() == off) {
                    scrollView.scrollTo(0, 0);
                    mHandler.postDelayed(this, 20);
                } else {
                    mHandler.postDelayed(this, 20);
                }
            }else{
                scrollView.scrollTo(0, 0);
                mHandler.postDelayed(this, 20);
            }
        }
    };

    @OnClick({R.id.tv_private_policy,R.id.tv_regist_policy,   R.id.tv_wechat,R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_private_policy:
                WebviewActivity.loadUrl(this,"http://palive.cn/privacy.html","隐私协议");
                break;
            case R.id.tv_regist_policy:
                WebviewActivity.loadUrl(this,"http://palive.cn/terms.html   ","用户协议");
                break;
            case R.id.tv_wechat:
                loginWeixin();
                break;
            case R.id.tv_phone:
                Intent intent2 = new Intent(this, LoginPhoneActivity.class);
                startActivityForResult(intent2,Contact.REQUEST_PHONE);
                break;
        }
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



    private void loginWeixin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
             if (requestCode == Contact.REQUEST_WEIXIN_QQ) {
                String type = data.getStringExtra("type");
                String openId = data.getStringExtra("openId");
                gotoLoginThird(openId, type, "", "", 1, "");
            }else if(requestCode ==Contact.REQUEST_PHONE){
                finish();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(ScrollRunnable);
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

    private void startConfirm(final String openId, String headurl, String mobileNumber, int sex, String city) {
        String birthDate ="2000-10-14";
        String json = AssetUtils.getJson(this, "name.json");
        String username ="";
        List<RandomNameBean> objects = GsonUtil.parseJsonToList(json, new TypeToken<List<RandomNameBean>>() {
        }.getType());
        if(TextUtils.isEmpty(username)){
            username=objects.get(new Random().nextInt(objects.size())).getName()+ RandomUtils.getRandomString();
        }
        Observable<RegistBean> regist = null;
        EditUserBean editUserBean = new EditUserBean();
        editUserBean.setNickname(username);
        editUserBean.setBirthDate(birthDate);
        editUserBean.setGender(sex + "");
        editUserBean.setMobileNumber(mobileNumber);
        editUserBean.setResidentialPlace(city);
        List<EditUserBean.AlbumBean> album = new ArrayList<>();
        if (!TextUtils.isEmpty(headurl)){
            EditUserBean.AlbumBean albumBean = new EditUserBean.AlbumBean();
            albumBean.setSortby("0");
            albumBean.setMediaUrl(headurl);
            album.add(albumBean);
            editUserBean.setAlbum(album);
        }

        editUserBean.setWxOpenId(openId);
            regist = RetrofitFactory.getInstance().registThirdParty(ProxyPostHttpRequest.getJsonInstance().regist(
                    GsonUtil.parseObjectToJson(editUserBean)));
        regist.subscribeOn(Schedulers.io())
                .compose(this.<RegistBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RegistBean>() {
                    @Override
                    public void onGetData(RegistBean baseBean) {
                        if (Contact.REPONSE_CODE_REGIST_FAIL_ALREADY_NAME == baseBean.getCode()) {
                            ToastUtils.showToast(LoginActivityNew.this, "名字已存在");
                            return;
                        }
                        if (Contact.REPONSE_CODE_REGIST_FAIL_ALREADY_PHONE == baseBean.getCode()) {
                            ToastUtils.showToast(LoginActivityNew.this, "手机号已存在无法注册");
                            return;
                        }
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            ToastUtils.showToast(LoginActivityNew.this, "保存失败");
                            return;
                        }
                        gotoLoginThird(openId, FillUserActivity.FROM_WE, "", "", 1, "");
                    }
                });
    }


}
