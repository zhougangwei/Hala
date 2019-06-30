package chat.hala.hala.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.dialog.PolicyDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.FacebookLoginManager;
import chat.hala.hala.utils.IdViewListener;
import chat.hala.hala.utils.TextUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements IdViewListener {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.ll_face_login)
    LinearLayout llFaceLogin;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_policy)
    TextView tvPolicy;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        TextUtils.makeTextViewCLick(tvPolicy, "EULA", Color.parseColor("#472EB4"), false, this, this);
        TextUtils.makeTextViewCLick(tvPolicy, "Privacy Policy", Color.parseColor("#472EB4"), false, this, this);
        initQiniuData();
    }
    private void initQiniuData() {
        RetrofitFactory
                .getInstance()
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<QiNiuToken>() {
                    @Override
                    public void onNext(QiNiuToken baseBean) {
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

    @OnClick({R.id.ll_face_login, R.id.ll_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_face_login:
                if(cb.isChecked()){
                    loginfacebook();
                }else{
                    alertPloicu();
                }
                break;
            case R.id.ll_phone:
                if(cb.isChecked()){
                    startLoginPhone();
                }else{
                    alertPloicu();
                }
                break;

        }
    }

    private void loginfacebook() {

        FacebookLoginManager facebookLoginManager = new FacebookLoginManager();
        facebookLoginManager.init();
        facebookLoginManager.loginFaceBook(this, new FacebookLoginManager.OnResult() {
            @Override
            public void success() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void fail() {
                ToastUtils.showToast(LoginActivity.this, getString(R.string.login_failed));

            }

            @Override
            public void regist(String id) {
                Intent intent = new Intent(LoginActivity.this, EditProUserActivity.class);
                intent.putExtra("type", EditProUserActivity.FROM_FACEBOOK);
                intent.putExtra("facebookid", id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void alertPloicu() {
        new PolicyDialog(this).
                setListener(new PolicyDialog.OnClickListener() {
                    @Override
                    public void onClickConfirm() {
                       cb.setChecked(true);
                    }
                    @Override
                    public void onClickCancel() {
                    }
                })
                .show();
    }

    private void startLoginPhone() {
        startActivity(new Intent(this, LoginPhoneActivity.class));
    }


    @Override
    public void clickId(String id) {
        alertPloicu();
        ToastUtils.showToast(this, "点击了");
    }
}
