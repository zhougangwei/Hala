package chat.hala.hala.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.utils.ActivityManagerUtil;
import io.rong.imkit.RongIM;

/**
 * @author wjy on 2019/10/14/014.
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_black)
    TextView tvBlack;
    @BindView(R.id.ll_black)
    LinearLayout llBlack;
    @BindView(R.id.ll_login_out)
    LinearLayout llLoginOut;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("设置");
    }


    @OnClick({R.id.iv_back, R.id.ll_black, R.id.ll_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_black:
                break;
            case R.id.ll_login_out:
                new CommonDialog(SettingActivity.this)
                        .setMsg(getString(R.string.want_to_log_out))
                        .setListener(new CommonDialog.OnClickListener() {
                            @Override
                            public void onClickConfirm() {
                                setResult(RESULT_OK);
                                SettingActivity.this.finish();
                            }
                            @Override
                            public void onClickCancel() {
                            }
                        })
                        .show();
                break;
        }
    }
}
