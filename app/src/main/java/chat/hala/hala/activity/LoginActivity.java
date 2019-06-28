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
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.dialog.PolicyDialog;
import chat.hala.hala.utils.FacebookLoginManager;
import chat.hala.hala.utils.IdViewListener;
import chat.hala.hala.utils.TextUtils;
import chat.hala.hala.utils.ToastUtils;

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
