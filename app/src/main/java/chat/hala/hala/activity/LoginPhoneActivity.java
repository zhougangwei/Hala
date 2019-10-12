package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.EditUserBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.RandomNameBean;
import chat.hala.hala.bean.RegistBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.AssetUtils;
import chat.hala.hala.utils.FacebookLoginManager;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.RandomUtils;
import chat.hala.hala.utils.ToastUtils;
import chat.hala.hala.wight.country.CountryActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPhoneActivity extends BaseActivity {


    private static final String TAG ="LoginPhoneActivity" ;
    private static final int REQUEST_FACEBOOK = 667;
    private static final int REQUEST_PHONE = 668;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_country_code)
    TextView tvCountryCode;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_sms_num)
    EditText etSmsNum;
    @BindView(R.id.tv_login)
    TextView tvLogin;



    @BindView(R.id.tv_send_msm)
    TextView tv_send_msm;
    private int countDown=60;
    private Disposable mCountDownSubscribe;

    private String mCountryCode="+86";       //电话国家前面的countryCode

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_phone;
    }


    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("");
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s!=null&&s.length()>7){
                    if (etSmsNum.getText().length()>0){
                        //tvLogin.setEnabled(true);
                        //tvLogin.setClickable(true);
                        tvLogin.setBackgroundResource(R.drawable.bg_rec_login_red2);
                    }else{
                        //tvLogin.setEnabled(false);
                        //tvLogin.setClickable(false);
                        tvLogin.setBackgroundResource(R.drawable.bg_rec_login_red1);
                    }
                    //tv_send_msm.setEnabled(true);
                    //tv_send_msm.setClickable(true);
                    tv_send_msm.setBackgroundResource(R.drawable.bg_rec_3_send_msm2);
                }else if(s!=null){
                    //tv_send_msm.setEnabled(false);
                    //tv_send_msm.setClickable(false);
                    tv_send_msm.setBackgroundResource(R.drawable.bg_rec_3_send_msm);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.e(TAG, "afterTextChanged: "+s.toString() );
            }
        });
        etSmsNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&&s.length()>0){
                    if (etPhoneNum.getText().length()>7){
                        tvLogin.setBackgroundResource(R.drawable.bg_rec_login_red2);
                    }else{
                        tvLogin.setBackgroundResource(R.drawable.bg_rec_login_red1);
                    }
                }else if(s!=null){
                    tvLogin.setBackgroundResource(R.drawable.bg_rec_login_red1);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_login,R.id.tv_send_msm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login:
                startLogin(1);
                break;

            case R.id.tv_country_name:
                Intent intent = new Intent(this, CountryActivity.class);
                intent.putExtra("type",CountryActivity.FROM_LOGIN_PHONE);
                startActivityForResult(intent,Contact.REQUEST_CHOOSE_COUNTRY);
                break;

            case R.id.tv_send_msm:
                sendSms();
                etSmsNum.setText("151439");
                break;
        }



    }

    private void sendSms() {
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
        mCountDownSubscribe = Observable.interval(1, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        countDown--;
                        tv_send_msm.setText(String.format(getString(R.string.count_down_sms), countDown + ""));
                        if (countDown == 0) {
                            mCountDownSubscribe.dispose();
                            countDown = 60;
                            tv_send_msm.setText("发验证码");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==Contact.REQUEST_CHOOSE_COUNTRY) {
                String countryName = data.getStringExtra("countryName");
                mCountryCode = data.getStringExtra("countryCode");

                tvCountryCode.setText(mCountryCode);
            }else if(requestCode==REQUEST_FACEBOOK){
                loginfacebook();
            }else if(requestCode==REQUEST_PHONE){
                startLogin(1);
            }
        }

    }

    private void loginfacebook() {
        FacebookLoginManager facebookLoginManager = new FacebookLoginManager();
        facebookLoginManager.init();
        facebookLoginManager.loginFaceBook(this, new FacebookLoginManager.OnResult() {
            @Override
            public void success() {
                Intent intent = new Intent(LoginPhoneActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void fail() {
                ToastUtils.showToast(LoginPhoneActivity.this,getString(R.string.login_failed));
            }
            @Override
            public void regist(String id) {
                Intent intent = new Intent(LoginPhoneActivity.this, FillUserActivity.class);
                intent.putExtra("type", FillUserActivity.FROM_WE);
                intent.putExtra("facebookid",id);
                startActivityForResult(intent,REQUEST_FACEBOOK);
            }
        });
    }

    private void startLogin(int type) {

       final String code = etSmsNum.getText().toString();
        final String mobileNumber = mCountryCode+etPhoneNum.getText().toString();
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
                            startConfirm(mobileNumber,code);
                        } else if (Contact.SIGN_IN.equals(action)) {
                            AvchatInfo.saveBaseData(baseBean.getData().getMember(),LoginPhoneActivity.this,true);
                            Intent intent = new Intent(LoginPhoneActivity.this,MainActivity.class);
                            startActivity(intent);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
    }
    private void startConfirm(String mobileNumber, String code) {
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
        editUserBean.setCode(code);
        editUserBean.setGender(0 + "");
        editUserBean.setMobileNumber(mobileNumber);
           regist = RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getJsonInstance().regist(
                   GsonUtil.parseObjectToJson(editUserBean)
           ));

        regist.subscribeOn(Schedulers.io())
                .compose(this.<RegistBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RegistBean>() {
                    @Override
                    public void onGetData(RegistBean baseBean) {
                        LogUtils.e(TAG, "onGetData: " + GsonUtil.parseObjectToJson(baseBean));
                        if (Contact.REPONSE_CODE_REGIST_FAIL_ALREADY_NAME == baseBean.getCode()) {
                            ToastUtils.showToast(LoginPhoneActivity.this, "名字已存在");
                            return;
                        }
                        if (Contact.REPONSE_CODE_REGIST_FAIL_ALREADY_PHONE == baseBean.getCode()) {
                            ToastUtils.showToast(LoginPhoneActivity.this, "手机号已存在无法注册");
                            return;
                        }
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            ToastUtils.showToast(LoginPhoneActivity.this, "保存失败");
                            return;
                        }
                        startLogin(1);

                    }
                });
    }

}
