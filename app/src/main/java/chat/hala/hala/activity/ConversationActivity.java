package chat.hala.hala.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.manager.MoneyHelper;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Message;

public class ConversationActivity extends BaseActivity implements RongIM.OnSendMessageListener {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView  mTvTitle;
    private String conversation;
    private String mtargetId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void beforeInitView() {


    }

    @Override
    protected void initView() {


        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data == null) {
            return;
        }
        String title = data.getQueryParameter("title");
        mTvTitle.setText(title);
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        fragement.setUri(data);
        RongIM.getInstance().setSendMessageListener(this);
    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }



    @Override
    public Message onSend(Message message) {
        if(AvchatInfo.isAnchor()){
            if(judgeMoney()){
                return null;
            }
        }
        return message;
    }

    private boolean judgeMoney() {
       return MoneyHelper.judgeMoney();
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        return false;
    }
}