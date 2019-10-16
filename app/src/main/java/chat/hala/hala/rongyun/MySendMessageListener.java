package chat.hala.hala.rongyun;

import android.util.Log;

import chat.hala.hala.activity.ConversationActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.manager.MoneyHelper;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Message;

/**
 * @author wjy on 2019/8/23/023.
 */
public class MySendMessageListener implements RongIM.OnSendMessageListener {
    private static final String TAG = "MySendMessageListener";

    ConversationActivity conversationActivity;
    public MySendMessageListener(ConversationActivity conversationActivity) {
        this.conversationActivity=conversationActivity;
    }


    @Override
    public Message onSend(Message message) {

        Log.e(TAG, "onSend:");
        if(!AvchatInfo.isAnchor()){
            if(!judgeMoney()){
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
        Log.e(TAG, "onSent:");
        if (sentMessageErrorCode==null&&!AvchatInfo.isAnchor()) {
             MoneyHelper.costMessageMoney(conversationActivity.getAnchorId());
        }
        return false;
    }
}
