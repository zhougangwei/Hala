package chat.hala.hala.rongyun.customizemessage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;

import org.w3c.dom.Text;

import chat.hala.hala.R;
import chat.hala.hala.dialog.RedPocketDialog;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imkit.widget.provider.ImageMessageItemProvider;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = CustomizeHongbaoMessage.class)
public class CustomizeHongbaoMessageProvider extends IContainerItemProvider.MessageProvider<CustomizeHongbaoMessage> {

    private static final String TAG ="CustomizeHongbaoProvider" ;
    Context mContext;


    class ViewHolder {

        TextView toptext;
        RelativeLayout llTop;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_hongbao_message, null);
        ViewHolder holder = new ViewHolder();
        holder.llTop = (RelativeLayout) view.findViewById(R.id.ll_top);
        holder.toptext = (TextView) view.findViewById(R.id.tv_top_text2);
        view.setTag(holder);
        return view;
    }


    @Override
    public void bindView(View view, int i, CustomizeHongbaoMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();



        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.llTop.setBackground(mContext.getResources().getDrawable(R.drawable.bg_chat_red_pocket_top_opened));
        } else {
            holder.llTop.setBackground(mContext.getResources().getDrawable(R.drawable.bg_chat_red_pocket_top));
        }
        //  AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(CustomizeHongbaoMessage customizeMessage) {
        return new SpannableString("发来一个红包");

    }

    @Override
    public void onItemClick(View view, int i, CustomizeHongbaoMessage customizeMessage, UIMessage uiMessage) {
        String extra = customizeMessage.getExtra();
        new RedPocketDialog(mContext, extra).show();
        CustomizeHongbaoReceiveMessage myTextMessage = CustomizeHongbaoReceiveMessage.obtain("9.99");
        Message myMessage = Message.obtain(uiMessage.getTargetId(), Conversation.ConversationType.PRIVATE, myTextMessage);
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                LogUtils.e(TAG, "onAttached:"+message.getTargetId());
            }
            @Override
            public void onSuccess(Message message) {
                LogUtils.e(TAG, "onSuccess: "+message.getTargetId());
            }
            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                LogUtils.e(TAG, "onError: "+errorCode);
            }
        });

    }


}