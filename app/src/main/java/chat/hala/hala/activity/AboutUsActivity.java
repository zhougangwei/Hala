package chat.hala.hala.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_user_protocol)
    LinearLayout llUserProtocol;
    @BindView(R.id.ll_privacy)
    LinearLayout llPrivacy;
    @BindView(R.id.ll_contact_us)
    LinearLayout llContactUs;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("关于Pa聊");
    }


    @OnClick({R.id.iv_back, R.id.ll_user_protocol, R.id.ll_privacy, R.id.ll_contact_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_user_protocol:
                WebviewActivity.loadUrl(this,"http://www.starchat.me/terms.html","用户协议");
                break;
            case R.id.ll_privacy:
                WebviewActivity.loadUrl(this,"http://www.starchat.me/privacy.html","隐私协议");
                break;
            case R.id.ll_contact_us:
                break;
        }
    }
}
