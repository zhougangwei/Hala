package chat.hala.hala.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.manager.MoneyHelper;
import chat.hala.hala.rongyun.RongInfo;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Message;
import retrofit2.http.Path;

public class ConversationActivity extends BaseActivity implements RongIM.OnSendMessageListener {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView  mTvTitle;
    private String mtargetId;
    private int anchorId;
    private int memberId;
    public static final String TAG="ConversationActivity";


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
        mtargetId = data.getQueryParameter("targetId");
        memberId = Integer.parseInt(mtargetId);
        mTvTitle.setText(title);
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        fragement.setUri(data);
        RongIM.getInstance().setSendMessageListener(this);
        RetrofitFactory.getInstance().getAnchorData("member", memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AnchorBean>() {
                    @Override
                    public void onGetData(AnchorBean anchorBean) {
                        if (ResultUtils.cheekSuccess(anchorBean)) {
                            anchorId = anchorBean.getData().getAnchorId();
                        }
                    }
                });
    }
    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Message onSend(Message message) {

        Log.e(TAG, "onSend:");

      /*  if(!AvchatInfo.isAnchor()){
            if(!judgeMoney()){
                return null;
            }
        }*/
        return message;
    }

    private boolean judgeMoney() {
       return MoneyHelper.judgeMoney();
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        Log.e(TAG, "onSent:");
        if (sentMessageErrorCode!=null&&!AvchatInfo.isAnchor()) {
            MoneyHelper.costMoney(anchorId);
        }
        return false;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}