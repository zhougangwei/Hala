package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RegistBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ToastUtils;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FillUserActivity extends BaseActivity {


    private static final String TAG = "FillUserActivity";

    @BindView(R.id.iv_back)
    ImageView    ivBack;
    @BindView(R.id.tv_title)
    TextView     tvTitle;
    @BindView(R.id.iv_head)
    ImageView    ivHead;
    @BindView(R.id.ll_user_avatar)
    LinearLayout llUserAvatar;
    @BindView(R.id.et_user_name)
    EditText     etUserName;
    @BindView(R.id.ll_user_name)
    LinearLayout llUserName;
    @BindView(R.id.et_gender)
    TextView     etGender;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
    @BindView(R.id.et_birth)
    TextView     etBirth;
    @BindView(R.id.ll_birthdate)
    LinearLayout llBirthdate;
    @BindView(R.id.tv_confirm)
    TextView     tvConfirm;
    @BindView(R.id.et_location)
    TextView     mEtLocation;
    @BindView(R.id.ll_location)
    LinearLayout mLlLocation;
    @BindView(R.id.et_bio)
    TextView     mEtBio;
    @BindView(R.id.ll_bio)
    LinearLayout mLlBio;
    private String type;
    private String facebookId;
    private String mobileNumber;
    private String code;


    private String username;

    private String birthDate;


    public static final String FROM_FACEBOOK    = "from_facebook";
    public static final String FROM_PHONE       = "from_phone";
    public static final String FROM_MYFRAG_MENT = "from_myfrag_ment";
    private String avatarUrl;
    private List<String> uriList = new ArrayList<>();
    private int genderIndex;

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
        tvTitle.setText(R.string.edit_profile);
        if (FROM_MYFRAG_MENT.equals(type)) {
            etUserName.setText(AvchatInfo.getName());
            etGender.setText(AvchatInfo.getGender());
            // etBirth.setText(AvchatInfo.getBirthDate());
            Glide.with(this).load(AvchatInfo.getAvatarUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
        }

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    tvConfirm.setSelected(true);
                    tvConfirm.setEnabled(true);
                } else {
                    tvConfirm.setSelected(false);
                    tvConfirm.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.iv_back, R.id.ll_user_avatar, R.id.ll_user_name, R.id.ll_gender, R.id.ll_birthdate, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_user_avatar:
                chooseAvatar();
                break;
            case R.id.ll_gender:
                chooseGender();
                break;
            case R.id.ll_birthdate:
                chooseBirthDate();
                break;
            case R.id.tv_confirm:
                if (!judgeEmpty()) {
                    return;
                } else {
                    upQiniu();
                }
                break;
        }
    }

    private void chooseBirthDate() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setLabel("", "", "");

        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2020, 1, 11);
        picker.setRangeStart(1900, 1, 1);
        picker.setSelectedItem(2000, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                etBirth.setText(String.format("%s-%s-%s", year, month, day));
                ToastUtils.showToast(FillUserActivity.this, year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(String.format("%s-%s-%s", year, picker.getSelectedMonth(), picker.getSelectedDay()));
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(String.format("%s-%s-%s", picker.getSelectedYear(), month, picker.getSelectedDay()));
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(String.format("%s-%s-%s", picker.getSelectedYear(), picker.getSelectedMonth(), day));
            }
        });
        picker.show();

    }

    private void chooseGender() {

        String[] sexArr = new String[]{getString(R.string.Secret), getString(R.string.male), getString(R.string.female)};
        List<String> data = Arrays.asList(sexArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(17);
        picker.setTextPadding(10);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                genderIndex = index;
                etGender.setText(item);
            }
        });
        picker.show();
    }

    private boolean judgeEmpty() {
        username = etUserName.getText().toString();

        birthDate = etBirth.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showToast(this, "userName" + "is Empty");
            return false;
        }

        return true;
    }

    private void upQiniu() {
        QiNiuToken.DataBean.StarchatmemberBean starchatmemberBean = QiniuInfo.getmStarchatmemberBean();
        if (starchatmemberBean == null) {
            return;
        }
        new UploadPicManger().uploadImageArray(uriList, 0, starchatmemberBean.getToken(), starchatmemberBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                avatarUrl = path;
                LogUtils.e(TAG, "uploadSuccess: " + avatarUrl);
                startConfirm();
            }

            @Override
            public void uploadFailure() {
                // TODO: 2019/6/25 0025 上传图片失败
                startConfirm();
                LogUtils.e(TAG, "uploadFailure: 失败");
            }
        });
    }


    private void chooseAvatar() {
        ChoosePicManager.choosePic(FillUserActivity.this, 1);
    }

    private void startConfirm() {

        Observable<RegistBean> regist = null;
        if (type.equals(FROM_PHONE)) {
            regist = RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(code, avatarUrl, username, genderIndex + "", birthDate, mobileNumber));
        } else if (type.equals(FROM_FACEBOOK)) {
            regist = RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(avatarUrl, username, genderIndex + "", birthDate, facebookId));
        } else if (type.equals(FROM_MYFRAG_MENT)) {
            regist = RetrofitFactory.getInstance().changeUserInfo(ProxyPostHttpRequest.getInstance().changeUserInfo(avatarUrl, username, genderIndex + "", birthDate, mobileNumber));
        }
        regist.subscribeOn(Schedulers.io())
                .compose(this.<RegistBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RegistBean>() {
                    @Override
                    public void onGetData(RegistBean baseBean) {
                        LogUtils.e(TAG, "onGetData: " + GsonUtil.parseObjectToJson(baseBean));
                        if (Contact.REPONSE_CODE_REGIST_FAIL_ALREADY_NAME == baseBean.getCode()) {
                            ToastUtils.showToast(FillUserActivity.this, "名字已存在");
                            return;
                        }
                        if (Contact.REPONSE_CODE_REGIST_FAIL_ALREADY_PHONE == baseBean.getCode()) {
                            ToastUtils.showToast(FillUserActivity.this, "手机号已存在无法注册");
                            return;
                        }
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            ToastUtils.showToast(FillUserActivity.this, "保存失败");
                            return;
                        }
                        AvchatInfo.setName(baseBean.getData().getUsername());
                        AvchatInfo.setCoin(baseBean.getData().getCoin());
                        AvchatInfo.setAvatarUrl(baseBean.getData().getAvatarUrl());
                        ToastUtils.showToast(FillUserActivity.this, "保存成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
            List<String> strings = Matisse.obtainPathResult(data);
            if (strings != null) {
                uriList.clear();
                uriList.addAll(strings);
                Glide.with(this).load(uriList.get(0)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
