package chat.hala.hala.rongyun.customizemessage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import chat.hala.hala.R;
import chat.hala.hala.dialog.RedPocketDialog;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = CustomizeHongbaoReceiveMessage.class)
public class CustomizeHongbaoReceiveMessageProvider extends IContainerItemProvider.MessageProvider<CustomizeHongbaoReceiveMessage> {

    Context mContext;


    class ViewHolder {
        TextView tvContent;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_hongbao_receive_message, null);
        ViewHolder holder = new ViewHolder();
        holder.tvContent = (TextView) view.findViewById(R.id.tv_content);
        view.setTag(holder);
        return view;
    }


    @Override
    public void bindView(View view, int i, CustomizeHongbaoReceiveMessage customizeMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // TODO: 2019/8/9/009 这里缺个名字
        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.tvContent.setText(String.format(mContext.getResources().getString(R.string.recei_your_red),"你","狗子"));
        } else {
            holder.tvContent.setText(String.format(mContext.getResources().getString(R.string.recei_your_red),"狗子","你"));
        }
    }

    @Override
    public Spannable getContentSummary(CustomizeHongbaoReceiveMessage customizeMessage) {
        return new SpannableString("发来一个红包");

    }

    @Override
    public void onItemClick(View view, int i, CustomizeHongbaoReceiveMessage customizeMessage, UIMessage uiMessage) {

    }


}