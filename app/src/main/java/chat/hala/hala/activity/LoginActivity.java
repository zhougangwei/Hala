package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.wight.country.CountryActivity;

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
                String countryName = data.getStringExtra("countryName");
                mCountryCode = data.getStringExtra("countryCode");
                mTvChooseCountry.setText(mCountryCode);
            }
        }

    }
}
