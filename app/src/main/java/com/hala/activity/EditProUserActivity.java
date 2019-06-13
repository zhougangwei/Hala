package com.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hala.R;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.BaseBean;
import com.hala.bean.LoginBean;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;
import com.hala.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

public class EditProUserActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.ll_user_avatar)
    LinearLayout llUserAvatar;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.ll_user_name)
    LinearLayout llUserName;
    @BindView(R.id.et_gender)
    EditText etGender;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
    @BindView(R.id.et_birth)
    EditText etBirth;
    @BindView(R.id.ll_birthdate)
    LinearLayout llBirthdate;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private String type;
    private String facebookId;
    private String mobileNumber;
    private String code;

    public static final String FROM_FACEBOOK = "from_facebook";
    public static final String FROM_PHONE = "from_phone";
    private String avatarUrl;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_pro_user;
    }

    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        switch (type) {
            case FROM_FACEBOOK:
                facebookId = intent.getStringExtra("facebookId");
                break;
            case FROM_PHONE:
                mobileNumber = intent.getStringExtra("mobileNumber");
                code = intent.getStringExtra("code");
                break;
        }

    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.iv_back, R.id.ll_user_avatar, R.id.ll_user_name, R.id.ll_gender, R.id.ll_birthdate, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.ll_user_avatar:
                break;
            case R.id.ll_user_name:
                break;
            case R.id.ll_gender:
                break;
            case R.id.ll_birthdate:
                break;
            case R.id.tv_confirm:
                startConfirm();
                break;
        }
    }

    private void startConfirm() {

        String username=etUserName.getText().toString();
        String gender=etGender.getText().toString();
        String birthDate= etBirth.getText().toString();

        Observable<BaseBean> regist=null;
        if (type.equals(FROM_PHONE)) {
            regist= RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(code, avatarUrl, username, gender, birthDate, mobileNumber));
        }else if (type.equals(FROM_FACEBOOK)){
            regist=RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(avatarUrl, username, gender, birthDate,facebookId));
        }
        regist.subscribeOn(Schedulers.io())
                .compose(this.<BaseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=baseBean.getCode()) {
                            ToastUtils.showToast(EditProUserActivity.this, "注册失败");
                            return;
                        }
                        ToastUtils.showToast(EditProUserActivity.this, "注册成功");
                    }
                });




    }
}
