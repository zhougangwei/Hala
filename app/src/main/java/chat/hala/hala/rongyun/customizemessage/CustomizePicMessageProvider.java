package chat.hala.hala.rongyun.customizemessage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chat.hala.hala.R;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.utils.BitmapUtil;

@ProviderTag(
        messageContent = ImageMessage.class,
        showProgress = false,
        showReadState = true
)public class CustomizePicMessageProvider extends IContainerItemProvider.MessageProvider<ImageMessage> {


    private static final String TAG = "CustomizePicMessageProvider";

    public CustomizePicMessageProvider() {
    }
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_image_message, (ViewGroup)null);
        CustomizePicMessageProvider.ViewHolder holder = new CustomizePicMessageProvider.ViewHolder();
        holder.message = (TextView)view.findViewById(R.id.rc_msg);
        holder.img = (AsyncImageView)view.findViewById(R.id.rc_img);
        view.setTag(holder);
        return view;
    }

    public void onItemClick(View view, int position, ImageMessage content, UIMessage message) {
        if (content != null) {
            Intent intent = new Intent("io.rong.imkit.intent.action.picturepagerview");
            intent.setPackage(view.getContext().getPackageName());
            intent.putExtra("message", message.getMessage());
            view.getContext().startActivity(intent);
        }
    }

    public void bindView(View v, int position, ImageMessage content, UIMessage message) {
        CustomizePicMessageProvider.ViewHolder holder = (CustomizePicMessageProvider.ViewHolder)v.getTag();
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {
            v.setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
        } else {
            v.setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
        }

        if (content.isDestruct()) {
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(content.getThumUri().toString());
            if (bitmap != null) {
                Bitmap blurryBitmap = BitmapUtil.getBlurryBitmap(v.getContext(), bitmap, 5.0F, 0.25F);
                holder.img.setBitmap(blurryBitmap);
            }
        } else {
            holder.img.setResource(content.getThumUri());
        }

        int progress = message.getProgress();
        Message.SentStatus status = message.getSentStatus();
        if (status.equals(Message.SentStatus.SENDING) && progress < 100) {
            holder.message.setText(progress + "%");
            holder.message.setVisibility(0);
        } else {
            holder.message.setVisibility(8);
        }

    }

    public Spannable getContentSummary(ImageMessage data) {
        return null;
    }

    public Spannable getContentSummary(Context context, ImageMessage data) {
        return new SpannableString(context.getString(R.string.rc_message_content_image));
    }

    private static class ViewHolder {
        AsyncImageView img;
        TextView message;

        private ViewHolder() {
        }
    }


}