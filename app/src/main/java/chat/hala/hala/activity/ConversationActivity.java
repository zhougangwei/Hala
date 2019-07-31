package chat.hala.hala.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;

import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import io.rong.imkit.fragment.ConversationFragment;

public class ConversationActivity extends BaseActivity {


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
        if (data==null){
            return;
        }
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        fragement.setUri(data);
    }
}