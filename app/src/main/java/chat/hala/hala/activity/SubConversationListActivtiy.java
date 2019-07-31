package chat.hala.hala.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;

import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import io.rong.imkit.fragment.SubConversationListFragment;

public class SubConversationListActivtiy extends BaseActivity {



    @Override
    protected int getContentViewId() {
        return R.layout.activity_subconversation_list;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        FragmentManager fragmentManage = getSupportFragmentManager();
        SubConversationListFragment fragement = (SubConversationListFragment) fragmentManage.findFragmentById(R.id.subconversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("subconversationlist")
              //  .appendQueryParameter("type", conversationType.getName())
                .build();
        fragement.setUri(uri);
    }
}