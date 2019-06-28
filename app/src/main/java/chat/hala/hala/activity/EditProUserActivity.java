package chat.hala.hala.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
import chat.hala.hala.utils.ToastUtils;
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
    public static final String FROM_MYFRAG_MENT = "from_myfrag_ment";
    private String avatarUrl;
    private List<String> uriList=new ArrayList<>();

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
                startConfirm();
                Log.e(TAG, "uploadFailure: 失败");
            }
        });
    }



    private void chooseAvatar() {
        ChoosePicManager.choosePic(EditProUserActivity.this,1);
    }

    private void startConfirm() {

        Observable<RegistBean> regist=null;
        if (type.equals(FROM_PHONE)) {
            regist= RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(code, avatarUrl, username, gender, birthDate, mobileNumber));
        }else if (type.equals(FROM_FACEBOOK)){
            regist=RetrofitFactory.getInstance().regist(ProxyPostHttpRequest.getInstance().regist(avatarUrl, username, gender, birthDate,facebookId));
        }else if(type.equals(FROM_MYFRAG_MENT)){
            regist=RetrofitFactory.getInstance().changeUserInfo(ProxyPostHttpRequest.getInstance().changeUserInfo( avatarUrl, username, gender, birthDate, mobileNumber));
        }
        regist.subscribeOn(Schedulers.io())
                .compose(this.<RegistBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RegistBean>() {
                    @Override
                    public void onNext(RegistBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=baseBean.getCode()) {
                            ToastUtils.showToast(EditProUserActivity.this, "保存失败");
                            return;
                        }
                        AvchatInfo.setName(baseBean.getData().getUsername());
                        AvchatInfo.setCoin(baseBean.getData().getCoin());
                        AvchatInfo.setAvatarUrl(baseBean.getData().getAvatarUrl());
                        ToastUtils.showToast(EditProUserActivity.this, "保存成功");
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(resultCode==RESULT_OK&&requestCode==ChoosePicManager.REQUEST_CODE_CHOOSE){
             List<String> strings = Matisse.obtainPathResult(data);
             if (strings!=null) {
                 uriList.clear();
                 uriList.addAll(strings) ;
                 Glide.with(this).load(uriList.get(0)) .into(ivHead) ;
             }
        }
    }
}
