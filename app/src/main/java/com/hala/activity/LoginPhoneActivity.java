package com.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hala.MainActivity;
import com.hala.R;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.BaseBean;
import com.hala.bean.LoginBean;
import com.hala.bean.LoginBean2;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;
import com.hala.utils.FacebookLoginManager;
import com.hala.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.hala.activity.EditProUserActivity.FROM_FACEBOOK;
import static com.hala.activity.EditProUserActivity.FROM_PHONE;

public class LoginPhoneActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_country_name)
    TextView tvCountryName;
    @BindView(R.id.tv_country_code)
    TextView tvCountryCode;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_sms_num)
    EditText etSmsNum;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv_try)
    TextView tvTry;
    @BindView(R.id.iv_facebook)
    ImageView ivFacebook;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_phone;
    }


    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

    }




    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_try, R.id.iv_facebook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login:
                startLogin();
                break;
            case R.id.tv_try:
                break;
            case R.id.iv_facebook:
                loginfacebook();
                break;
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
                Intent intent = new Intent(LoginPhoneActivity.this,EditProUserActivity.class);
                intent.putExtra("type",FROM_FACEBOOK);
                intent.putExtra("facebookid",id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void startLogin() {
        //final String code = etSmsNum.getText().toString();
        final String code = "151439";
      //  final String mobileNumber = etPhoneNum.getText().toString();
        final String mobileNumber = "+"+"8613851668725";
        RetrofitFactory.getInstance()
                .login(ProxyPostHttpRequest.getInstance().login(code, mobileNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<LoginBean>() {
                    @Override
                    public void onNext(LoginBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            return;
                        }
                        String action = baseBean.getData().getAction();
                        if (Contact.SIGN_UP.equals(action)) {
                            Intent intent = new Intent(LoginPhoneActivity.this,EditProUserActivity.class);
                            intent.putExtra("mobileNumber", mobileNumber);
                            intent.putExtra("code", code);
                            intent.putExtra("type",FROM_PHONE);
                            startActivity(intent);
                            finish();
                        } else if (Contact.SIGN_IN.equals(action)) {
                            Intent intent = new Intent(LoginPhoneActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
