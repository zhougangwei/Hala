package com.hala.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hala.R;
import com.hala.avchat.AvchatInfo;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.RegistBean;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;
import com.hala.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditProUserActivity extends BaseActivity {


    private final static String[] constellationThArr = new String[] { "ราศีมังกร",
            "ราศีกุมภ์", "ราศีมีน", "ราศีเมษ", "ราศีพฤษภ", "ราศีเมถุน", "ราศีกรกฎ", "ราศีสิงห์", "ราศีกันย์", "ราศีตุล",
            "ราศีพิจิก", "ราศีธนู", "ราศีมังกร" };

    private final static String[] constellationEnArr = new String[] { "Capricornus",
            "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra",
            "Scorpio", "Sagittarius", "Capricornus" };

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
                chooseAvatar();
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

    private void chooseAvatar() {
        List<String> data = Arrays.asList(constellationEnArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(false);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
            }
        });
        picker.show();
    }

    private void startConfirm() {

        String username=etUserName.getText().toString();
        String gender=etGender.getText().toString();
        String birthDate= etBirth.getText().toString();

        Observable<RegistBean> regist=null;
        if (type.equals(FROM_PHONE)) {
            regist= RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(code, avatarUrl, username, gender, birthDate, mobileNumber));
        }else if (type.equals(FROM_FACEBOOK)){
            regist=RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(avatarUrl, username, gender, birthDate,facebookId));
        }
        regist.subscribeOn(Schedulers.io())
                .compose(this.<RegistBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RegistBean>() {
                    @Override
                    public void onNext(RegistBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=baseBean.getCode()) {
                            ToastUtils.showToast(EditProUserActivity.this, "注册失败");
                            return;
                        }
                        AvchatInfo.setName(baseBean.getData().getUsername());
                        AvchatInfo.setCoin(baseBean.getData().getCoin());
                        AvchatInfo.setAvatarUrl(baseBean.getData().getAvatarUrl());
                        ToastUtils.showToast(EditProUserActivity.this, "注册成功");
                    }
                });




    }
}
