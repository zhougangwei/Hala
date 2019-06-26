package chat.hala.hala.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.utils.FacebookLoginManager;
import chat.hala.hala.utils.ToastUtils;

public class LoginActivity extends BaseActivity {

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
        initFaceBook();

    }

    private void initFaceBook() {

    }

    @OnClick({R.id.ll_face_login, R.id.ll_phone, R.id.tv_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_face_login:
                loginfacebook();
                break;
            case R.id.ll_phone:
                startLoginPhone();
                break;
            case R.id.tv_policy:
                alertPloicu();
                break;
        }
    }

    private void loginfacebook() {

        FacebookLoginManager facebookLoginManager = new FacebookLoginManager();
        facebookLoginManager.init();
        facebookLoginManager.loginFaceBook(this, new FacebookLoginManager.OnResult() {
            @Override
            public void success() {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void fail() {
                ToastUtils.showToast(LoginActivity.this,getString(R.string.login_failed));

            }
            @Override
            public void regist(String id) {
                Intent intent = new Intent(LoginActivity.this,EditProUserActivity.class);
                intent.putExtra("type", EditProUserActivity.FROM_FACEBOOK);
                intent.putExtra("facebookid",id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void alertPloicu() {


    }
    private void startLoginPhone() {
        startActivity(new Intent(this,LoginPhoneActivity.class));
    }


}
