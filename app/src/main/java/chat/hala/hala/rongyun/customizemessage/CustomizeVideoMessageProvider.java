package chat.hala.hala.rongyun.customizemessage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import chat.hala.hala.R;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = CustomizeVideoMessage.class)
public class CustomizeVideoMessageProvider extends IContainerItemProvider.MessageProvider<CustomizeVideoMessage> {


    Context mContext;


    class ViewHolder {
        TextView topMessage;
        TextView bottomMessage;
        ImageView topImage;
        LinearLayout llTop;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_voice_message, null);
        ViewHolder holder = new ViewHolder();
        holder.topMessage = (TextView) view.findViewById(R.id.tv_top_text);
        holder.bottomMessage = (TextView) view.findViewById(R.id.tv_bottom_text);
        holder.topImage = (ImageView) view.findViewById(R.id.iv_voice_video);
        holder.llTop = (LinearLayout) view.findViewById(R.id.ll_top);
        view.setTag(holder);
        return view;

    }


    @Override
    public void bindView(View view, int i, CustomizeVideoMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String fromOrTo;
        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            fromOrTo = "出";
            holder.topImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_chat_video_left));
            holder.llTop.setBackground(mContext.getResources().getDrawable(R.drawable.bg_chat_video_send_top_send));
        } else {
            fromOrTo = "来";
            holder.llTop.setBackground(mContext.getResources().getDrawable(R.drawable.bg_chat_video_send_top_receive));
            holder.topImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_chat_video_right));
        }
        if (customizeMessage.getType() == CustomizeVideoMessage.VOICE_CALL) {
            holder.topImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_chat_voice));
            holder.topMessage.setText("发" + fromOrTo + "一个语音邀请");
        } else if (customizeMessage.getType() == CustomizeVideoMessage.VIDEO_CALL) {
            holder.topMessage.setText("发" + fromOrTo + "一个视频邀请");
        }

        //  AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(CustomizeVideoMessage customizeMessage) {

        if (customizeMessage.getType() == CustomizeVideoMessage.VOICE_CALL) {
            return new SpannableString("发来一个语音邀请");
        } else if (customizeMessage.getType() == CustomizeVideoMessage.VIDEO_CALL) {
            return new SpannableString("发来一个视频邀请");
        }
        return new SpannableString("发来一个语音邀请");

    }

    @Override
    public void onItemClick(View view, int i, CustomizeVideoMessage customizeMessage, UIMessage uiMessage) {

    }


}