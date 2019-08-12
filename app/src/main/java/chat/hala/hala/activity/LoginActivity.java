package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.PhoneUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.blankj.utilcode.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.dialog.PolicyDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.wight.country.CountryActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_private_policy)
    TextView  mTvPrivatePolicy;
    @BindView(R.id.tv_choose_country)
    TextView  mTvChooseCountry;
    @BindView(R.id.et_phone_num)
    EditText  mEtPhoneNum;
    @BindView(R.id.iv_line)
    ImageView mIvLine;
    @BindView(R.id.tv_get_sms)
    TextView  mTvGetSms;

    @BindView(R.id.tv_wechat)
    TextView  mTvWechat;
    @BindView(R.id.tv_qq)
    TextView  mTvQq;

    private String mCountryCode="+86";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        initQiniu();
        mTvGetSms.setSelected(false);
        mTvGetSms.setEnabled(false);
        mEtPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null& RegexUtils.isMobileSimple(s)){
                    mTvGetSms.setSelected(true);
                    mTvGetSms.setEnabled(true);
                }else{
                    mTvGetSms.setSelected(false);
                    mTvGetSms.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    @OnClick({R.id.tv_private_policy, R.id.tv_choose_country, R.id.tv_get_sms, R.id.tv_wechat,R.id.tv_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_private_policy:
                break;
            case R.id.tv_choose_country:
                Intent intent = new Intent(this, CountryActivity.class);
                intent.putExtra("type",CountryActivity.FROM_LOGIN_PHONE);
                startActivityForResult(intent, Contact.REQUEST_CHOOSE_COUNTRY);
                break;
            case R.id.tv_get_sms:
                CharSequence text = mEtPhoneNum.getText();
                Intent intent2 = new Intent(LoginActivity.this,LoginSmsActivity.class);
                intent2.putExtra("phoneNum",mCountryCode+text);
                startActivity(intent2);
                break;
            case R.id.tv_wechat:
                break;
            case R.id.tv_qq:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==Contact.REQUEST_CHOOSE_COUNTRY) {
                mCountryCode = data.getStringExtra("countryCode");
                mTvChooseCountry.setText(mCountryCode);
            }
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

}
