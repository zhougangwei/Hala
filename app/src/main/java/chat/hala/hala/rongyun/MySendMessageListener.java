package chat.hala.hala.rongyun;

import android.util.Log;

import chat.hala.hala.activity.ConversationActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.bean.MemberInfoBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.manager.MoneyHelper;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * @author wjy on 2019/8/23/023.
 */
public class MySendMessageListener implements RongIM.OnSendMessageListener, IRongCallback.ISendMessageCallback {
    private static final String TAG = "MySendMessageListener";

    ConversationActivity conversationActivity;
    public MySendMessageListener(ConversationActivity conversationActivity) {
        this.conversationActivity=conversationActivity;
    }


    @Override
    public Message onSend(final Message message) {
        Log.e(TAG, "onSend:");
        if(!AvchatInfo.isAnchor()&&message.getSentStatus()==null&&message.getContent()instanceof TextMessage){
            RetrofitFactory.getInstance().getMemberData(AvchatInfo.getAccount())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseCosumer<MemberInfoBean>() {
                        @Override
                        public void onGetData(MemberInfoBean memberInfoBean) {
                            if(ResultUtils.cheekSuccess(memberInfoBean)){
                                if (memberInfoBean.getData().getCoin()-conversationActivity.getChatCpm()>=0){
                                    message.setSentStatus(Message.SentStatus.SENDING);
                                    RongIM.getInstance().sendMessage(message, null, null, MySendMessageListener.this);
                                }else{
                                    ToastUtils.showToast(conversationActivity,"余额不足!");
                                }
                            }else{
                                ToastUtils.showToast(conversationActivity,"发送失败!");
                            }
                        }
                    });
                return null;
        }
        return message;
    }

    private boolean judgeMoney() {
        return MoneyHelper.judgeMoney();
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        Log.e(TAG, "onSent:");
        if (sentMessageErrorCode==null&&!AvchatInfo.isAnchor()) {
             MoneyHelper.costMessageMoney(conversationActivity.getAnchorId());
        }
        return false;
    }

    @Override
    public void onAttached(Message message) {

    }
    @Override
    public void onSuccess(Message message) {

    }

    @Override
    public void onError(Message message, RongIMClient.ErrorCode errorCode) {

    }
}
