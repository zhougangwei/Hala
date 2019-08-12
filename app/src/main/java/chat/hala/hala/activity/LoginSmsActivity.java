package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.RegexUtils;

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


    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_sms;
    }


    @Override
    protected void beforeInitView() {

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
                break;
            case R.id.tv_qq:
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
                            Intent intent = new Intent(LoginSmsActivity.this,EditProUserActivity.class);
                            intent.putExtra("mobileNumber", mobileNumber);
                            intent.putExtra("code", code);
                            intent.putExtra("type", EditProUserActivity.FROM_PHONE);
                            startActivityForResult(intent,Contact.REQUEST_PHONE);
                        } else if (Contact.SIGN_IN.equals(action)) {
                            AvchatInfo.saveBaseData(baseBean.getData().getMember(),LoginSmsActivity.this);
                            Intent intent = new Intent(LoginSmsActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if(requestCode==Contact.REQUEST_PHONE){
                startLogin();
            }
        }

    }

}
