package chat.hala.hala.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import io.rong.imkit.fragment.SubConversationListFragment;

public class SubConversationListActivtiy extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_subconversation_list;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("系统消息");

        FragmentManager fragmentManage = getSupportFragmentManager();
        SubConversationListFragment fragement = (SubConversationListFragment) fragmentManage.findFragmentById(R.id.subconversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("subconversationlist")
                //  .appendQueryParameter("type", conversationType.getName())
                .build();
        fragement.setUri(uri);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}