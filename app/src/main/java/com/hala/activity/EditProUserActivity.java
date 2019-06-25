package com.hala.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hala.R;
import com.hala.avchat.AvchatInfo;
import com.hala.avchat.QiniuInfo;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.QiNiuToken;
import com.hala.bean.RegistBean;
import com.hala.glide.MyGlideEngine;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;
import com.hala.http.UploadPicManger;
import com.hala.utils.ToastUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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

    private static final int REQUEST_CODE_CHOOSE =224 ;
    private final static String[] constellationThArr = new String[] { "ราศีมังกร",
            "ราศีกุมภ์", "ราศีมีน", "ราศีเมษ", "ราศีพฤษภ", "ราศีเมถุน", "ราศีกรกฎ", "ราศีสิงห์", "ราศีกันย์", "ราศีตุล",
            "ราศีพิจิก", "ราศีธนู", "ราศีมังกร" };

    private final static String[] constellationEnArr = new String[] { "Capricornus",
            "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra",
            "Scorpio", "Sagittarius", "Capricornus" };
    private static final String TAG                     = "EditProUserActivity";

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



    private String username;
    private String gender;
    private String birthDate;






    public static final String FROM_FACEBOOK = "from_facebook";
    public static final String FROM_PHONE = "from_phone";
    private String avatarUrl;
    private List<Uri> uriList;


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
                finish();
                break;
            case R.id.ll_user_avatar:
                chooseAvatar();
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

    private boolean judgeEmpty() {
         username = etUserName.getText().toString();
         gender=etGender.getText().toString();
         birthDate= etBirth.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showToast(this, "userName" + "不可以为空");
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
                avatarUrl=path;
                startConfirm();
            }

            @Override
            public void uploadFailure() {
                // TODO: 2019/6/25 0025 上传图片失败
                Log.e(TAG, "uploadFailure: 失败");
            }
        });
    }



    private void chooseAvatar() {
        Matisse.from(this)
                .choose(MimeType.ofAll())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(1)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, getPackageName()+".fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(new MyGlideEngine())//图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE);//

    }

    private void startConfirm() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==REQUEST_CODE_CHOOSE){
            uriList = Matisse.obtainResult(data);
        }

    }
}
